package tmall.servlet;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.ImageUtil;
import tmall.util.Page;
import tmall.util.ProductImageDAO;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baobaochu on 2017/7/2.
 * 1.增加产品单个图片
 * 2.增加产品详情图片
 */
@WebServlet(name = "ProductImageServlet")
public class ProductImageServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {

        //上传文件的输入流
        InputStream is=null;

        //提交上传文件时的其他参数
        Map<String,String> params=new HashMap<>();

        //解析上传
        is=parseUpload(request,params);


        //根据上传的参数生成productImage对象
        String type=params.get("type");
        int pid=Integer.parseInt(params.get("pid"));
        Product p=productDAO.get(pid);

        ProductImage pi=new ProductImage();
        pi.setType(type);
        pi.setProduct(p);
        productImageDAO.add(pi);

        //生成文件
        String fileName=pi.getId()+".jpg";
        String imageFolder = null;
        String imageFolder_small=null;
        String imageFolder_middle=null;
        //如果是单个展示图片，生成三种大中小文件在相应的文件夹下
        //如果是产品细节图片，则生成文件在相应文件夹下
        if(ProductImageDAO.type_single.equals(pi.getType())){
            imageFolder=request.getSession().getServletContext().getRealPath("img/productSingle");
            imageFolder_small=request.getSession().getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle=request.getSession().getServletContext().getRealPath("img/productSingle_middle");
        }
        if(ProductImageDAO.type_detail.equals(pi.getType())){
            imageFolder=request.getSession().getServletContext().getRealPath("img/productDetail");
        }
        File f=new File(imageFolder,fileName);
        f.getParentFile().mkdirs();

        //复制文件
        try{
            if(is!=null&&is.available()!=0){
                try(FileOutputStream fos=new FileOutputStream(f)){
                    byte b[]=new byte[1024*1024];
                    int length=0;
                    while ((length=is.read(b))!=-1){
                        fos.write(b,0,length);
                    }
                    fos.flush();

                    //把文件转换为jpg
                    BufferedImage img= ImageUtil.change2jpg(f);
                    ImageIO.write(img,"jpg",f);

                    if(ProductImageDAO.type_single.equals(pi.getType())){
                        File f_small=new File(imageFolder_small,fileName);
                        File f_middle=new File(imageFolder_middle,fileName);

                        ImageUtil.resizeImage(f,56,56,f_small);
                        ImageUtil.resizeImage(f,217,190,f_middle);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
        return "@admin_productImage_list?pid="+p.getId();

    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id=Integer.parseInt(request.getParameter("id"));
        ProductImage pi=productImageDAO.get(id);
        productImageDAO.delete(id);

        if(productImageDAO.type_single.equals(pi.getType())){
            String imageFolder_single=request.getSession().getServletContext().getRealPath("img/productSingle");
            String imageFolder_small=request.getSession().getServletContext().getRealPath("img/productSingle_small");
            String imageFolder_middle=request.getSession().getServletContext().getRealPath("img/productSingle_middle");

            File f_single=new File(imageFolder_single,pi.getId()+".jpg");
            f_single.delete();
            File f_small=new File(imageFolder_small,pi.getId()+".jpg");
            f_small.delete();
            File f_middle=new File(imageFolder_middle,pi.getId()+".jpg");
            f_middle.delete();
        }
        else {
            String imageFolder_detail=request.getSession().getServletContext().getRealPath("img/productDetail");
            File f_detail=new File(imageFolder_detail,pi.getId()+".jpg");
            f_detail.delete();
        }
        return "@admin_productImage_list?pid="+pi.getProduct().getId();


    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int pid=Integer.parseInt(request.getParameter("pid"));
        Product p=productDAO.get(pid);
        List<ProductImage> pisSingle=productImageDAO.list(p,productImageDAO.type_single);
        List<ProductImage> pisDetail=productImageDAO.list(p,productImageDAO.type_detail);

        request.setAttribute("p",p);
        request.setAttribute("pisSingle",pisSingle);
        request.setAttribute("pisDetail",pisDetail);

        return "admin/listProductImage.jsp";
    }
}

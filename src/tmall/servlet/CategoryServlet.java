package tmall.servlet;

import tmall.bean.Category;
import tmall.util.ImageUtil;
import tmall.util.Page;

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
 * Created by baobaochu on 2017/6/25.
 *
 */
@WebServlet(name = "CategoryServlet")
public class CategoryServlet extends BaseBackServlet {

    @Override

    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        //从父类的parseupload方法中，修改参数。把浏览器提交的name信息放在其中。最后获取上传文件的输入流
        Map<String,String> params=new HashMap<>();
        InputStream is=super.parseUpload(request,params);

        //从param中取出name(分类名)，根据这个name借助categoryDAO向数据库中插入数据
        String name=params.get("name");
        Category c=new Category();
        c.setName(name);
        categoryDAO.add(c);

        // 定位存放分类图片的目录。
        File imageFolder=new File(request.getSession().getServletContext().getRealPath("img/category"));
        //文件命名以保存到数据库的分类对象的id+.jpg 格式命名
        File file=new File(imageFolder,c.getId()+".jpg");

        try{
            //根据步骤1获取的输入流，把浏览器提交的文件，复制到目标文件
            if(is!=null&&is.available()!=0){
               try(FileOutputStream fos=new FileOutputStream(file)){
                   byte b[] =new byte[1024*1024];
                   int length=0;
                   while((length=is.read(b))!=-1){
                       fos.write(b,0,length);
                   }
                   fos.flush();

                   //通过如下代码把文件保存为jpg格式
                   BufferedImage img= ImageUtil.change2jpg(file);
                   ImageIO.write(img,"jpg",file);
               }

            }

        }catch (IOException e){
            e.printStackTrace();
        }

        return "@admin_category_list";
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
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
        List<Category> cs=categoryDAO.list(page.getStart(),page.getCount());
        int total=categoryDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("thecs",cs);
        request.setAttribute("page",page);
        return "admin/listCategory.jsp";


    }
}

package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by baobaochu on 2017/7/1.
 */
@WebServlet(name = "ProductServlet")
public class ProductServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));
        Category c=categoryDAO.get(cid);

        String name=request.getParameter("name");
        String subTitle=request.getParameter("subTitle");
        float originalPrice=Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice=Float.parseFloat(request.getParameter("promotePrice"));
        int stock=Integer.parseInt(request.getParameter("stock"));

        Product p=new Product();

        p.setCategory(c);
        p.setStock(stock);
        p.setPromotePrice(promotePrice);
        p.setName(name);
        p.setOriginalPrice(originalPrice);
        p.setSubTitle(subTitle);
        productDAO.add(p);

        return "@admin_product_list?cid="+cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id=Integer.parseInt(request.getParameter("id"));
        Product p=productDAO.get(id);
        productDAO.delete(id);


        return "@admin_product_list?cid="+p.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {

        int id=Integer.parseInt(request.getParameter("id"));
        Product p=productDAO.get(id);
        request.setAttribute("p",p);
        return "admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));

        Category c=categoryDAO.get(cid);

        int id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        String subTitle=request.getParameter("subTitle");
        float originalPrice=Float.parseFloat(request.getParameter("originalPrice"));
        float promotePrice=Float.parseFloat(request.getParameter("promotePrice"));
        int stock=Integer.parseInt(request.getParameter("stock"));

        Product p=new Product();
        p.setCategory(c);
        p.setStock(stock);
        p.setPromotePrice(promotePrice);
        p.setName(name);
        p.setOriginalPrice(originalPrice);
        p.setSubTitle(subTitle);
        p.setId(id);

        productDAO.update(p);



        return "@admin_product_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));
        Category c=categoryDAO.get(cid);
        List<Product> ps=productDAO.list(cid,page.getStart(),page.getCount());
        int total=productDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        //设定传递产品的list
        request.setAttribute("ps",ps);
        request.setAttribute("c",c);
        request.setAttribute("page",page);




        return "admin/listProduct.jsp";
    }
}

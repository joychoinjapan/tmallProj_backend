package tmall.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.util.Page;
import tmall.util.PropertyDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by baobaochu on 2017/6/29.
 */
@WebServlet(name = "PropertyServlet")
public class PropertyServlet extends BaseBackServlet{

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));
        Category c=categoryDAO.get(cid);
        String name=request.getParameter("name");
        Property p=new Property();
        p.setName(name);
        p.setCategory(c);
        propertyDAO.add(p);
        return "@admin_property_list?cid="+cid;

    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id=Integer.parseInt(request.getParameter("id"));
        Property p=propertyDAO.get(id);
        propertyDAO.delete(id);
        return "@admin_property_list?cid="+p.getCategory().getId();


    }

    @Override
    //点击编辑按钮之后，获得点击的属性id生成一个属性对象保存，然后传递给编辑页面的jsp
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id=Integer.parseInt(request.getParameter("id"));
        Property p=propertyDAO.get(id);
        request.setAttribute("p",p);
        return  "admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));
        Category c=categoryDAO.get(cid);

        int id=Integer.parseInt(request.getParameter("id"));
        String name=request.getParameter("name");
        Property p=new Property();
        p.setCategory(c);
        p.setName(name);
        p.setId(id);
        propertyDAO.update(p);
        return "@admin_property_list?cid="+p.getCategory().getId();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid=Integer.parseInt(request.getParameter("cid"));
        Category c=categoryDAO.get(cid);
        List<Property> ps=propertyDAO.list(cid,page.getStart(),page.getCount());

        int total=propertyDAO.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid="+c.getId());

        request.setAttribute("ps",ps);
        request.setAttribute("c",c);
        request.setAttribute("page",page);




        return "admin/listProperty.jsp";
    }
}

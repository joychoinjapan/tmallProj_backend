package tmall.servlet;

import tmall.bean.Order;
import tmall.util.OrderDAO;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by baobaochu on 2017/7/5.
 */
@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    public String delivery(HttpServletRequest request,HttpServletResponse response,Page page){
        int id=Integer.parseInt(request.getParameter("id"));
        Order o=orderDAO.get(id);
        o.setDeliveryDate(new Date());
        //将收货状态设置为等待收货
        o.setStatus(OrderDAO.waitConfirm);
        orderDAO.update(o);
        return "@admin_order_list";
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
        List<Order> os=orderDAO.list(page.getStart(),page.getCount());
        orderItemDAO.fill(os);
        int total=orderDAO.getTotal();
        page.setTotal(total);

        request.setAttribute("os",os);
        request.setAttribute("page",page);
        return "admin/listOrder.jsp";
    }
}

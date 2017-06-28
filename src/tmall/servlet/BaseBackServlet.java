package tmall.servlet;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.Property;
import tmall.util.*;




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by baobaochu on 2017/6/25.
 */
@WebServlet(name = "BaseBackServlet")
public abstract class BaseBackServlet extends HttpServlet {
    public abstract String add(HttpServletRequest request,HttpServletResponse response,Page page);
    public abstract String delete(HttpServletRequest request,HttpServletResponse response,Page page);
    public abstract String edit(HttpServletRequest request,HttpServletResponse response,Page page);
    public abstract String update(HttpServletRequest request,HttpServletResponse response,Page page);
    public abstract String list(HttpServletRequest request,HttpServletResponse response,Page page);

    protected CategoryDAO categoryDAO=new CategoryDAO();
    protected OrderDAO orderDAO=new OrderDAO();
    protected OrderItemDAO orderItemDAO=new OrderItemDAO();
    protected ProductDAO productDAO=new ProductDAO();
    protected ProductImageDAO productImageDAO=new ProductImageDAO();
    protected PropertyDAO propertyDAO=new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO=new PropertyValueDAO();
    protected ReviewDAO reviewDAO=new ReviewDAO();
    protected UserDAO userDAO=new UserDAO();


    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //获取分页信息
            int start = 0;
            int count = 5;
            try {
                start = Integer.parseInt(request.getParameter("page.start"));

            } catch (Exception e) {

            }
            try {
                count = Integer.parseInt(request.getParameter("page.count"));
            } catch (Exception e) {

            }
            Page page = new Page(start, count);

            //用反射调用相应的方法
            String method = (String) request.getAttribute("method");
            Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class, javax.servlet.http.HttpServletResponse.class, Page.class);
            String redirect = m.invoke(this, request, response, page).toString();

            //借助反射执行方法后获得返回值redirect，如果是@开头，执行客户端跳转，如果是%开头直接输出字符串，其他进行服务端跳转
            if (redirect.startsWith("@")) {
                response.sendRedirect(redirect.substring(1));

            } else if (redirect.startsWith("%")) {
                response.getWriter().print(redirect.substring(1));
            } else {
                request.getRequestDispatcher(redirect).forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


        public InputStream parseUpload(HttpServletRequest request,Map<String,String> params){

          InputStream is=null;
          try{
              DiskFileItemFactory factory=new DiskFileItemFactory();
              ServletFileUpload upload=new ServletFileUpload();
              factory.setSizeThreshold(1024*10240);
              List items=upload.parseRequest(request);
              Iterator iter=items.iterator();
              while (iter.hasNext()){
                  FileItem item=(FileItem)iter.next();
                  if(!item.isFormField()){
                      //输入流
                      is=item.getInputStream();

                  }else {
                      String paramName=item.getFieldName();
                      String paramValue=item.getString();
                      paramValue= new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
                      //参数名
                      params.put(paramName,paramValue);
                  }
              }
          }catch (Exception e){
              e.printStackTrace();
          }
          //返回输入流
          return is;

        }

    }




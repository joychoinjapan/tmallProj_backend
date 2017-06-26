package tmall.filter;



import org.apache.commons.lang.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletRequest;
import java.io.IOException;


/**
 * Created by baobaochu on 2017/6/25.
 */
@WebFilter(filterName = "BackServletFilter")
public class BackServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletResponse response=(HttpServletResponse) resp;

        //获取/tmall/admin_category_list的部分
        String contextPath=request.getServletContext().getContextPath();
        String uri=request.getRequestURI();
        uri= StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/admin_")){
            String servletPath= StringUtils.substringBetween(uri,"_","_")+"Servlet";
            String method=StringUtils.substringAfterLast(uri,"_");
            request.setAttribute("method",method);
            req.getRequestDispatcher("/"+servletPath).forward(request,response);
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

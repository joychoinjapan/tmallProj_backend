package tmall.filter;



import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.commons.lang.StringUtils;

/**
 * Created by baobaochu on 2017/6/25.
 */
@WebFilter(filterName = "BackServletFilter")
public class BackServletFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request=(HttpServletRequest)req;
        HttpServletRequest response=(HttpServletRequest)resp;

        //获取/tmall/admin_category_list的部分
        String contextPath=request.getServletContext().getContextPath();
        String uri=request.getRequestURI();
        uri=StringUtils.remove(uri,contextPath);
        if(uri.startsWith("/admin_")){
            String servletPath= StringUtils.substringBetween(uri,"_","_")+"Servlet";
            String method=StringUtils.substringAfterLast(uri,"_");
            request.setAttribute("method",method);
            req.getRequestDispatcher("/"+servletPath).forward(request, (ServletResponse) response);
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}

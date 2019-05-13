package controlador;
  
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PageFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        String user = (String) session.getAttribute("logado");
        if (user == null || !user.equals("sim")) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/sischamados/faces/login.xhtml");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig fc){
    }

    @Override
    public void destroy() {
    }
}
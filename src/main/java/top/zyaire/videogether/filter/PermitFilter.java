package top.zyaire.videogether.filter;


import org.springframework.core.annotation.Order;
import top.zyaire.videogether.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "permitFilter",urlPatterns = "/video")
public class PermitFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter启用");
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        //验证用户是否登录状态
        //已登录---放行
        //未登录---跳转到登录页面
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        // 验证用户是否登录
        User name = (User) session.getAttribute("logedUser");
        if (name == null) {
            // 未登录状态
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

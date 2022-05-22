package ru.job4j.cinema.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alex Gutorov
 * @version 1.0
 * @created 11/05/2022 - 21:47
 */
@Component
public class AuthFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("formAddUser") || uri.endsWith("addUser") || uri.endsWith("success")
                || uri.endsWith("loginPage") || uri.endsWith("login")) {
            chain.doFilter(req, res);
            return;
        } else if (req.getSession().getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }

        chain.doFilter(req, res);
    }
}

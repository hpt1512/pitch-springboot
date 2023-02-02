package com.example.pitchspringboot.filter;

import com.example.pitchspringboot.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

//        List<String> pathAllow = new ArrayList<>();
//        pathAllow.add("/login");
//        boolean isLogin = true;
//        for (String p: pathAllow) {
//            if (path.contains(p)) {
//                isLogin = false;
//                break;
//            }
//        }

        if (path.contains("/login") || path.contains("/register") || (path.contains("/") && path.length() == 1)  || path.contains("/company") || path.contains("/user/view-user") || path.contains("bootstrap-5.1.3-dist")
                || path.contains("css") || path.contains("img") || path.contains("/err-not-login")) {
            chain.doFilter(req, resp);
        } else if (user == null) {
            response.sendRedirect("/login");
        } else {
            chain.doFilter(req, resp);
        }

    }

    public void init(FilterConfig config) throws ServletException {

    }

}

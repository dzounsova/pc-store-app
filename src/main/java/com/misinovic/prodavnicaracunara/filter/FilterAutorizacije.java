/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.misinovic.prodavnicaracunara.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Misinovic
 */
@WebFilter("/*")
public class FilterAutorizacije implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);

        String reqURI = request.getRequestURI();
        if (reqURI.contains("/login.xhtml")
                || (session != null && session.getAttribute("zaposleni") != null)
                || reqURI.contains("/public/")
                || reqURI.contains("javax.faces.resource")) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/faces/login.xhtml");
        }

    }

    @Override
    public void destroy() {
    }

}

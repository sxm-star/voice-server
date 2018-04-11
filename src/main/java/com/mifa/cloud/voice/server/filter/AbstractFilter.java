package com.mifa.cloud.voice.server.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractFilter implements Filter {
    public AbstractFilter() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }

    protected HttpServletRequest cast(ServletRequest request) {
        return (HttpServletRequest)request;
    }

    protected HttpServletResponse cast(ServletResponse response) {
        return (HttpServletResponse)response;
    }
}

package org.iish.hsn.invoer.util;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor that makes sure that AJAX calls are not cached in Internet Explorer.
 */
public class CachingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestedWith = request.getHeader("X-Requested-With");
        if ((requestedWith != null) && requestedWith.equals("XMLHttpRequest")) {
            response.setHeader("Expires", "-1");
        }
        return true;
    }
}

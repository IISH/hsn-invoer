package org.iish.hsn.invoer.util;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor that makes sure the user has filled out some extra information
 * about the work order he/she will be working on.
 */
public class InputMetadataInterceptor implements HandlerInterceptor {
    private final InputMetadata inputMetadata;

    public InputMetadataInterceptor(InputMetadata inputMetadata) {
        this.inputMetadata = inputMetadata;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean methodAllowsCheckAndRedirect = true;

        // Check for methods with a NoInputMetadataCheck annotation
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            methodAllowsCheckAndRedirect = (handlerMethod.getMethodAnnotation(NoInputMetadataCheck.class) == null);
        }

        // Static resources should never redirect
        if (handler instanceof ResourceHttpRequestHandler)
            methodAllowsCheckAndRedirect = false;

        if (methodAllowsCheckAndRedirect && !inputMetadata.isValid()) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        return true;
    }
}

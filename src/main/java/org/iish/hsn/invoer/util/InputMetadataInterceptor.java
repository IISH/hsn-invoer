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
    private final InputMetadataChecker inputMetadataChecker;

    public InputMetadataInterceptor(InputMetadata inputMetadata, InputMetadataChecker inputMetadataChecker) {
        this.inputMetadata = inputMetadata;
        this.inputMetadataChecker = inputMetadataChecker;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean methodAllowsCheckAndRedirect = true;

        // Check for methods with a NoInputMetadataCheck annotation
        if (handler instanceof HandlerMethod handlerMethod)
            methodAllowsCheckAndRedirect = (handlerMethod.getMethodAnnotation(NoInputMetadataCheck.class) == null);

        // Static resources should never redirect
        if (handler instanceof ResourceHttpRequestHandler)
            methodAllowsCheckAndRedirect = false;

        if (methodAllowsCheckAndRedirect && !inputMetadataChecker.isValid(inputMetadata)) {
            response.sendRedirect(request.getContextPath() + "/");
            return false;
        }

        return true;
    }
}

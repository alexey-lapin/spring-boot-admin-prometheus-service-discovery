package com.github.alexeylapin.sbapsd.web;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerMapping;

public class ReactiveHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotatedElementUtils.hasAnnotation(beanType, ServiceDiscoveryController.class);
    }

}

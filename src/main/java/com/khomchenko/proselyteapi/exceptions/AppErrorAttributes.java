package com.khomchenko.proselyteapi.exceptions;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.Map;

@Component
public class AppErrorAttributes extends DefaultErrorAttributes {

    public AppErrorAttributes() {
        super();
    }

    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        String message = getError(request).getMessage();

        Map<String, Object> errorAttributes = super.getErrorAttributes(request, options);
        errorAttributes.put("message", message);
        return errorAttributes;
    }
}

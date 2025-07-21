package alliwannadev.shop.core.api.common.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class HttpRequestEndpointChecker {

    private final DispatcherServlet servlet;

    public boolean existsEndpoint(HttpServletRequest request) {
        List<HandlerMapping> handlerMappings = servlet.getHandlerMappings();
        if (handlerMappings == null) {
            throw new RuntimeException("handlerMappings가 null 입니다.");
        }

        for (HandlerMapping handlerMapping : handlerMappings) {
            try {
                HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(request);
                if (handlerExecutionChain != null) {
                    Object foundHandler = handlerExecutionChain.getHandler();
                    return !(foundHandler instanceof ResourceHttpRequestHandler);
                }
            } catch (Exception ex) {
                return false;
            }
        }

        return false;
    }
}

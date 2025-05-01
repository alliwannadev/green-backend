package alliwannadev.shop.common.security;

import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.common.error.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final HttpRequestEndpointChecker httpRequestEndpointChecker;
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        if (httpRequestEndpointChecker.existsEndpoint(request)) {
            sendErrorResponse(
                    response,
                    ErrorCode.UNAUTHORIZED
            );
        } else {
            sendErrorResponse(
                    response,
                    ErrorCode.END_POINT_NOT_FOUND
            );
        }
    }

    private void sendErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ) throws IOException {
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");

        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        String result = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(result);
    }
}

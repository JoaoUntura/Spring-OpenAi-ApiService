package dev.joaountura.aihelper.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";
    private static final String REQUEST_ID_ATTRIBUTE = "requestId";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        String requestId = generateRequestId();

        request.setAttribute(START_TIME_ATTRIBUTE, startTime);
        request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Map<String, Object> logData = new HashMap<>();
        logData.put("event", "request_started");
        logData.put("request_id", requestId);
        logData.put("method", request.getMethod());
        logData.put("path", request.getRequestURI());
        logData.put("client_ip", getClientIpAddress(request));
        logData.put("controller", handlerMethod.getBeanType().getSimpleName());
        logData.put("handler_method", handlerMethod.getMethod().getName());
        logData.put("timestamp", startTime);

        logger.info(objectMapper.writeValueAsString(logData));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // Opcional: adicionar log aqui se necessário
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws Exception {

        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        String requestId = (String) request.getAttribute(REQUEST_ID_ATTRIBUTE);

        if (startTime == null) {
            return;
        }

        long duration = System.currentTimeMillis() - startTime;
        int status = response.getStatus();

        Map<String, Object> logData = new HashMap<>();
        logData.put("event", "request_completed");
        logData.put("request_id", requestId);
        logData.put("method", request.getMethod());
        logData.put("path", request.getRequestURI());
        logData.put("status", status);
        logData.put("status_description", getStatusDescription(status));
        logData.put("duration_ms", duration);
        logData.put("client_ip", getClientIpAddress(request));
        logData.put("timestamp", System.currentTimeMillis());

        // Se houver exceção, adiciona informações dela
        if (ex != null) {
            Map<String, String> exceptionData = new HashMap<>();
            exceptionData.put("class", ex.getClass().getSimpleName());
            exceptionData.put("message", ex.getMessage());
            logData.put("exception", exceptionData);

            logger.error(objectMapper.writeValueAsString(logData));
        } else {
            logger.info(objectMapper.writeValueAsString(logData));
        }
    }

    private String generateRequestId() {
        return "REQ-" + System.currentTimeMillis() + "-" +
                (int)(Math.random() * 10000);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }

    private String getStatusDescription(int status) {
        if (status >= 200 && status < 300) return "SUCCESS";
        if (status >= 300 && status < 400) return "REDIRECT";
        if (status >= 400 && status < 500) return "CLIENT_ERROR";
        if (status >= 500) return "SERVER_ERROR";
        return "UNKNOWN";
    }
}
package uk.co.onsdigital.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class RequestIdFilter implements Filter, RequestIdProvider {

    private static final Logger log = LoggerFactory.getLogger(RequestIdFilter.class);
    public static final String REQUEST_ID = "requestId";
    public static final String REQUEST_ID_HEADER = "X-Request-Id";

    private ThreadLocal<String> requestId = new ThreadLocal<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Inserting request id into logs from request header '{}'", REQUEST_ID_HEADER);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // get the request id
            String id = null;
            if (request instanceof HttpServletRequest) {
                ((HttpServletRequest) request).getHeader(REQUEST_ID_HEADER);
            }
            if (isEmpty(id)) {
                id = UUID.randomUUID().toString();
                log.debug("No request id found - generating uuid: {}", id);
            }
            requestId.set(id);
            // Setup MDC data for logging:
            MDC.put(REQUEST_ID, String.format("[requestId=%s]", id)); // referenced in Spring Boot's logging.pattern.level property
            chain.doFilter(request, response);
        } finally {
            // Tear down MDC data:
            MDC.remove(REQUEST_ID);
            requestId.remove();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public String getId() {
        return requestId.get();
    }
}
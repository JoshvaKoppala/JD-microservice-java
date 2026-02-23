package com.jd.microservice.inventoryservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class WebLogFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(WebLogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Wrap request and response so we can read their bodies without consuming the
        // stream
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        // Proceed with the actual request handling
        filterChain.doFilter(wrappedRequest, wrappedResponse);

        // Read bodies AFTER the filter chain has run
        String requestBody = new String(wrappedRequest.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(wrappedResponse.getContentAsByteArray(), StandardCharsets.UTF_8);

        log.info("""
                \n--- REQUEST ---
                Method : {} {}
                Headers: Content-Type={}
                Body   : {}
                --- RESPONSE ---
                Status : {}
                Body   : {}
                ----------------""",
                request.getMethod(),
                request.getRequestURI(),
                request.getHeader("Content-Type"),
                requestBody.isBlank() ? "(none)" : requestBody,
                response.getStatus(),
                responseBody.isBlank() ? "(none)" : responseBody);

        // IMPORTANT: copy cached response body back so the client actually receives it
        wrappedResponse.copyBodyToResponse();
    }
}

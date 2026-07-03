package com.example.rest_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final List<String> LOG_HEADERS = Arrays.asList(
            "User-Agent",
            "Content-Type",
            "Accept",
            "Authorization",
            "Accept-Language"
    );

    // Maximum size of request body to cache (in bytes)
    private static final int MAX_PAYLOAD_SIZE = 1000000; // 1MB

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // FIX: Added MAX_PAYLOAD_SIZE parameter
        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request, MAX_PAYLOAD_SIZE);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            logRequest(requestWrapper, duration);
            logResponse(responseWrapper);

            // Important: Copy body back to response
            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, long duration) {
        log.info("========== HTTP REQUEST ==========");
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getRequestURI());
        log.info("Query String: {}", request.getQueryString() != null ? request.getQueryString() : "None");
        log.info("Duration: {} ms", duration);

        // Log Headers
        log.info("Headers:");
        LOG_HEADERS.forEach(header -> {
            String value = request.getHeader(header);
            if (value != null) {
                log.info("  {}: {}", header, value);
            }
        });

        // Log Request Body
        String requestBody = getRequestBody(request);
        if (requestBody != null && !requestBody.isEmpty()) {
            log.info("Request Body: {}", requestBody);
        }

        log.info("==================================");
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        log.info("========== HTTP RESPONSE ==========");
        log.info("Status: {}", response.getStatus());

        // Log Response Body
        String responseBody = getResponseBody(response);
        if (responseBody != null && !responseBody.isEmpty()) {
            if (responseBody.length() > 1000) {
                responseBody = responseBody.substring(0, 1000) + "... [TRUNCATED]";
            }
            log.info("Response Body: {}", responseBody);
        }

        log.info("===================================");
    }

    private String getRequestBody(ContentCachingRequestWrapper request) {
        byte[] content = request.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }
        try {
            return new String(content, request.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            return "[UNSUPPORTED ENCODING]";
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response) {
        byte[] content = response.getContentAsByteArray();
        if (content.length == 0) {
            return null;
        }
        try {
            return new String(content, response.getCharacterEncoding());
        } catch (UnsupportedEncodingException e) {
            return "[UNSUPPORTED ENCODING]";
        }
    }
}
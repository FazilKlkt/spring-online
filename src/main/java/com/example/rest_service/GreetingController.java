package com.example.rest_service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;


@Slf4j
@RestController              
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/test")    
    public String sayTest(){
        return "Test is working!\n";
    }

    @RequestMapping("/echo")
    public Map<String, Object> echo(@RequestBody(required = false) Map<String, Object> body,
                                    @RequestParam Map<String, String> queryParams,
                                    @RequestHeader Map<String, String> headers,
                                    HttpServletRequest request) {

        log.info("=== ECHO Request ===");
        log.info("Method: {}", request.getMethod());
        log.info("URI: {}", request.getRequestURI());

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("method", request.getMethod());
        response.put("path", request.getRequestURI());
        response.put("queryParams", queryParams);
        response.put("headers", headers);
        response.put("body", body != null ? body : "No body");
        response.put("timestamp", Instant.now().toString());

        return response;
    }
}

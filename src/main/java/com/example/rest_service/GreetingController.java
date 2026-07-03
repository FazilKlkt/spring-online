package com.example.rest_service;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController              
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/hello")    
    public String sayHello() {
        return "Hello, Spring Boot!\n";
    }
    
    @GetMapping("/test")    
    public String sayTest() {
        return "Test is working NEW!\n";
    }

    @PostMapping("/same")
    public Map<String,Object> returnSameBody(@RequestBody Map<String,Object> req){
        return req;
    }
}

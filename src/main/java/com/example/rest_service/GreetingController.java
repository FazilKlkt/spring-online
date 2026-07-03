package com.example.rest_service;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Slf4j
@RestController              
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/hello")    
    public String sayHello() {
        log.info("/hello endpoint called");
        return "Hello, Spring Boot!\n";
    }
    
    @GetMapping("/test")    
    public String sayTest(){
        log.info("/test endpoint called");
        return "Test is working NEW!\n";
    }

    @PostMapping("/same")
    public Map<String,Object> returnSameBody(@RequestBody Map<String,Object> req){
        log.info("/same endpoint called");
        return req;
    }
}

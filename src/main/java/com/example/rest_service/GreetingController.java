package com.example.rest_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController              
@RequestMapping("/api")
public class GreetingController {

    @GetMapping("/hello")    
    public String sayHello() {
        return "Hello, Spring Boot!\n";
    }
    @GetMapping("/wadu")
    public Object returnObj(){
        return Map.of(
                "message","hello bro",
                "status",true,
                "data",Map.of(
                    "id",1,
                    "name","aman"
                    )
                );
    }
    
    @GetMapping("/test")    
    public String sayTest() {
        return "Test is working\n";
    }
}

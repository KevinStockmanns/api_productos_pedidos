package com.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PruebaController {
    
    @GetMapping("/index")
    public String index(){
        return "Index con seguridad";
    }

    @GetMapping("/index2")
    public String index2(){
        return "Index sin seguridad";
    }
}

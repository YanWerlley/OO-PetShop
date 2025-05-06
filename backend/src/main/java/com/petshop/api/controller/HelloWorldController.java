package com.petshop.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String hello() {
        return "Hello World! Salve";
    }
    
    @GetMapping("/docs-info")
    public String docsInfo() {
        return "A documentação completa do projeto está disponível em /docs";
    }
}

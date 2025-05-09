package com.petshop.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador para exibir a documentação do projeto
 */
@Controller
@RequestMapping("/docs")
public class DocumentationController {

    @GetMapping("/backlog")
    public ModelAndView backlog() {
        ModelAndView modelAndView = new ModelAndView("backlog");
        return modelAndView;
    }
    
    @GetMapping("/uml")
    public ModelAndView uml() {
        ModelAndView modelAndView = new ModelAndView("uml");
        return modelAndView;
    }
    
    @GetMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("docs");
        return modelAndView;
    }
}

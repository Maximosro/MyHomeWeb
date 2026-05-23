package com.myhomeweb.controller;

import com.myhomeweb.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    @Transactional(readOnly = true)
    public String dashboard(Model model) {
        model.addAttribute("categories", categoryService.findAllOrdered());
        return "dashboard";
    }
}

package com.myhomeweb.controller;

import com.myhomeweb.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;

    @GetMapping("/")
    @Transactional(readOnly = true)
    public String dashboard(Model model) {
        var categories = categoryService.findAllOrdered();
        categories.forEach(c -> c.getLinks().size()); // force lazy init before session closes
        model.addAttribute("categories", categories);
        return "dashboard";
    }
}

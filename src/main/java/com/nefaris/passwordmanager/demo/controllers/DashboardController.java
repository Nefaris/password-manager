package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        Domain domain = new Domain();

        model.addAttribute("domain", domain);
        model.addAttribute("displayUsername", StringUtils.capitalize(username));
        model.addAttribute("domains", userService.getDomains(username));

        return "dashboard";
    }
}

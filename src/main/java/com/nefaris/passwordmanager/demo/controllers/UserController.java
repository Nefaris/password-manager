package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.nefaris.passwordmanager.demo.models.RegisterFormData;
import org.thymeleaf.util.StringUtils;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/login"})
    public String index() {
        return userService.isUserAuthenticated() ? "redirect:/dashboard" : "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (userService.isUserAuthenticated()) return "redirect:/dashboard";

        RegisterFormData registerFormData = new RegisterFormData();
        model.addAttribute("registerFormData", registerFormData);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterFormData registerFormData) {
        if (userService.isUserAuthenticated()) return "redirect:/dashboard";
        return userService.registerUser(registerFormData) ? "redirect:/login" : "/register";
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

    @PostMapping("/addDomain")
    public String addDomain(Authentication authentication, @ModelAttribute Domain domain) {
        userService.addDomain(authentication.getName(), domain);
        return "redirect:/dashboard";
    }

    @PostMapping("/removeDomain")
    public String removeDomain(Authentication authentication, @RequestParam String domain, @RequestParam String domainUsername) {
        System.out.println(domain);
        userService.removeDomain(authentication.getName(), domain, domainUsername);
        return "redirect:/dashboard";
    }
}

package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.RegisterFormData;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
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
}

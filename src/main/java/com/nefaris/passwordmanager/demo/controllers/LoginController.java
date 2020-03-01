package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/login"})
    public String index() {
        return userService.isUserAuthenticated() ? "redirect:/dashboard" : "login";
    }
}

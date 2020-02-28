package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.nefaris.passwordmanager.demo.models.RegisterFormData;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        RegisterFormData registerFormData = new RegisterFormData();
        model.addAttribute("registerFormData", registerFormData);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterFormData registerFormData) {
        boolean usernameExists = userRepository.findUserByUsername(registerFormData.getUsername()).isPresent();
        boolean emailExists = userRepository.findUserByEmail(registerFormData.getEmail()).isPresent();

        if (!usernameExists && !emailExists) {
            userService.addNewUser(new User(
                    registerFormData.getUsername(),
                    registerFormData.getPassword(),
                    true,
                    "USER",
                    registerFormData.getEmail()
            ));

            return "redirect:/login";
        }

        return "/register";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/remember")
    public String remember() {
        return "remember";
    }
}

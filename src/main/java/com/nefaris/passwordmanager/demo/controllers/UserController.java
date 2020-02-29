package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.User;
import com.nefaris.passwordmanager.demo.repositories.UserRepository;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.nefaris.passwordmanager.demo.models.RegisterFormData;
import org.thymeleaf.util.StringUtils;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }

        return "login";
    }

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }

        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }

        RegisterFormData registerFormData = new RegisterFormData();
        model.addAttribute("registerFormData", registerFormData);
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterFormData registerFormData) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/dashboard";
        }

        boolean usernameExists = userRepository.findUserByUsername(registerFormData.getUsername()).isPresent();
        boolean emailExists = userRepository.findUserByEmail(registerFormData.getEmail()).isPresent();

        if (!usernameExists && !emailExists) {
            User newUser = new User(registerFormData);
            userService.addNewUser(newUser);

            return "redirect:/login";
        }

        return "/register";
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        String username = authentication.getName();
        model.addAttribute("displayUsername", StringUtils.capitalize(username));

        userRepository.findUserByUsername(username).ifPresent(user -> model.addAttribute("domains", user.getDomains()));

        return "dashboard";
    }

    @GetMapping("/remember")
    public String remember() {
        // todo remember password page
        return "remember";
    }
}

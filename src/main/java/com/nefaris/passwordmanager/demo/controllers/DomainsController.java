package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DomainsController {

    private final UserService userService;

    public DomainsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addDomain")
    public String addDomain(Authentication authentication, @ModelAttribute Domain domain) {
        userService.addDomain(authentication.getName(), domain);
        return "redirect:/dashboard";
    }

    @PostMapping("/removeDomain")
    public String removeDomain(Authentication authentication, @RequestParam String domain, @RequestParam String domainUsername) {
        userService.removeDomain(authentication.getName(), domain, domainUsername);
        return "redirect:/dashboard";
    }
}

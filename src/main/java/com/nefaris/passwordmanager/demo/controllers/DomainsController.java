package com.nefaris.passwordmanager.demo.controllers;

import com.nefaris.passwordmanager.demo.models.Domain;
import com.nefaris.passwordmanager.demo.services.DomainService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DomainsController {

    private final DomainService domainService;

    public DomainsController(DomainService domainService) {
        this.domainService = domainService;
    }

    @PostMapping("/addDomain")
    public String addDomain(Authentication authentication, @ModelAttribute Domain domain) {
        domainService.addDomain(authentication.getName(), domain);
        return "redirect:/dashboard";
    }

    @PostMapping("/removeDomain")
    public String removeDomain(Authentication authentication, @RequestParam String domain, @RequestParam String domainUsername) {
        domainService.removeDomain(authentication.getName(), domain, domainUsername);
        return "redirect:/dashboard";
    }
}

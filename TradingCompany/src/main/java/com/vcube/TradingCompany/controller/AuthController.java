package com.vcube.TradingCompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // 🔐 Login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // 🔁 Redirect root to dashboard
    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }
}
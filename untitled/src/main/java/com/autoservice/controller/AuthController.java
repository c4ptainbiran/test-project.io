package com.autoservice.controller;

import com.autoservice.model.User;
import com.autoservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@Controller
public class AuthController {
    @Autowired private UserService userService;

    @GetMapping("/")
    public String index(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            User user = userService.findByLogin(authentication.getName());
            if (user != null && "CLIENT".equals(user.getRole())) {
                return "redirect:/client/profile";
            } else if (user != null && "EMPLOYEE".equals(user.getRole())) {
                return "redirect:/employee/dashboard";
            }
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "role", required = false) String role,
                        @RequestParam(value = "error", required = false) String error,
                        Model model) {
        model.addAttribute("role", role);
        if (error != null) model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    @GetMapping("/register")
    public String register(@RequestParam String role, Model model) {
        model.addAttribute("role", role);
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@RequestParam String role,
                             @RequestParam String fullName,
                             @RequestParam String birthDate,
                             @RequestParam String login,
                             @RequestParam String password,
                             Model model) {
        if (userService.isLoginTaken(login)) {
            model.addAttribute("error", "Логин уже занят");
            model.addAttribute("role", role);
            return "register";
        }
        User user = new User();
        user.setFullName(fullName);
        user.setBirthDate(LocalDate.parse(birthDate));
        user.setLogin(login);
        user.setRole(role.toUpperCase()); // один раз, корректно
        userService.register(user, password);
        return "redirect:/login?role=" + role;
    }
}
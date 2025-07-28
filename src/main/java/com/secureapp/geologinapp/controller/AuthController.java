package com.secureapp.geologinapp.controller;

import com.secureapp.geologinapp.model.User;
import com.secureapp.geologinapp.service.EmailService;
import com.secureapp.geologinapp.service.OtpService;
import com.secureapp.geologinapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private OtpService otpService;
    @Autowired private EmailService emailService;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, HttpServletRequest request, Model model) {
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already exists.");
            return "register";
        }

        // Store location/IP
        user.setAllowedLocation(request.getRemoteAddr());
        user.setEnabled(false);
        userService.saveUser(user);

        String otp = otpService.generateOtp();
        otpService.saveOtp(user.getEmail(), otp);
        emailService.sendOtpEmail(user.getEmail(), otp);

        model.addAttribute("email", user.getEmail());
        return "verify";
    }

    @PostMapping("/verify")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        if (otpService.verifyOtp(email, otp)) {
            userService.findByEmail(email).ifPresent(userService::enableUser);
            return "redirect:/login?verified";
        }
        model.addAttribute("error", "Invalid or expired OTP.");
        model.addAttribute("email", email);
        return "verify";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/welcome")
    public String welcomePage(HttpServletRequest request, Model model) {
        String currentIp = request.getRemoteAddr();
        String email = request.getUserPrincipal().getName();
        User user = userService.findByEmail(email).orElse(null);

        if (user == null || !user.isEnabled()) {
            return "redirect:/login?error";
        }

        if (!currentIp.equals(user.getAllowedLocation())) {
            model.addAttribute("error", "Access denied from this location: " + currentIp);
            return "login";
        }

        model.addAttribute("username", user.getUsername());
        return "welcome";
    }
}

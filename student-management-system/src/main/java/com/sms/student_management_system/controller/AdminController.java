package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Admin;
import com.sms.student_management_system.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public String loginProcess(@RequestParam String username, 
                               @RequestParam String password, 
                               HttpSession session,
                               Model model) {
        
        // Check if credentials match
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            session.setAttribute("loggedInAdmin", username);
            return "redirect:/students";
        } else {
            model.addAttribute("error", "Invalid username or password!");
            return "login";
        }
    }

    @GetMapping("/admin/register")
    public String adminRegForm(Model model) {
        model.addAttribute("admin", new Admin());
        return "admin_register";
    }
    @GetMapping("/logout")
public String logout(HttpSession session) {
    session.invalidate(); // Session එක මකලා දානවා
    return "redirect:/login"; // ආපහු Login එකට
}

    @PostMapping("/admin/register")
    public String saveAdmin(@ModelAttribute("admin") Admin admin) {
        adminRepository.save(admin);
        return "redirect:/"; // මුල් පිටුවට යවනවා
    }
    @GetMapping("/login")
public String loginPage() {
    return "login"; 
}
}
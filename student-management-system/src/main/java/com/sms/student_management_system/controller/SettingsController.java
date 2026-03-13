package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Admin;
import com.sms.student_management_system.repository.AdminRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/settings")
    public String settingsPage(HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute("loggedInAdmin");
        Admin admin = adminRepository.findByUsername(username);

        if (admin != null) {
            model.addAttribute("fullName", admin.getFullName());
            model.addAttribute("phone", admin.getPhone());
            model.addAttribute("department", admin.getDepartment());
            model.addAttribute("email", admin.getEmail());
        }

        return "settings";
    }

    @PostMapping("/settings")
    public String saveSettings(@RequestParam(required = false) String fullName,
                               @RequestParam(required = false) String phone,
                               @RequestParam(required = false) String department,
                               @RequestParam(required = false) String email,
                               @RequestParam(required = false) String currentPassword,
                               @RequestParam(required = false) String newPassword,
                               @RequestParam(required = false) String confirmPassword,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }

        String username = (String) session.getAttribute("loggedInAdmin");
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            redirectAttributes.addFlashAttribute("error", "Admin account not found.");
            return "redirect:/settings";
        }

        // Update profile fields
        if (fullName != null && !fullName.trim().isEmpty()) {
            admin.setFullName(fullName.trim());
        }
        if (phone != null) {
            admin.setPhone(phone.trim());
        }
        if (department != null) {
            admin.setDepartment(department.trim());
        }
        if (email != null && !email.trim().isEmpty()) {
            admin.setEmail(email.trim());
        }

        // Handle password change
        if (currentPassword != null && !currentPassword.isEmpty()) {
            if (!admin.getPassword().equals(currentPassword)) {
                redirectAttributes.addFlashAttribute("error", "Current password is incorrect.");
                return "redirect:/settings";
            }
            if (newPassword == null || newPassword.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "New password cannot be empty.");
                return "redirect:/settings";
            }
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error", "New password and confirmation do not match.");
                return "redirect:/settings";
            }
            admin.setPassword(newPassword);
        }

        adminRepository.save(admin);
        redirectAttributes.addFlashAttribute("success", "Settings saved successfully!");
        return "redirect:/settings";
    }
}

package com.sms.student_management_system.controller;

import com.sms.student_management_system.entity.Program;
import com.sms.student_management_system.repository.ProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    @GetMapping("/programs")
    public String listPrograms(@RequestParam(value = "keyword", required = false) String keyword, HttpSession session, Model model) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        // 1. මුලින්ම සියලුම දත්ත අරගන්න (Stats සඳහා)
        List<Program> allPrograms = programRepository.findAll();

        // 2. Search keyword එක අනුව පෙරහන් කරන්න
        List<Program> filteredPrograms;
        if (keyword != null && !keyword.isEmpty()) {
            filteredPrograms = programRepository.searchByNameOrCode(keyword);
        } else {
            filteredPrograms = allPrograms;
        }

        // 3. Stats ගණනය කිරීම (මේවා 'allPrograms' මත පදනම් විය යුතුයි)
        long totalPrograms = allPrograms.size();
        long activePrograms = allPrograms.stream().filter(p -> "Active".equalsIgnoreCase(p.getStatus())).count();
        int totalStudents = allPrograms.stream().mapToInt(p -> p.getEnrolled() != 0 ? p.getEnrolled() : 0).sum();
        long accreditedCount = allPrograms.stream().filter(p -> "Accredited".equalsIgnoreCase(p.getAccreditation())).count();

        // 4. Model එකට දත්ත යැවීම
        model.addAttribute("programs", filteredPrograms);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPrograms", totalPrograms);
        model.addAttribute("activePrograms", activePrograms);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("accreditedCount", accreditedCount);
        model.addAttribute("program", new Program());

        return "programs";
    }

    // Edit Form එක පෙන්වීමට
@GetMapping("/programs/edit/{id}")
public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }
    Program program = programRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid program Id:" + id));
    model.addAttribute("program", program);
    return "edit_program"; // අපි අලුතින් edit_program.html කියලා පේජ් එකක් හදමු
}

// Edit කළ දත්ත සේව් කිරීමට
@PostMapping("/programs/update/{id}")
public String updateProgram(@PathVariable Long id, @ModelAttribute("program") Program program, HttpSession session) {
    if (session.getAttribute("loggedInAdmin") == null) {
        return "redirect:/login";
    }
    program.setId(id); // ID එක Set කිරීම අනිවාර්යයි
    programRepository.save(program);
    return "redirect:/programs";
}
    @PostMapping("/programs/save")
    public String saveProgram(@ModelAttribute("program") Program program, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        programRepository.save(program);
        return "redirect:/programs";
    }

    @GetMapping("/programs/delete/{id}")
    public String deleteProgram(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("loggedInAdmin") == null) {
            return "redirect:/login";
        }
        programRepository.deleteById(id);
        return "redirect:/programs";
    }
}
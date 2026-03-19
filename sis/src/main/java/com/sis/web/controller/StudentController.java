package com.sis.web.controller;

import com.sis.entity.Student;
import com.sis.repository.StudentRepository;
import com.sis.web.form.StudentForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/new")
    public String newStudent(Model model) {
        if (!model.containsAttribute("studentForm")) {
            model.addAttribute("studentForm", new StudentForm());
        }
        return "students/new";
    }

    @PostMapping
    public String createStudent(@ModelAttribute StudentForm studentForm,
                                RedirectAttributes ra) {
        Student s = new Student();
        s.setStudentCode(studentForm.getStudentCode());
        s.setFullName(studentForm.getFullName());
        s.setAddress(studentForm.getAddress());

        studentRepository.save(s);
        ra.addFlashAttribute("toast", "Added student successfully.");
        return "redirect:/scores";
    }
}


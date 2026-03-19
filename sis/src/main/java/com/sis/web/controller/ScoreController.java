package com.sis.web.controller;

import com.sis.entity.Student;
import com.sis.entity.StudentScore;
import com.sis.entity.Subject;
import com.sis.repository.StudentRepository;
import com.sis.repository.StudentScoreRepository;
import com.sis.repository.SubjectRepository;
import com.sis.web.form.ScoreForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/scores")
public class ScoreController {

    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentScoreRepository studentScoreRepository;

    public ScoreController(StudentRepository studentRepository,
                           SubjectRepository subjectRepository,
                           StudentScoreRepository studentScoreRepository) {
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentScoreRepository = studentScoreRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("scores", studentScoreRepository.findAllWithStudentAndSubject());
        return "scores/list";
    }

    @GetMapping("/new")
    public String newScore(Model model) {
        if (!model.containsAttribute("scoreForm")) {
            model.addAttribute("scoreForm", new ScoreForm());
        }
        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("subjects", subjectRepository.findAll());
        return "scores/new";
    }

    @PostMapping
    public String createScore(@ModelAttribute ScoreForm scoreForm,
                              RedirectAttributes ra) {
        Student student = studentRepository.findById(scoreForm.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Subject subject = subjectRepository.findById(scoreForm.getSubjectId())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));

        StudentScore ss = new StudentScore();
        ss.setStudent(student);
        ss.setSubject(subject);
        ss.setScore1(scoreForm.getScore1());
        ss.setScore2(scoreForm.getScore2());

        studentScoreRepository.save(ss);
        ra.addFlashAttribute("toast", "Added score successfully.");
        return "redirect:/scores";
    }
}


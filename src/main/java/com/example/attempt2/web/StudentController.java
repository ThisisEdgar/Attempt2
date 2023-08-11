package com.example.attempt2.web;

import com.example.attempt2.entities.Student;
import com.example.attempt2.repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@SessionAttributes({"a","e"})
@Controller
@AllArgsConstructor
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    static int num = 0;

    @GetMapping(path= "/index")
    public String students(Model model, HttpSession session,@RequestParam(name="keyword",defaultValue = "") String keyword)
    {
        List<Student> students;
        if (keyword.isEmpty())
        {
            students = studentRepository.findAll();
        }
        else
        {
            long key = Long.parseLong(keyword);
            students = studentRepository.findStudentById(key);
        }

        model.addAttribute("listStudents",students);
        if (session.getAttribute("errorMessage") != null)
        {
            String variableValue = (String) session.getAttribute("errorMessage");
            if (!variableValue.isEmpty())
            {
                model.addAttribute("errorMessage", variableValue);
                session.setAttribute("errorMessage",null);

            }
            else
            {

            }
        }


        return "myStudents";
    }
    @GetMapping(path = "/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model)
    {
        List<Student> studentSelected = studentRepository.findStudentById(id);
        if (!studentSelected.isEmpty())
        {
            model.addAttribute("myStudent",studentSelected.get(0));
            return "edit";
        }
        else
        {
            String message = "No student found";
            model.addAttribute("errorMessage",message);
            return "errorHandling";
        }

    }
    @PostMapping(path = "saveEdit")
    public String saveEdit(Student student, Model model,HttpSession session) {
        studentRepository.save(student);
        List<Student> students = studentRepository.findAll();
        model.addAttribute("listStudents", students);


        session.setAttribute("errorMessage","1 student edited");

        return "redirect:/index";
    }

    @PostMapping(path = "delete/{id}")
    public String deleteThis(@PathVariable Long id, HttpSession session) {
        List<Student> student = studentRepository.findStudentById(id);
        studentRepository.delete(student.get(0));


        session.setAttribute("errorMessage","1 student deleted");

        return "redirect:/index";
    }

    @GetMapping("/addStudent")
    public String formStudents(Model model){
        model.addAttribute("student", new Student());
        return "addStudent";
    }
    @PostMapping(path="/save")
    public String saveStudent(Model model, Student student, BindingResult
            bindingResult, ModelMap mm, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "addStudent";
        }
        else {
            studentRepository.save(student);
            session.setAttribute("errorMessage","1 student added");
            if (num == 2)
            {
                mm.put("e", 2);
                mm.put("a", 0);
            }
            else
            {
                mm.put("a", 1);
                mm.put("e", 0);
            }


            return "redirect:index";
        }

    }
}

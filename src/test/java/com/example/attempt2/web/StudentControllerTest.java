package com.example.attempt2.web;

import com.example.attempt2.entities.Student;
import com.example.attempt2.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class StudentControllerTest {
    Student student;

    private MockMvc mockMvc;

    @Mock
    View mockView;
    @Mock
    StudentRepository studentRepository;

    @InjectMocks
    StudentController studentController;
    @BeforeEach
    void setup() throws ParseException {
        student = new Student();
        student.setId(1L);
        student.setName("John");

        String sDate1 = "2012/11/11";
        Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
        student.setDob(date1);
        student.setPassed(true);
        student.setGpa(3.5);


        MockitoAnnotations.openMocks(this);

        mockMvc = standaloneSetup(studentController).setSingleView(mockView).build();

    }
    @Test
    void students() throws Exception {
        List<Student> studentsList = new ArrayList<Student>();
        studentsList.add(student);
        studentsList.add(student);
        when(studentRepository.findAll()).thenReturn(studentsList);
        mockMvc.perform(get("/index"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listStudents", studentsList))
                .andExpect(view().name("myStudents"))
                .andExpect(model().attribute("listStudents", hasSize(2)));
        verify(studentRepository, times(1)).findAll();
        //verifyNoInteractions(studentRepository);
    }
    @Test
    void editStudent() {

    }

    @Test
    void saveEdit() {
    }

    @Test
    void deleteThis() {
    }

    @Test
    void formStudents() {
    }

    @Test
    void saveStudent() {
    }


}
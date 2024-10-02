package com.mcddhub.backend.controller;

import com.mcddhub.backend.service.QuestionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(QuestionController.class)
@AutoConfigureMockMvc
class QuestionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private QuestionService questionService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void name() {
    }
}
package com.example.questionpro.controller;

import com.example.questionpro.dto.Comment;
import com.example.questionpro.dto.StoriesData;
import com.example.questionpro.service.StoriesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = StoriesController.class)
@AutoConfigureMockMvc
class StoriesControllerTest {

    List<StoriesData> storiesDataList;

    List<Comment> commentList;

    @MockBean
    StoriesService storiesService;
    @Autowired
    private StoriesController storiesController;
    private MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(storiesController)
                .build();
        StoriesData storiesData = new StoriesData("title", "url", 123, 1234, "tonny");
        storiesDataList = new ArrayList<>();
        storiesDataList.add(storiesData);
        Comment comment = new Comment("title", "raju");
        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    void getStories() throws Exception {
        when(storiesService.getStoriesData()).thenReturn(storiesDataList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/question-pro/top-stories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        String expected = new ObjectMapper().writeValueAsString(storiesDataList);
        assertEquals(expected, result);
    }

    @Test
    void getPastStories() throws Exception{
        when(storiesService.getPastStoriesData()).thenReturn(storiesDataList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/question-pro/past-stories")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        String expected = new ObjectMapper().writeValueAsString(storiesDataList);
        assertEquals(expected, result);
    }

    @Test
    void getComments() throws Exception{
        when(storiesService.getComments(any())).thenReturn(commentList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/question-pro/comments?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(1))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        assertNotNull(result);
        String expected = new ObjectMapper().writeValueAsString(commentList);
        assertEquals(expected, result);
    }
}
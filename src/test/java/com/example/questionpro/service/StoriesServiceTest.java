package com.example.questionpro.service;

import com.example.questionpro.controller.StoriesController;
import com.example.questionpro.dto.Comment;
import com.example.questionpro.dto.StoriesData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StoriesService.class)
@AutoConfigureMockMvc
class StoriesServiceTest {

    @MockBean
    private StoriesService storiesService;

    private List<StoriesData> storiesDataList;

    private List<Comment> commentList;

    @BeforeEach
    public void init(){
        StoriesData storiesData = new StoriesData("title", "url", 123, 1234, "tonny");
        storiesDataList = new ArrayList<>();
        storiesDataList.add(storiesData);
        Comment comment = new Comment("title", "raju");
        commentList = new ArrayList<>();
        commentList.add(comment);
    }

    @Test
    void getStoriesData() throws Exception{
        when(storiesService.getStoriesData()).thenReturn(storiesDataList);
        List<StoriesData> result = storiesService.getStoriesData();

        assertEquals(storiesDataList, result);
    }

    @Test
    void getPastStoriesData() {
        when(storiesService.getPastStoriesData()).thenReturn(storiesDataList);
        List<StoriesData> result = storiesService.getPastStoriesData();

        assertEquals(storiesDataList, result);
    }

    @Test
    void getComments() {
        when(storiesService.getComments(any())).thenReturn(commentList);
        List<Comment> result = storiesService.getComments(12);

        assertEquals(commentList, result);
    }

}
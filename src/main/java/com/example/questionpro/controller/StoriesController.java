package com.example.questionpro.controller;

import com.example.questionpro.dto.Comment;
import com.example.questionpro.dto.StoriesData;
import com.example.questionpro.service.StoriesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/question-pro")
public class StoriesController {

    public static Logger LOGGER = LoggerFactory.getLogger(StoriesController.class);

    @Autowired
    private StoriesService storiesService;

    @GetMapping(path="/top-stories")
    public ResponseEntity<List<StoriesData>> getStories() throws Exception{
        return ResponseEntity.ok().body(storiesService.getStoriesData());
    }

    @GetMapping(path="/past-stories")
    public ResponseEntity<List<StoriesData>> getPastStories(){
        return ResponseEntity.ok().body(storiesService.getPastStoriesData());
    }

    @GetMapping(path="/comments")
    public ResponseEntity<List<Comment>> getComments(@RequestParam("id") Integer id){
        return ResponseEntity.ok().body(storiesService.getComments(id));
    }
}

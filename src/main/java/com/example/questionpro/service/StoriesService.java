package com.example.questionpro.service;

import com.example.questionpro.cache.CacheData;
import com.example.questionpro.config.AppConfig;
import com.example.questionpro.dto.Comment;
import com.example.questionpro.dto.StoriesData;
import com.example.questionpro.job.StoriesDataJob;
import com.example.questionpro.job.StoriesDataJobResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.lang.Runnable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
public class StoriesService {

    public static Logger LOGGER = LoggerFactory.getLogger(StoriesService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppConfig appConfig;

    public List<StoriesData> getStoriesData() throws Exception {
        CacheData cacheData = CacheData.getCacheData();
        if(cacheData.getPresentStories().size() == 0) {
            LOGGER.info("Going to Call rest API {}",appConfig.getUrlStories());
            List<Integer> storiesList = restTemplate.getForObject(appConfig.getUrlStories(), List.class);
            List<StoriesData> storiesDataList = new ArrayList<>();
            List<StoriesDataJob> jobList = new ArrayList<>();
            int noOfJob = storiesList.size()/(int)appConfig.getBatch();
            int start=0;
            ExecutorService executorService = Executors.newFixedThreadPool(noOfJob);
            for(int i=0;i<noOfJob;i++){
                StoriesDataJob storiesDataJob = new StoriesDataJob(storiesList.subList(start,start+(int)appConfig.getBatch()), appConfig.getUrlId(), restTemplate);
                jobList.add(storiesDataJob);
                start = start+(int)appConfig.getBatch();
            }

            List<Future<StoriesDataJobResult>> result = executorService.invokeAll(jobList);

            for(Future<StoriesDataJobResult> job : result){
                storiesDataList.addAll(job.get().getStoriesDataList());
            }
            LOGGER.info("Stories API Data fetch complete");
            Collections.sort(storiesDataList);
            for(int i=0; i<appConfig.getTopSize(); i++){
                StoriesData story = storiesDataList.get(i);
                story.setType(null);
                story.setKids(null);
                cacheData.getPresentStories().add(story);
            }
            Runnable exampleRunnable = new Runnable(){
                @Override
                public void run() {
                    List<StoriesData> list = CacheData.getCacheData().getPresentStories();
                    cacheData.getPastStories().clear();
                    List<StoriesData> pastStories = new ArrayList<>();
                    for(StoriesData story :  list) {
                        pastStories.add(story);
                    }
                    CacheData.getCacheData().setPastStories(pastStories);
                    CacheData.getCacheData().getPresentStories().clear();
                }
            };
            Executors.newSingleThreadScheduledExecutor().schedule(exampleRunnable, appConfig.getCacheTiming(), TimeUnit.MINUTES);
        }
        LOGGER.info("return Stories top list response");
        return cacheData.getPresentStories();
    }

    public List<StoriesData> getPastStoriesData(){
        LOGGER.info("Going to return past data");
        return CacheData.getCacheData().getPastStories();
    }

    public List<Comment> getComments(Integer id){
        LOGGER.info("Going to fetch comments data");
        String url = String.format(appConfig.getUrlId(), id.intValue());
        StoriesData storiesData = restTemplate.getForEntity(url, StoriesData.class).getBody();
        List<Comment> commentList = new ArrayList<>();
        for(Integer kid : storiesData.getKids()){
            url = String.format(appConfig.getUrlId(), kid.intValue());
            Comment comment= restTemplate.getForEntity(url, Comment.class).getBody();
            commentList.add(comment);
        }
        Collections.sort(commentList);
        LOGGER.info("Comments data detch done");
        List<Comment> result = new ArrayList<>();
        for(int i=0;i< appConfig.getTopSize();i++){
            Comment comment = commentList.get(i);
            comment.setKids(null);
            result.add(comment);
        }
        LOGGER.info("now returning final comments data");
        return result;
    }
}

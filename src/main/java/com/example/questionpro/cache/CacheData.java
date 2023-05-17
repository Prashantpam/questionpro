package com.example.questionpro.cache;

import com.example.questionpro.dto.StoriesData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CacheData {

    private List<StoriesData> presentStories;

    private List<StoriesData> pastStories;

    private static CacheData cacheData;
    private CacheData(){
        this.presentStories = new ArrayList<>();
        this.pastStories = new ArrayList<>();
    }

    public static CacheData getCacheData(){
        if(cacheData == null){
            cacheData = new CacheData();
            return cacheData;
        }else {
            return cacheData;
        }
    }

    public List<StoriesData> getPresentStories(){
        return this.presentStories;
    }

    public void setPresentStories(List<StoriesData> presentStories){
        this.presentStories = presentStories;
    }

    public List<StoriesData> getPastStories(){
        return this.pastStories;
    }

    public void setPastStories(List<StoriesData> pastStories){
        this.pastStories = pastStories;
    }
}

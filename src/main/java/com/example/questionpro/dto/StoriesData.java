package com.example.questionpro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoriesData implements Comparable<StoriesData> {

    private String title;
    private String url;
    private long score;
    private long time;
    private String by;
    private String type;
    private List<Integer> kids;

    public StoriesData(){}

    public StoriesData(String title, String url, long score, long time, String by){
        this.title = title;
        this.url = url;
        this.score = score;
        this.time = time;
        this.by = by;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public int compareTo(StoriesData storiesData){
        return (int)(storiesData.score - this.score);
    }
}

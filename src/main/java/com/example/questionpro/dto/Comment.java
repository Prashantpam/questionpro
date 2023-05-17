package com.example.questionpro.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Comment implements Comparable<Comment>{

    private String text;
    private List<Integer> kids;
    private String by;

    public Comment(){ }

    public Comment(String text, String by) {
        this.text = text;
        this.by = by;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getKids() {
        return kids;
    }

    public void setKids(List<Integer> kids) {
        this.kids = kids;
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public int compareTo(Comment comment){
        if(comment.kids != null && kids != null) {
            return comment.kids.size() - kids.size();
        }else if(comment.kids == null && kids != null){
            return -1*kids.size();
        }else if(comment.kids !=null && kids == null){
            return comment.kids.size();
        } else{
            return 0;
        }
    }
}

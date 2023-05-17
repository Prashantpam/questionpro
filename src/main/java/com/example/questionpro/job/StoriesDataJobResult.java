package com.example.questionpro.job;

import com.example.questionpro.dto.StoriesData;
import java.util.List;

public class StoriesDataJobResult {

    private List<StoriesData> storiesDataList;

    public StoriesDataJobResult(List<StoriesData> storiesDataList){
        this.storiesDataList = storiesDataList;
    }

    public List<StoriesData> getStoriesDataList() {
        return this.storiesDataList;
    }
}

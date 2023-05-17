package com.example.questionpro.job;

import com.example.questionpro.dto.StoriesData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class StoriesDataJob implements Callable<StoriesDataJobResult> {

    public static Logger LOGGER = LoggerFactory.getLogger(StoriesDataJob.class);

    private List<Integer> storiesList;

    private String urlId;

    private RestTemplate restTemplate;

    public StoriesDataJob(List<Integer> storiesList, String urlId, RestTemplate restTemplate){
        this.storiesList = storiesList;
        this.urlId = urlId;
        this.restTemplate = restTemplate;
    }

    public StoriesDataJobResult call() throws InterruptedException, ExecutionException {
        List<StoriesData> storiesDataList = new ArrayList<>();
        for(Integer story:storiesList){
            try {
                String url = String.format(urlId, story.intValue());
                StoriesData storiesData = restTemplate.getForEntity(url, StoriesData.class).getBody();
                if ("story".equals(storiesData.getType())) {
                    storiesDataList.add(storiesData);
                }
            }catch(RestClientException e){
                LOGGER.error("Exception on rest call {}",e.getMessage());
            }
        }
        return new StoriesDataJobResult(storiesDataList);
    }
}

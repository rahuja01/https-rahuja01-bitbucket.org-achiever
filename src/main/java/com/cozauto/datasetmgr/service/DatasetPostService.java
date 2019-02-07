package com.cozauto.datasetmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.web.client.DefaultRestTemplateExchangeTagsProvider;
import org.springframework.web.client.RestTemplate;

public class DatasetPostService {

    RestTemplate restTemplate;
    @Autowired
    private DefaultRestTemplateExchangeTagsProvider restTemplateTagConfigurer;

    public DatasetPostService() {
        restTemplate = new RestTemplate();
    }
}

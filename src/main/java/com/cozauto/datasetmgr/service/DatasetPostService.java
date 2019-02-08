package com.cozauto.datasetmgr.service;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.web.client.DefaultRestTemplateExchangeTagsProvider;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

public class DatasetPostService {

    RestTemplate restTemplate;
    @Autowired
    private DefaultRestTemplateExchangeTagsProvider restTemplateTagConfigurer;

    public DatasetPostService() {
        restTemplate = new RestTemplate();
    }


    public void postDataSetService(String datasetId, String json) {

        System.out.println("Begin /GET request!");
        StringBuilder sb = new StringBuilder();
        sb.append("http://vautointerview.azurewebsites.net/api/") ;
        sb.append(datasetId);
        sb.append("/answer");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("Content-Type", MediaType.APPLICATION_JSON.toString());

        HttpEntity<String> entity = new HttpEntity<String>(json,headers);

        // Send the request as POST

        //String answer = restTemplate.postForObject(sb.toString(), entity, String.class);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                sb.toString(), HttpMethod.POST, entity,
                String.class);

        System.out.println("Response for Post Request <<<<<<<<<<: " + responseEntity.getBody());

    }



}

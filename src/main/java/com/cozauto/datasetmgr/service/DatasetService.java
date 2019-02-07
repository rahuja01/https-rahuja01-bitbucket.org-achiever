package com.cozauto.datasetmgr.service;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.web.client.DefaultRestTemplateExchangeTagsProvider;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


public class DatasetService {

    RestTemplate restTemplate;
    @Autowired
    private DefaultRestTemplateExchangeTagsProvider restTemplateTagConfigurer;

    public DatasetService() {
        restTemplate = new RestTemplate();
    }

    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();


    public String getDataSetId() {

        System.out.println("Begin /GET request!");
        String getUrl = "http://vautointerview.azurewebsites.net/api/datasetId";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.parseMediaType("application/json")));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        // Send the request as GET

        ResponseEntity<String> getResponse = restTemplate.getForEntity(getUrl, String.class);
        if (getResponse.getBody() != null) {
            System.out.println("Response for Get Request: " + getResponse.getBody().toString());
        } else {
            System.out.println("Response for Get Request: NULL");
        }

        JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
        JSONObject jsonObject = new JSONObject(jsonTokener);
        Object datasetId = jsonObject.get("datasetId");
        System.out.println("datasetId>>>>>" + datasetId.toString());
        
        return datasetId.toString();


    }
}

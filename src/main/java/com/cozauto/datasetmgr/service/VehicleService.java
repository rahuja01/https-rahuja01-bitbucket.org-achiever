package com.cozauto.datasetmgr.service;

import com.cozauto.datasetmgr.model.Vehicle;
import com.cozauto.datasetmgr.model.Vehicles;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleService {

    RestTemplate restTemplate;

    public VehicleService() {
        restTemplate = new RestTemplate();
    }

    public List<String> getVehicleIds(String datasetId) {

        StringBuilder stringBuilder = new StringBuilder();
        //Vehicles vehicles = new Vehicles();
        List<String> lstVehicleIds = new ArrayList<>();
        String[] arr = null;

        System.out.println("Begin /GET getVehicleIds!");
        stringBuilder.append("http://vautointerview.azurewebsites.net/api/");

        stringBuilder.append(datasetId);
        stringBuilder.append("/vehicles");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.parseMediaType("application/json")));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

        // Send the request as GET

        ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);
        if (getResponse.getBody() != null) {
            //System.out.println("Response for Get Request: " + getResponse.getBody().toString());
        } else {
            //System.out.println("Response for Get Request: NULL");
        }

        //JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
        JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
        JSONObject jsonObject = new JSONObject(jsonTokener);
        Object vehicleIds = jsonObject.get("vehicleIds");
        //System.out.println("vehicleIds>>>>>" + vehicleIds.toString());

        if (vehicleIds.toString() != null) {

            arr = vehicleIds.toString().split(",");

        }

        for (int i = 0; i < arr.length; i++) {

            lstVehicleIds.add(arr[i]);

        }


        return lstVehicleIds;


    }
}




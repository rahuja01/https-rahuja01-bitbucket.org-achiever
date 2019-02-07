package com.cozauto.datasetmgr.service;

import com.cozauto.datasetmgr.model.Vehicle;
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

public class VehicleDealerService {

    RestTemplate restTemplate;

    public VehicleDealerService() {
        restTemplate = new RestTemplate();
    }

    public List<Vehicle> getVehicleDelaerInfo(String datasetId, List<String> vehicleList) {

        StringBuilder stringBuilder = new StringBuilder();
        //Vehicles vehicles = new Vehicles();
        List<String> lstVehicleIds = new ArrayList<>();
        String[] arr = null;

        System.out.println("Begin /GET getVehicleIds!");
        stringBuilder.append("http://vautointerview.azurewebsites.net/api/");

        stringBuilder.append(datasetId);
        stringBuilder.append("/vehicles/");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.parseMediaType("application/json")));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);


        for(String vehicle : vehicleList){

            stringBuilder.append(vehicle);

            ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);

            //JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
            JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
            JSONObject jsonObject = new JSONObject(jsonTokener);
            Object vehicleIds = jsonObject.get("vehicleId");
            Object year = jsonObject.get("year");
            Object make = jsonObject.get("make");
            Object model = jsonObject.get("model");
            Object dealerId = jsonObject.get("dealerId");
            //System.out.println("vehicleIds>>>>>" + vehicleIds.toString());



        }

        // Send the request as GET

        ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);





        return null;


    }


}

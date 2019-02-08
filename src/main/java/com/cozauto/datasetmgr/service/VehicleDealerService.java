package com.cozauto.datasetmgr.service;

import com.cozauto.datasetmgr.model.Vehicle;
import com.google.common.util.concurrent.ListenableFuture;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleDealerService {

    RestTemplate restTemplate;

    public VehicleDealerService() {
        restTemplate = new RestTemplate();
    }

    public List<Vehicle> getVehicleDelaerInfo(String datasetId, List<String> vehicleList) {


        //Vehicles vehicles = new Vehicles();
        List<String> lstVehicleIds = new ArrayList<>();
        String[] arr = null;

        List<Vehicle> lstVehicle = new ArrayList<>();

        System.out.println("Begin /GET getVehicleIds!");


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.parseMediaType("application/json")));
        headers.setContentType(MediaType.parseMediaType("application/json"));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
        restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

/*        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("http://vautointerview.azurewebsites.net/api/");

        stringBuilder.append(datasetId);
        stringBuilder.append("/vehicles/");*/

        /*List<String> listOfResponses = vehicleList.stream()
                .parallel().map({vehicle ->
                        ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);
                })
        .collect(Collectors.toList());*/

/*
        List<ListenableFuture<ResponseEntity<String>>> responseFutures = new ArrayList<>();
        for (int i=0; i<vehicleList.size(); i++) {
            // FIXME studentId is not used
            ListenableFuture<ResponseEntity<String>> responseEntityFuture =
                    restTemplate.exchange(stringBuilder.toString(),HttpMethod.GET,entity, String.class);
            responseFutures.add(responseEntityFuture);
        }
// now all requests were send, so we can process the responses
        List<String> listOfResponses = new ArrayList<>();
        for (ListenableFuture<ResponseEntity<String>> future: responseFutures) {
            try {
                String respBody = future.get().getBody();
                listOfResponses.add(respBody);
            } catch (Exception ex) {
                throw new ApplicationException("Exception while making Rest call.", ex);
            }
        }

*/








        for(/*String vehicle : vehicleList*/ int i=0; i<vehicleList.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("http://vautointerview.azurewebsites.net/api/");

            stringBuilder.append(datasetId);
            stringBuilder.append("/vehicles/");

            //http://vautointerview.azurewebsites.net/api/ag1WUxiN1gg/vehicles/1580284399
            if (i == 0) {
                String result = vehicleList.get(0).substring(1, vehicleList.get(0).length());
                stringBuilder.append(result);
            } else if (i == vehicleList.size() - 1) {
                String result = vehicleList.get(i).substring(0, vehicleList.get(i).length() - 1);
                stringBuilder.append(result);
            } else {
                stringBuilder.append(vehicleList.get(i));
            }




            ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);

            if(getResponse.getStatusCode()!= HttpStatus.BAD_REQUEST){
                Vehicle vehicle1 = new Vehicle();

                JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
                JSONObject jsonObject = new JSONObject(jsonTokener);
                Object vehicleId = jsonObject.get("vehicleId");
                Object year = jsonObject.get("year");
                Object make = jsonObject.get("make");
                Object model = jsonObject.get("model");
                Object dealerId = jsonObject.get("dealerId");

                vehicle1.setVehicleId(Long.parseLong(vehicleId.toString()));
                vehicle1.setDealerId(Long.parseLong(dealerId.toString()));
                vehicle1.setYear(Integer.parseInt(year.toString()));
                vehicle1.setMake(make.toString());
                vehicle1.setModel(model.toString());

                lstVehicle.add(vehicle1);
            }


        }

        return lstVehicle;

    }


}

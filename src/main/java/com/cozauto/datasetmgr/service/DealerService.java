package com.cozauto.datasetmgr.service;

import com.cozauto.datasetmgr.model.Dealer;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.web.client.DefaultRestTemplateExchangeTagsProvider;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class DealerService {

    RestTemplate restTemplate;
    @Autowired
    private DefaultRestTemplateExchangeTagsProvider restTemplateTagConfigurer;

    public DealerService() {
        restTemplate = new RestTemplate();
    }

    public List<Dealer> getDealerInfo(Set<String> dealerIds, String datasetId) {

        List<Dealer> lstDelarInfo = new ArrayList<>();

        for(String dealerId : dealerIds){

            StringBuilder stringBuilder = new StringBuilder();

            System.out.println("Begin /GET getVehicleIds!");
            stringBuilder.append("http://vautointerview.azurewebsites.net/api/");

            stringBuilder.append(datasetId);
            stringBuilder.append("/dealers/");
            stringBuilder.append(dealerId);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(org.springframework.http.MediaType.parseMediaType("application/json")));
            headers.setContentType(MediaType.parseMediaType("application/json"));
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

            MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
            mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));
            restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

            // Send the request as GET

            ResponseEntity<String> getResponse = restTemplate.getForEntity(stringBuilder.toString(), String.class);

            Dealer dealer = new Dealer();

            //JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
            JSONTokener jsonTokener = new JSONTokener(getResponse.getBody());
            JSONObject jsonObject = new JSONObject(jsonTokener);
            Object dealerId1 = jsonObject.get("dealerId");
            Object name = jsonObject.get("name");

                dealer.setDealerId(Long.parseLong(dealerId1.toString()));
                dealer.setName(name.toString());
                lstDelarInfo.add(dealer);


        }



        //System.out.println("vehicleIds>>>>>" + vehicleIds.toString());


        return lstDelarInfo;
    }


}

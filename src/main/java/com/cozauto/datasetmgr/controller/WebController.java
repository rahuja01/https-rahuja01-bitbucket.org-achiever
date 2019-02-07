package com.cozauto.datasetmgr.controller;

import com.cozauto.datasetmgr.model.Dataset;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @RequestMapping(value = "/api", method = RequestMethod.GET)
    public Dataset get(@RequestParam("datasetId") String datasetId) {
        String info = String.format("/GET info: id=%d", datasetId);
        System.out.println(info);
        return new Dataset(info);
    }
}

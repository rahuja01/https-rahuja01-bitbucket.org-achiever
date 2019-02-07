package com.cozauto.datasetmgr;

import com.cozauto.datasetmgr.service.DatasetService;
import com.cozauto.datasetmgr.service.VehicleService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DatasetmgrApplication {

	public static void main(String[] args) {

		//SpringApplication.run(DatasetmgrApplication.class, args);

		//retrieving the dataSetId
		DatasetService datasetService = new DatasetService();
		String datasetId = datasetService.getDataSetId();

		//retrieving the vehicleIds
		VehicleService vehicleService = new VehicleService();
		List<String> vehicleIds = new ArrayList<>();

		if (datasetId != null) {
			vehicleIds = vehicleService.getVehicleIds(datasetId);
		}

		vehicleIds.forEach(value->System.out.println("Vehicle Ids are >>>>>>>"+value));


	}

}


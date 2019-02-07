package com.cozauto.datasetmgr;

import com.cozauto.datasetmgr.model.Vehicle;
import com.cozauto.datasetmgr.service.DatasetService;
import com.cozauto.datasetmgr.service.VehicleDealerService;
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


        //retrieving the list of Vehicle for the datasetId and Vehicles
        VehicleDealerService vehicleDealerService = new VehicleDealerService();
        List<Vehicle> lstVehicle = new ArrayList<>();

        lstVehicle = vehicleDealerService.getVehicleDelaerInfo(datasetId, vehicleIds);

        if(lstVehicle!=null){

            lstVehicle.forEach(vehicle->
                    System.out.println("vehicle Make is *****"+vehicle.getMake() +
                    "\n" + "vehicle Model is ******" + vehicle.getModel() +
                    "\n" + "vehicle Id is *****" + vehicle.getVehicleId() +
                    "\n" + "dealer Id for the vehicle is *****" + vehicle.getDealerId() +
                                    "\n" + "Vehicle year is *******" + vehicle.getYear()
                    )

            );

        }


	}

}


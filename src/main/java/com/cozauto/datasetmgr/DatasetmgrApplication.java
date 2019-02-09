package com.cozauto.datasetmgr;

import com.cozauto.datasetmgr.model.Dealer;
import com.cozauto.datasetmgr.model.Vehicle;
import com.cozauto.datasetmgr.service.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.json.Json;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class DatasetmgrApplication {

	public static void main(String[] args) {

		long start = System.currentTimeMillis();
		System.out.println("PROCESS STARTS #######################"+start);



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

		//vehicleIds.forEach(value->System.out.println("Vehicle Ids are >>>>>>>"+value));


        //retrieving the list of Vehicle for the datasetId and Vehicles
        VehicleDealerService vehicleDealerService = new VehicleDealerService();
        List<Vehicle> lstVehicle = new ArrayList<>();

        lstVehicle = vehicleDealerService.getVehicleDelaerInfo(datasetId, vehicleIds);

/*        if(lstVehicle!=null){

            lstVehicle.forEach(vehicle->
                    System.out.println("vehicle Make is *****"+vehicle.getMake() +
                    "\n" + "vehicle Model is ******" + vehicle.getModel() +
                    "\n" + "vehicle Id is *****" + vehicle.getVehicleId() +
                    "\n" + "dealer Id for the vehicle is *****" + vehicle.getDealerId() +
                                    "\n" + "Vehicle year is *******" + vehicle.getYear()
                    )

            );

        }*/

        StringBuilder dealerIdStringCommaSeparated = new StringBuilder();

        for(int i=0; i< lstVehicle.size(); i++){

			dealerIdStringCommaSeparated.append(lstVehicle.get(i).getDealerId());
			dealerIdStringCommaSeparated.append(",");

		}

		//Using a Set to remove duplicate dealerIds.
		Set<String> set = new HashSet<>();

		String[] dealerIdArray = dealerIdStringCommaSeparated.toString().split(",");
		DealerService dealerService = new DealerService();

		for(String dealerIds : dealerIdArray){
			set.add(dealerIds);

		}

		// retrieve dealerInfo - dealerId and Name
		List<Dealer> lstDealerInfo = dealerService.getDealerInfo(set, datasetId );

		// Find Vehicle info based out of dealerId and assign to list.

		//System.out.println("PROCESS 2 #######################"+stringDate2);

		Vehicle[] vehicles = new Vehicle[lstDealerInfo.size()];

		Set<Vehicle> myset = lstVehicle.stream().collect(Collectors.toSet());


		for(int i=0; i<lstDealerInfo.size(); i++) {

			for (Vehicle vehicle : myset) {

				if (lstDealerInfo.get(i).getDealerId() == vehicle.getDealerId()) {
					if(lstDealerInfo.contains(vehicle))
						continue;

					//Vehicle[] vehicles = null;

					Dealer dealer = new Dealer();
					Vehicle vehicleToAdd = new Vehicle();
					dealer.setDealerId(lstDealerInfo.get(i).getDealerId());
					dealer.setName(lstDealerInfo.get(i).getName());

					vehicleToAdd.setModel(vehicle.getModel());
					vehicleToAdd.setMake(vehicle.getMake());
					vehicleToAdd.setYear(vehicle.getYear());
					vehicleToAdd.setDealerId(vehicle.getDealerId());
					vehicleToAdd.setVehicleId(vehicle.getVehicleId());
					vehicles[i] = vehicleToAdd;
					dealer.setVehicle(vehicles);

					lstDealerInfo.set(i, dealer);

				}
			}

		}

		String multipleDealers = "";

		/*GsonBuilder gsonBuilder = new GsonBuilder();

		Gson gson = gsonBuilder.create();

		String json = gson.toJson(lstDealerInfo);*/

		//JSONObject jsonObject = new JSONObject();

		JSONArray JsonDealersArray = new JSONArray();

		for (/*Dealer dealer : lstDealerInfo*/int i=0; i<lstDealerInfo.size();i++) {

			Vehicle[] vehicle = lstDealerInfo.get(i).getVehicle();

			LinkedHashMap map = new LinkedHashMap();
			//JSONObject jsonArrayDealer = new JSONObject();

			map.put("name", lstDealerInfo.get(i).getName());
			map.put("dealerId", lstDealerInfo.get(i).getDealerId());
			JSONArray jsonArrayVehicle = new JSONArray();



			for (int j = 0; j < vehicle.length; j++) {

				/*String vehicleJson = Json.createObjectBuilder()
						.add("vehicleId", vehicle[i].getVehicleId())
						.add("year", vehicle[i].getYear())
						.add("make", vehicle[i].getMake())
						.add("model", vehicle[i].getModel()).build().toString();

				String dealerJson = Json.createArrayBuilder()
						.add("dealerId", dealer.getDealerId())
						.add("name", dealer.getName())
						.add("vehicles", vehicleJson)
						.build()
						.toString();*/

				/*multipleDealers =  multipleDealers + Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
						.add("dealerId", lstDealerInfo.get(i).getDealerId())
						.add("name", lstDealerInfo.get(i).getName())
						.add("vehicles",Json.createArrayBuilder()
						.add(Json.createObjectBuilder()
						.add("vehicleId", vehicle[j].getVehicleId())
						.add("year", vehicle[j].getYear())
						.add("make", vehicle[j].getMake())
						.add("model", vehicle[j].getModel()))))
				.build().toString();
				multipleDealers.replaceAll("\\\\", "");*/
				LinkedHashMap<String, Object> m = new LinkedHashMap(4);


				//m.put("dealerId", lstDealerInfo.get(i).getDealerId());
				//m.put("name", lstDealerInfo.get(i).getName());
				//m.put("vehicles", jsonArray);

				m.put("vehicleId", Long.toString(vehicle[j].getVehicleId()));
				m.put("year", Long.toString(vehicle[j].getYear()));
				m.put("make", vehicle[j].getMake());
				m.put("model", vehicle[j].getModel());

				/*m.entrySet().stream()
						.forEach(vehicleMap ->jsonArrayVehicle.put(vehicleMap));*/

				GsonBuilder gsonBuilder = new GsonBuilder();

				Gson gson = gsonBuilder.create();

				String string = gson.toJson(m,LinkedHashMap.class);

				jsonArrayVehicle.put(i,string.replaceAll("\\\\", ""));

			}
			map.put("vehicles", jsonArrayVehicle);
			JsonDealersArray.put(map);
		}

		JSONObject jsonObjectFinal = new JSONObject();
		jsonObjectFinal.put("dealers", JsonDealersArray);
		//multipleDealers.replaceAll("\\\\", "");
		//System.out.println(multipleDealers);


		/*String temp = Json.createObjectBuilder()
				.add("dealers", multipleDealers).build().toString();
		temp.replaceAll("\\\\", "");*/

		/*String temp = Json.createObjectBuilder()
				.add("dealers", multipleDealers).build().toString();
		//String finalTemp = "{" +
		temp.replaceAll("\\\\\\\\", "");*/

		System.out.println(jsonObjectFinal.toString());

		DatasetPostService datasetPostService = new DatasetPostService();
		datasetPostService.postDataSetService(datasetId,jsonObjectFinal.toString());

		//System.out.println("JSON Final ------------"+multipleDealers);

		long finish = System.currentTimeMillis();

		System.out.println("Time finish ------------"+finish);

		long timeElapsed = finish - start;

		System.out.println("Time ELAPSED ------------"+timeElapsed);


	}

}


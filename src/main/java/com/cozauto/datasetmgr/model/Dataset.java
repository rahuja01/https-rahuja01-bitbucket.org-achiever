package com.cozauto.datasetmgr.model;

import java.io.Serializable;

public class Dataset {

    public Dataset(String datasetId){
        this.datasetId = datasetId;

    }

    private String datasetId;

    public String getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(String datasetId) {
        this.datasetId = datasetId;
    }

    @Override
    public String toString() {
        String info = String.format("DataSet Info: datasetId = %d", datasetId);
        return info;
    }
}

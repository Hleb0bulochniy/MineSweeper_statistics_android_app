package com.example.sashenkov_9.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MapSaveListModelDTO implements Serializable {

    private List<MapSaveModelDTO> mapSaveList = new ArrayList<>();

    public MapSaveListModelDTO() {
    }

    public MapSaveListModelDTO(List<MapSaveModelDTO> mapSaveList) {
        this.mapSaveList = mapSaveList;
    }

    public List<MapSaveModelDTO> getMapSaveList() {
        return mapSaveList;
    }

    public void setMapSaveList(List<MapSaveModelDTO> mapSaveList) {
        this.mapSaveList = mapSaveList;
    }
}

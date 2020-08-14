package com.safetynet.Alerts.dto;

import java.util.List;

public class FloodDTO {

    List<HouseholdsByStationDTO> householdsByStation;

    public FloodDTO(List<HouseholdsByStationDTO> householdsByStation) {
        this.householdsByStation = householdsByStation;
    }

    public List<HouseholdsByStationDTO> getHouseholdsByStation() {
        return householdsByStation;
    }

    public void setHouseholdsByStation(List<HouseholdsByStationDTO> householdsByStation) {
        this.householdsByStation = householdsByStation;
    }
}

package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.Household;

import java.util.List;

public class HouseholdsByStationDTO {

    private int station;
    private List<Household> households;

    public HouseholdsByStationDTO(int station, List<Household> households) {
        this.station = station;
        this.households = households;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<Household> getHouseholds() {
        return households;
    }

    public void setHouseholds(List<Household> households) {
        this.households = households;
    }
}



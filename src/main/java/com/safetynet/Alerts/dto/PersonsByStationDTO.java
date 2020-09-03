package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonStation;

import java.util.List;

public class PersonsByStationDTO {

    private List<PersonStation> personsByStation;
    private int adultNumber;
    private int childNumber;

    public PersonsByStationDTO(List<PersonStation> personsByStation, int adultNumber, int childNumber) {
        this.personsByStation = personsByStation;
        this.adultNumber = adultNumber;
        this.childNumber = childNumber;
    }

    public PersonsByStationDTO() {
    }

    public List<PersonStation> getPersonsByStation() {
        return personsByStation;
    }

    public void setPersonsByStation(List<PersonStation> personsByStation) {
        this.personsByStation = personsByStation;
    }

    public int getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(int adultNumber) {
        this.adultNumber = adultNumber;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(int childNumber) {
        this.childNumber = childNumber;
    }
}

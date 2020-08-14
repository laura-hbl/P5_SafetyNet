package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonAddress;

import java.util.List;

public class FireDTO {

    private int station;
    private List<PersonAddress> persons;

    public FireDTO(int station, List<PersonAddress> persons) {
        this.station = station;
        this.persons = persons;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<PersonAddress> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonAddress> persons) {
        this.persons = persons;
    }
}


package com.safetynet.Alerts.model;

import java.util.List;

public class Household {

    private String address;

    private List<PersonAddress> personsList;

    public Household(String address, List<PersonAddress> personsList) {
        this.address = address;
        this.personsList = personsList;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<PersonAddress> getPersonsList() {
        return personsList;
    }

    public void setPersonsList(List<PersonAddress> personsList) {
        this.personsList = personsList;
    }
}

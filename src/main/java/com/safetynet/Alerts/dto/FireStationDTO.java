package com.safetynet.Alerts.dto;

public class FireStationDTO {

    private String address;
    private int station;

    public FireStationDTO(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public FireStationDTO() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

}

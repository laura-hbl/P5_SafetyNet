package com.safetynet.Alerts.dto;

/**
 * Permits the storage and retrieving data of a fire station.
 *
 * @author Laura Habdul
 */
public class FireStationDTO {

    /**
     * Address of the house covered by this fire station.
     */
    private String address;

    /**
     * The station number of the fire station.
     */
    private int station;

    /**
     * Constructor of class FireStationDTO.
     * Initialize address and station.
     *
     * @param address address of the house covered by this fire station
     * @param station station number of the fire station
     */
    public FireStationDTO(final String address, final int station) {
        this.address = address;
        this.station = station;
    }

    /**
     * Empty constructor of class FireStationDTO.
     */
    public FireStationDTO() {
    }

    /**
     * Getter of FireStationDTO.address.
     *
     * @return The address of the house covered by this fire station.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of FireStationDTO.address.
     *
     * @param address address of the house covered to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of FireStationDTO.station.
     *
     * @return The station number of the fire station.
     */
    public int getStation() {
        return station;
    }

    /**
     * Setter of FireStationDTO.address.
     *
     * @param station station number of the fire station to set
     */
    public void setStation(final int station) {
        this.station = station;
    }
}

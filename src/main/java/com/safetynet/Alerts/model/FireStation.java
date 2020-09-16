package com.safetynet.Alerts.model;

/**
 * Permits the storage and retrieving data of a fire station.
 *
 * @author Laura Habdul
 */
public class FireStation {

    /**
     * Address of the house covered by this fire station.
     */
    private String address;

    /**
     * The station number of the fire station.
     */
    private int station;

    /**
     * Constructor of class FireStation.
     * Initialize address and station.
     *
     * @param address address of the house covered by this fire station
     * @param station station number of the fire station
     */
    public FireStation(final String address, final int station) {
        this.address = address;
        this.station = station;
    }

    /**
     * Getter of FireStation.address.
     *
     * @return The address of the house covered by this fire station.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of FireStation.address.
     *
     * @param address address of the house covered to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of FireStation.station.
     *
     * @return The station number of the fire station.
     */
    public int getStation() {
        return station;
    }

    /**
     * Setter of FireStation.address.
     *
     * @param station station number of the fire station to set
     */
    public void setStation(final int station) {
        this.station = station;
    }
}

package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.Household;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of households covered by a given fire station number
 * as well as the station number itself.
 *
 * @author Laura Habdul
 */
public class HouseholdsByStationDTO {

    /**
     * The station number which covers the list of households
     */
    private int station;

    /**
     * List of all households covered by the given fire station number.
     */
    private List<Household> households;

    /**
     * Constructor of class HouseholdsByStationDTO.
     * Initialize station and households.
     *
     * @param station station number which covers the list of households
     * @param households list of all households covered by the given fire station number.
     */
    public HouseholdsByStationDTO(final int station, final List<Household> households) {
        this.station = station;
        this.households = households;
    }

    /**
     * Empty constructor of class HouseholdsByStationDTO.
     */
    public HouseholdsByStationDTO() {
    }

    /**
     * Getter of HouseholdsByStationDTO.station.
     *
     * @return The station number which covers the list of households
     */
    public int getStation() {
        return station;
    }

    /**
     * Setter of HouseholdsByStationDTO.station.
     *
     * @param station station number which covers the list of households to set
     */
    public void setStation(final int station) {
        this.station = station;
    }

    /**
     * Getter of HouseholdsByStationDTO.households.
     *
     * @return The list of all households covered by the given fire station number
     */
    public List<Household> getHouseholds() {
        return households;
    }

    /**
     * Setter of HouseholdsByStationDTO.households.
     *
     * @param households list of all households covered by the given fire station number to set
     */
    public void setHouseholds(final List<Household> households) {
        this.households = households;
    }
}
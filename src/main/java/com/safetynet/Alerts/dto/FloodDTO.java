package com.safetynet.Alerts.dto;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of households covered by given fire stations,
 * grouping persons by address.
 *
 * @author Laura Habdul
 */
public class FloodDTO {

    /**
     * List of households covered by given fire stations.
     */
    private List<HouseholdsByStationDTO> householdsByStation;

    /**
     * Constructor of class FireStationDTO.
     * Initialize householdsByStation.
     *
     * @param householdsByStation
     */
    public FloodDTO(final List<HouseholdsByStationDTO> householdsByStation) {
        this.householdsByStation = householdsByStation;
    }

    /**
     * Empty constructor of class FloodDTO.
     */
    public FloodDTO() {
    }

    /**
     * Getter of FloodDTO.householdsByStation.
     *
     * @return The list of households covered by given fire stations
     */
    public List<HouseholdsByStationDTO> getHouseholdsByStation() {
        return householdsByStation;
    }

    /**
     * Setter of FloodDTO.householdsByStation.
     *
     * @param householdsByStation list of households covered by given fire stations to set
     */
    public void setHouseholdsByStation(List<HouseholdsByStationDTO> householdsByStation) {
        this.householdsByStation = householdsByStation;
    }
}

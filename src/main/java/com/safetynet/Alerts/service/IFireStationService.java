package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;

import java.util.List;

public interface IFireStationService {

    /**
     * Saves a new FireStation.
     *
     * @param fireS the fire station to be saved
     * @return The fire station saved converted to a FireStationDTO object
     */
    FireStationDTO createFireStation(FireStationDTO fireS);

    /**
     * Updates a registered FireStation.
     *
     * @param fireS the fire station to be updated
     * @return The fire station updated converted to a FireStationDTO object
     */
    FireStationDTO updateFireStation(FireStationDTO fireS);

    /**
     * Deletes a registered FireStation.
     *
     * @param address address covered by the fire station
     * @param station fire station number
     */
    void deleteFireStation(String address, Integer station);

    /**
     * Retrieves the fire station that covers the given address.
     *
     * @param address address of the house covered by the fire station to be retrieved
     * @return The fire station that covers the given address retrieved
     */
    FireStation getFireStationByAddress(String address);

    /**
     * Retrieves the fire station with the given key identifier.
     *
     * @param address address of the house covered by the fire station to be retrieved
     * @param station station number of the fire station to be retrieved
     * @return The fire station retrieved converted to a FireStationDTO object
     */
    FireStationDTO getFireStationByKeyId(String address, Integer station);

    /**
     * Retrieves the list of address covered by the given station number.
     *
     * @param station fire station number
     * @return The addresses covered by the given station number
     */
    List<String> getAddressesByStation(int station);
}

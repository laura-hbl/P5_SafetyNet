package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import com.safetynet.Alerts.util.DTOConverter;
import com.safetynet.Alerts.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that allow interaction between FireStation business logic and FireStation repository.
 *
 * @author Laura Habdul
 * @see IFireStationService
 */
@Service
public class FireStationService implements IFireStationService {

    /**
     * FireStationService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(FireStation.class);

    /**
     * FireStationRepository instance.
     */
    private final FireStationRepository fireStationRepository;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class FireStationService.
     * Initialize fireStationRepository, modelConverter and dtoConverter.
     *
     * @param fireStationRepository FireStationRepository instance
     * @param modelConverter        ModelConverter instance
     * @param dtoConverter          DTOConverter instance
     */
    @Autowired
    public FireStationService(final FireStationRepository fireStationRepository, final ModelConverter modelConverter,
                              final DTOConverter dtoConverter) {
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
        this.fireStationRepository = fireStationRepository;
    }

    /**
     * Calls FireStationRepository's find method to checks if the given fire station is already registered, if so
     * DataAlreadyRegisteredException is thrown. Else, it is converted to model object by calling ModelConverter's
     * toFireStation and then saved by calling FireStationRepository's save method.
     *
     * @param fireS the fire station to be saved
     * @return The fire station saved converted to a FireStationDTO object
     */
    public FireStationDTO createFireStation(final FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.createFireStation");
        FireStation fireFound = fireStationRepository.find(fireS.getAddress(), fireS.getStation());

        if (fireFound != null) {
            throw new DataAlreadyRegisteredException("FireStation already registered");
        }
        FireStation fireToSave = modelConverter.toFireStation(fireS);
        FireStation fireSaved = fireStationRepository.save(fireToSave);

        return dtoConverter.toFireStationDTO(fireSaved);
    }

    /**
     * Calls FireStationRepository's findByAddress method to check if the given fire station to update is registered,
     * if so it is updated. Else, DataNotFoundException is thrown.
     *
     * @param fireS fire station to be updated
     * @return The fire station updated converted to a FireStationDTO object
     */
    public FireStationDTO updateFireStation(final FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.updateFireStation");
        FireStation fireToUpdate = fireStationRepository.findByAddress(fireS.getAddress());

        if (fireToUpdate == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireToUpdate.setStation(fireS.getStation());

        return dtoConverter.toFireStationDTO(fireToUpdate);
    }

    /**
     * Calls FireStationRepository's find method to check if the fire station with given key Id is registered, if so
     * it is deleted by calling FireStationRepository's delete method. Else, DataNotFoundException is thrown.
     *
     * @param address address of the house covered by the fire station
     * @param station fire station number
     */
    public void deleteFireStation(final String address, final Integer station) {
        LOGGER.debug("Inside FireStationService.deleteFireStation");
        FireStation fireStationToDelete = fireStationRepository.find(address, station);

        if (fireStationToDelete == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireStationRepository.delete(fireStationToDelete);
    }

    /**
     * Retrieves the fire station with the given key id by calling FireStationRepository's
     * find method and returned by being first converted to a model object by calling ModelConverter's
     * toFireStation. If it is not found, DataNotFoundException is thrown.
     *
     * @param address address of the house covered by the fire station to be retrieved
     * @param station station number of the fire station to be retrieved
     * @return The fire station retrieved converted to a FireStationDTO object
     */
    public FireStationDTO getFireStationByKeyId(final String address, final Integer station) {
        LOGGER.debug("Inside FireStationService.getFireStation for fireStation: " + address, station);
        FireStation fireStation = fireStationRepository.find(address, station);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " + address);
        }

        return dtoConverter.toFireStationDTO(fireStation);
    }

    /**
     * Retrieves the fire station that covers the given address by calling FireStationRepository's
     * findByAddress method. If it is not found, DataNotFoundException is thrown.
     *
     * @param address address of the house covered by the fire station to be retrieved
     * @return The fire station that covers the given address retrieved
     */
    public FireStation getFireStationByAddress(final String address) {
        LOGGER.debug("Inside FireStationService.getFireStationByAddress for address :" + address);
        FireStation fireStation = fireStationRepository.findByAddress(address);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " + address);
        }

        return fireStation;
    }

    /**
     * Retrieves the list of address covered by the given station number by first calling FireStationRepository's
     * findByStation method and loops through the fire station list in order to add each address to an ArrayList.
     * If not found, DataNotFoundException is thrown.
     *
     * @param station fire station number
     * @return The addresses covered by the given station number
     */
    public List<String> getAddressesByStation(final int station) {
        LOGGER.debug("Inside FireStationService.getAddressesByStation for station {}", station);
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();

        if (fireStations.isEmpty()) {
            throw new DataNotFoundException("Failed to get the addresses mapped to station : " + station);
        }

        for (FireStation fireS : fireStations) {
            addresses.add(fireS.getAddress());
        }

        return addresses;
    }
}

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

@Service
public class FireStationService implements IFireStationService {

    private static final Logger LOGGER = LogManager.getLogger(FireStation.class);

    private final FireStationRepository fireStationRepository;

    private final ModelConverter modelConverter;

    private final DTOConverter dtoConverter;


    @Autowired
    public FireStationService(FireStationRepository fireStationRepository, ModelConverter modelConverter, DTOConverter dtoConverter) {
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
        this.fireStationRepository = fireStationRepository;
    }

    public FireStationDTO createFireStation(FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.createFireStation");
        FireStation fireFound = fireStationRepository.find(fireS.getAddress(), fireS.getStation());

        if (fireFound != null) {
            throw new DataAlreadyRegisteredException("FireStation already registered");
        }
        FireStation fireToSave = modelConverter.toFireStation(fireS);
        FireStation fireSaved = fireStationRepository.save(fireToSave);

        return dtoConverter.toFireStationDTO(fireSaved);
    }

    public FireStationDTO updateFireStation(FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.updateFireStation");
        FireStation fireToUpdate = fireStationRepository.findByAddress(fireS.getAddress());

        if (fireToUpdate == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireToUpdate.setStation(fireS.getStation());

        return dtoConverter.toFireStationDTO(fireToUpdate);
    }

    public void deleteFireStation(String address, Integer station) {
        LOGGER.debug("Inside FireStationService.deleteFireStation");
        FireStation fireStationToDelete = fireStationRepository.find(address, station);

        if (fireStationToDelete == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireStationRepository.delete(fireStationToDelete);
    }

    public FireStationDTO getFireStationByKeyId(String address, Integer station) {
        LOGGER.debug("Inside FireStationService.getFireStation for fireStation: " +address, station);
        FireStation fireStation = fireStationRepository.find(address, station);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " +address);
        }

        return dtoConverter.toFireStationDTO(fireStation);
    }

    public FireStation getFireStationByAddress(String address) {
        LOGGER.debug("Inside FireStationService.getFireStationByAddress for address :" +address);
        FireStation fireStation = fireStationRepository.findByAddress(address);

        if (fireStation == null) {
            throw new DataNotFoundException("Failed to get the fireStations mapped to address : " +address);
        }

        return fireStation;
    }

    public List<String> getAddressesByStation(int station) {
        LOGGER.debug("Inside FireStationService.getAddressesByStation for station {}", station);
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();

        if (fireStations.isEmpty()) {
            throw new DataNotFoundException("Failed to get the addresses mapped to station : " +station);
        }

        for (FireStation fireS : fireStations) {
            addresses.add(fireS.getAddress());
        }

        return addresses;
    }
}

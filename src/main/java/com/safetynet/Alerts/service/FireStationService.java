package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
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

    @Autowired
    public FireStationService(FireStationRepository fireStationRepository) {
        this.fireStationRepository = fireStationRepository;
    }

    public FireStation createFireStation(FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.createFireStation");
        FireStation fireStationCreated = new FireStation(fireS.getAddress(), fireS.getStation());
        FireStation fireStation = fireStationRepository.find(fireStationCreated);

        if (fireStation != null) {
            throw new DataAlreadyRegisteredException("FireStation already registered");
        }

        return fireStationRepository.save(fireStationCreated);
    }

    public FireStation updateFireStation(FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.updateFireStation");
        FireStation fireStationToUpdate = fireStationRepository.findByAddress(fireS.getAddress());

        if (fireStationToUpdate == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireStationToUpdate.setStation(fireS.getStation());
        return fireStationToUpdate;
    }

    public void deleteFireStation(FireStationDTO fireS) {
        LOGGER.debug("Inside FireStationService.deleteFireStation");
        FireStation fireStation = new FireStation(fireS.getAddress(), fireS.getStation());
        FireStation fireStationToDelete = fireStationRepository.find(fireStation);

        if (fireStationToDelete == null) {
            throw new DataNotFoundException("FireStation not found");
        }

        fireStationRepository.delete(fireStationToDelete);
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

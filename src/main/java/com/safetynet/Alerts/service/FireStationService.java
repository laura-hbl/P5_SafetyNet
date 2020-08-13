package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.repository.FireStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService implements IFireStationService{

    @Autowired
    private FireStationRepository fireStationRepository;

    public FireStation createFireStation(FireStationDTO fireS) {
        FireStation fireStationCreated = new FireStation(fireS.getAddress(), fireS.getStation());
        FireStation fireStation = fireStationRepository.find(fireStationCreated);

        if (fireStation == null) {
            return fireStationRepository.save(fireStationCreated);
        }

        return null;
    }

    public FireStation updateFireStation(FireStationDTO fireS) {
        FireStation fireStation = new FireStation(fireS.getAddress(), fireS.getStation());
        FireStation fireStationUpdated = fireStationRepository.find(fireStation);

        if (fireStationUpdated != null) {
            fireStationUpdated.setStation(fireS.getStation());
            return fireStationUpdated;
        }

        return null;
    }

    public void deleteFireStation(FireStationDTO fireS) {
        FireStation fireStation = new FireStation(fireS.getAddress(), fireS.getStation());
        FireStation fireStationToDelete = fireStationRepository.find(fireStation);

        if (fireStationToDelete != null) {
            fireStationRepository.delete(fireStationToDelete);
        }
    }

    public FireStation getFireStationByAddress(String address) {
        FireStation fireStation = fireStationRepository.findByAddress(address);

        if (fireStation != null) {
            return fireStation;
        }

        return null;
    }

    public List<String> getAddressesByStation(int station) {
        List<FireStation> fireStations = fireStationRepository.findByStation(station);
        List<String> addresses = new ArrayList<>();

        for (FireStation fireS : fireStations) {
                addresses.add(fireS.getAddress());
            }

        return addresses;
    }
}

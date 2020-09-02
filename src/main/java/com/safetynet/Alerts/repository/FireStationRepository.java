package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FireStationRepository {

    private static final Logger LOGGER = LogManager.getLogger(FireStationRepository.class);

    Map<String, FireStation> fireStationMap = new HashMap<>();

    @Autowired
    public FireStationRepository(StoredData storedData) {
        LOGGER.debug("Map FireStationList");
        storedData.getFireStationList().forEach(fireS -> fireStationMap.put(fireS.getAddress() + fireS.getStation(), fireS));
    }

    public FireStation save(FireStation fireS) {
        LOGGER.debug("Inside FireStationRepository.save for fireStation : " +fireS.getAddress(),
                fireS.getStation());
        fireStationMap.put(fireS.getAddress() + fireS.getStation(), fireS);
        return fireS;
    }

    public void delete(FireStation fireS) {
        LOGGER.debug("Inside FireStationRepository.delete for fireStation : " +fireS.getAddress(),
                fireS.getStation());
        fireStationMap.remove(fireS.getAddress() + fireS.getStation());
    }

    public FireStation find(FireStation fireS) {
        LOGGER.debug("Inside FireStationRepository.find for fireStation : " +fireS.getAddress(),
                fireS.getStation());
        return fireStationMap.get(fireS.getAddress() + fireS.getStation());
    }

    public FireStation findByAddress(String address) {
        LOGGER.debug("Inside FireStationRepository.findByAddress for address : " +address);
        Collection<FireStation> fireStations = fireStationMap.values();

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }

        return null;
    }

    public List<FireStation> findByStation(int station) {
        LOGGER.debug("Inside FireStationRepository.findByStation for station : " +station);
        Collection<FireStation> fireStations = fireStationMap.values();
        List<FireStation> fireStationsByStation = new ArrayList<>();

        for (FireStation fireS : fireStations) {
            if (fireS.getStation() == station) {
                fireStationsByStation.add(fireS);
            }
        }

        return fireStationsByStation;
    }
}

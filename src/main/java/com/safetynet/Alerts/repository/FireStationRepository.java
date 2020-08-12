package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class FireStationRepository {

    Map<String, FireStation> fireStationMap = new HashMap<>();

    @Autowired
    public FireStationRepository(StoredData storedData) {
        storedData.getFireStationList().forEach(fireS -> fireStationMap.put(fireS.getStation()
                + fireS.getAddress(), fireS));
    }

    public FireStation save(FireStation fireS) {
        return fireStationMap.put(fireS.getStation() + fireS.getAddress(), fireS);
    }

    public void delete(FireStation fireS) {
        fireStationMap.remove(fireS.getStation() + fireS.getAddress());
    }

    public FireStation find(FireStation fireS) {
        return fireStationMap.get(fireS.getStation() + fireS.getAddress());
    }

    public FireStation findByAddress(String address) {
        Collection<FireStation> fireStations = fireStationMap.values();

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }

        return null;
    }

    public List<FireStation> findByStation(int station) {
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

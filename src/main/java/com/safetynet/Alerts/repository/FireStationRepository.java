package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FireStationRepository {

    @Autowired
    StoredData storedData;

    public List<FireStation> getFireStationList() {
        return storedData.getFireStationList();
    }

    public void save(FireStation fireStation) {
        storedData.getFireStationList().add(fireStation);
    }

    public void delete(FireStation fireStation) {
        storedData.getFireStationList().remove(fireStation);
    }

}

package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;

import java.util.List;

public interface IFireStationService {

    FireStation createFireStation(FireStationDTO fireStation);

    FireStation updateFireStation(FireStationDTO fireStation);

    void deleteFireStation(FireStationDTO fireStation);

    FireStation getFireStationByAddress(String address);

    List<String> getAddressesByStation(int station);
}

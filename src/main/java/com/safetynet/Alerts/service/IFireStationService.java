package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.model.FireStation;

import java.util.List;

public interface IFireStationService {

    FireStationDTO createFireStation(FireStationDTO fireStation);

    FireStationDTO updateFireStation(FireStationDTO fireStation);

    void deleteFireStation(String address, Integer station);

    FireStation getFireStationByAddress(String address);

    FireStationDTO getFireStationByKeyId(String address, Integer station);

    List<String> getAddressesByStation(int station);
}

package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.*;

import java.util.List;

public interface IAlertsService {

    PersonsByStationDTO getPersonsByStation(int station);

    PersonInfoDTO getInfoByIdentity(String firstName, String lastName);

    PhoneAlertDTO getPhonesByStation(int station);

    CommunityEmailDTO getEmailsByCity(String city);

    ChildAlertDTO getChildByAddress(String address);

    FireDTO getPersonsByAddress(String address);

    FloodDTO getHouseholdsByStation(List<Integer> stations);

}

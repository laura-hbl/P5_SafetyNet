package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.*;

import java.util.List;

public interface IAlertsService {

    /**
     * Retrieves the list of all persons covered by the given fire station number and the count of children and
     * adults inside that list of people.
     *
     * @param station the fire station number
     * @return PersonsByStationDTO instance
     */
    PersonsByStationDTO getPersonsByStation(int station);

    /**
     * Retrieves a list composed of the person with the given identity as well as persons with the same name.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     * @return PersonInfoDTO instance
     */
    PersonInfoDTO getInfoByIdentity(String firstName, String lastName);

    /**
     * Retrieves the list of phone numbers of all inhabitants covered by the given station number.
     *
     * @param station fire station number
     * @return PhoneAlertDTO instance
     */
    PhoneAlertDTO getPhonesByStation(int station);

    /**
     * Retrieves the list of e-mail of all inhabitants living at the given city.
     *
     * @param city the city that we want inhabitants e-mails.
     * @return CommunityEmailDTO instance
     */
    CommunityEmailDTO getEmailsByCity(String city);

    /**
     * Retrieves the list of children and other household members living at the given address.
     *
     * @param address household address
     * @return ChildAlertDTO instance
     */
    ChildAlertDTO getChildByAddress(String address);

    /**
     * Retrieves the list of inhabitants living at the given address as well as fire station number which
     * covers it.
     *
     * @param address household address
     * @return FireDTO instance
     */
    FireDTO getPersonsByAddress(String address);

    /**
     * Retrieves the list of all households covered by given fire stations, grouping persons by address.
     *
     * @param stations fire station numbers
     * @return FloodDTO instance
     */
    FloodDTO getHouseholdsByStation(List<Integer> stations);
}

package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonStation;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of persons covered by the given fire station number and the count of
 * children and adults inside that list of people.
 *
 * @author Laura Habdul
 */
public class PersonsByStationDTO {

    /**
     * The list of persons covered by the given fire station number.
     */
    private List<PersonStation> personsByStation;

    /**
     * Count of adults inside the list of persons covered by the given fire station number.
     */
    private int adultNumber;

    /**
     * Count of children inside the list of persons covered by the given fire station number.
     */
    private int childNumber;

    /**
     * Constructor of class PersonsByStationDTO.
     * Initialize personsByStation, adultNumber and childNumber.
     *
     * @param personsByStation list of persons covered by the given fire station number
     * @param adultNumber count of adults
     * @param childNumber count of children
     */
    public PersonsByStationDTO(final List<PersonStation> personsByStation, final int adultNumber,
                               final int childNumber) {
        this.personsByStation = personsByStation;
        this.adultNumber = adultNumber;
        this.childNumber = childNumber;
    }

    /**
     * Empty constructor of class PersonsByStationDTO.
     */
    public PersonsByStationDTO() {
    }

    /**
     * Getter of PersonsByStationDTO.personsByStation.
     *
     * @return The list of persons covered by the given fire station number
     */
    public List<PersonStation> getPersonsByStation() {
        return personsByStation;
    }

    /**
     * Setter of PersonsByStationDTO.personsByStation.
     *
     * @param personsByStation list of persons covered by the given fire station number to set
     */
    public void setPersonsByStation(final List<PersonStation> personsByStation) {
        this.personsByStation = personsByStation;
    }

    /**
     * Getter of PersonsByStationDTO.adultNumber.
     *
     * @return The count of adults inside the list of persons covered by the given fire station number
     */
    public int getAdultNumber() {
        return adultNumber;
    }

    /**
     * Setter of PersonsByStationDTO.adultNumber.
     *
     * @param adultNumber count of adults inside the list of persons covered by the given fire station number to set
     */
    public void setAdultNumber(final int adultNumber) {
        this.adultNumber = adultNumber;
    }

    /**
     * Getter of PersonsByStationDTO.childNumber.
     *
     * @return The count of children inside the list of persons covered by the given fire station number
     */
    public int getChildNumber() {
        return childNumber;
    }

    /**
     * Setter of PersonsByStationDTO.childNumber.
     *
     * @param childNumber count of children inside the list of persons covered by the given fire station number to set
     */
    public void setChildNumber(final int childNumber) {
        this.childNumber = childNumber;
    }
}

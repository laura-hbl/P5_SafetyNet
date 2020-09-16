package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonAddress;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of inhabitants living at a given address as well as the
 * number of the fire station which covers it.
 *
 * @author Laura Habdul
 */
public class FireDTO {

    /**
     * Fire station number which covers the given address.
     */
    private int station;

    /**
     * List of all persons living at the given address.
     */
    private List<PersonAddress> persons;

    /**
     * Constructor of class FireDTO.
     * Initialize station and persons.
     *
     * @param station fire station number which covers the given address
     * @param persons list of all persons living at the given address
     */
    public FireDTO(final int station, final List<PersonAddress> persons) {
        this.station = station;
        this.persons = persons;
    }

    /**
     * Empty constructor of class FireDTO.
     */
    public FireDTO() {
    }

    /**
     * Getter of FireDTO.station.
     *
     * @return station The fire station number which covers the given address
     */
    public int getStation() {
        return station;
    }

    /**
     * Setter of FireDTO.station.
     *
     * @param station station number which covers the given address to set
     */
    public void setStation(final int station) {
        this.station = station;
    }

    /**
     * Getter of FireDTO.persons.
     *
     * @return The list of all persons living at the given address
     */
    public List<PersonAddress> getPersons() {
        return persons;
    }

    /**
     * Setter of FireDTO.emails.
     *
     * @param persons list of all persons living at the given address to set
     */
    public void setPersons(final List<PersonAddress> persons) {
        this.persons = persons;
    }
}


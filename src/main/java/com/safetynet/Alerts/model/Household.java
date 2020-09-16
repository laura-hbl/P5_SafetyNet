package com.safetynet.Alerts.model;

import java.util.List;

/**
 * Permits the storage and retrieving data of a household.
 *
 * @author Laura Habdul
 */
public class Household {

    /**
     * Address of the household.
     */
    private String address;

    /**
     * List of persons living inside the household.
     */
    private List<PersonAddress> personsList;

    /**
     * Constructor of class Household.
     * Initialize address and personsList.
     *
     * @param address     address of the household
     * @param personsList list of persons living inside the household
     */
    public Household(final String address, final List<PersonAddress> personsList) {
        this.address = address;
        this.personsList = personsList;
    }

    /**
     * Getter of Household.address.
     *
     * @return The address of the household
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of Household.address.
     *
     * @param address address of the household to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of Household.personsList.
     *
     * @return The list of persons living inside the household
     */
    public List<PersonAddress> getPersonsList() {
        return personsList;
    }

    /**
     * Setter of Household.personsList.
     *
     * @param personsList list of persons living inside the household to set
     */
    public void setPersonsList(final List<PersonAddress> personsList) {
        this.personsList = personsList;
    }
}

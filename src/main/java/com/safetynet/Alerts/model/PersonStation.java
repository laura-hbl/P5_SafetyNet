package com.safetynet.Alerts.model;

/**
 * Permits the storage and retrieving certain data of a person covered at specific fire station number.
 *
 * @author Laura Habdul
 */
public class PersonStation {

    /**
     * First name of the person.
     */
    private String firstName;

    /**
     * Last name of the person.
     */
    private String lastName;

    /**
     * Address where the person lives.
     */
    private String address;

    /**
     * Phone number of the person.
     */
    private String phone;

    /**
     * Constructor of class PersonStation.
     * Initialize lastName, address, age, email, medications and allergies.
     *
     * @param firstName first name of the person
     * @param lastName  last name of the person
     * @param address   address where the person lives
     * @param phone     phone number of the person
     */
    public PersonStation(final String firstName, final String lastName, final String address, final String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Getter of PersonStation.firstName.
     *
     * @return The first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of PersonStation.firstName.
     *
     * @param firstName first name of the person to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of PersonStation.lastName.
     *
     * @return The last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of PersonStation.lastName.
     *
     * @param lastName last name of the person to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of PersonStation.address.
     *
     * @return The address where the person lives
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of PersonStation.address.
     *
     * @param address address where the person lives to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of PersonStation.phone.
     *
     * @return The phone number of the person
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter of PersonInfo.lastName.
     *
     * @param phone phone number of the person to set
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }
}

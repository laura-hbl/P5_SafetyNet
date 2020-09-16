package com.safetynet.Alerts.model;

/**
 * Permits the storage and retrieving data of a person.
 *
 * @author Laura Habdul
 */
public class Person {

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
     * City where the person lives.
     */
    private String city;

    /**
     * Zip code of the person.
     */
    private int zip;

    /**
     * Phone number of the person.
     */
    private String phone;

    /**
     * Email of the person.
     */
    private String email;

    /**
     * Constructor of class Person.
     * Initialize firstName, lastName, address, city, zip, phone and email.
     *
     * @param firstName first name of the person
     * @param lastName  last name of the person
     * @param address   address where the person lives
     * @param city      city where the person lives
     * @param zip       zip code of the person
     * @param phone     phone number of the person
     * @param email     email number of the person
     */
    public Person(final String firstName, final String lastName, final String address, final String city, final int zip,
                  final String phone, final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Getter of Person.firstName.
     *
     * @return The first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of Person.firstName.
     *
     * @param firstName first name of the person to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of Person.lastName.
     *
     * @return The last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of Person.lastName.
     *
     * @param lastName last name of the person to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of Person.address.
     *
     * @return The address where the person lives
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of Person.address.
     *
     * @param address address where the person lives to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of Person.city.
     *
     * @return The city where the person lives
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter of Person.city.
     *
     * @param city city where the person lives to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Getter of Person.zip.
     *
     * @return The zip code the person
     */
    public int getZip() {
        return zip;
    }

    /**
     * Setter of Person.zip.
     *
     * @param zip zip code the person to set
     */
    public void setZip(final int zip) {
        this.zip = zip;
    }

    /**
     * Getter of Person.phone.
     *
     * @return The phone number of the person
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter of Person.phone.
     *
     * @param phone phone number of the person to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter of Person.email.
     *
     * @return The email of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of Person.email.
     *
     * @param email email of the person to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }
}

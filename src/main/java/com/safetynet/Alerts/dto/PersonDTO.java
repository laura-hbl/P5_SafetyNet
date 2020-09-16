package com.safetynet.Alerts.dto;

/**
 * Permits the storage and retrieving data of a person.
 *
 * @author Laura Habdul
 */
public class PersonDTO {

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
     * Constructor of class PersonDTO.
     * Initialize firstName, lastName, address, city, zip, phone and email.
     *
     * @param firstName first name of the person
     * @param lastName last name of the person
     * @param address address where the person lives
     * @param city city where the person lives
     * @param zip zip code of the person
     * @param phone phone number of the person
     * @param email email number of the person
     */
    public PersonDTO(final String firstName, final String lastName, final String address, final String city, final int zip,
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
     * Empty constructor of class PersonDTO.
     */
    public PersonDTO() {
    }

    /**
     * Getter of PersonDTO.firstName.
     *
     * @return The first name of the person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of PersonDTO.firstName.
     *
     * @param firstName first name of the person to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of PersonDTO.lastName.
     *
     * @return The last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of PersonDTO.lastName.
     *
     * @param lastName last name of the person to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of PersonDTO.address.
     *
     * @return The address where the person lives
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter of PersonDTO.address.
     *
     * @param address address where the person lives to set
     */
    public void setAddress(final String address) {
        this.address = address;
    }

    /**
     * Getter of PersonDTO.city.
     *
     * @return The city where the person lives
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter of PersonDTO.city.
     *
     * @param city city where the person lives to set
     */
    public void setCity(final String city) {
        this.city = city;
    }

    /**
     * Getter of PersonDTO.zip.
     *
     * @return The zip code the person
     */
    public int getZip() {
        return zip;
    }

    /**
     * Setter of PersonDTO.zip.
     *
     * @param zip zip code the person to set
     */
    public void setZip(final int zip) {
        this.zip = zip;
    }

    /**
     * Getter of PersonDTO.phone.
     *
     * @return The phone number of the person
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter of PersonDTO.phone.
     *
     * @param phone phone number of the person to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Getter of PersonDTO.email.
     *
     * @return The email of the person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter of PersonDTO.email.
     *
     * @param email email of the person to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }
}

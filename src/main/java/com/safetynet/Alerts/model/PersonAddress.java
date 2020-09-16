package com.safetynet.Alerts.model;

import java.util.List;

/**
 * Permits the storage and retrieving certain data of a person living at specific address.
 *
 * @author Laura Habdul
 */
public class PersonAddress {

    /**
     * Last name of the person.
     */
    private String lastName;

    /**
     * Phone of the person.
     */
    private String phone;

    /**
     * Age of the person.
     */
    private int age;

    /**
     * List of medications needed by the person.
     */
    List<String> medications;

    /**
     * List of allergies of the person.
     */
    List<String> allergies;

    /**
     * Constructor of class PersonAddress.
     * Initialize lastName, phone, age, medications and allergies.
     *
     * @param lastName    last name of the person
     * @param phone       phone number of the person
     * @param age         age of the person
     * @param medications list of medications needed by the person
     * @param allergies   list of allergies of the person
     */
    public PersonAddress(final String lastName, final String phone, final int age, final List<String> medications,
                         final List<String> allergies) {
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Getter of PersonAddress.lastName.
     *
     * @return The last name of the person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of PersonAddress.lastName.
     *
     * @param lastName last name of the person to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of PersonAddress.phone.
     *
     * @return The phone number of the person
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Setter of PersonAddress.phone.
     *
     * @param phone phone number of the person to set
     */
    public void setPhone(final String phone) {
        this.phone = phone;
    }

    /**
     * Getter of PersonAddress.age.
     *
     * @return The age of the person
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter of PersonAddress.age.
     *
     * @param age age of the person to set
     */
    public void setAge(final int age) {
        this.age = age;
    }

    /**
     * Getter of PersonAddress.medications.
     *
     * @return The list of medications needed by the person
     */
    public List<String> getMedications() {
        return medications;
    }

    /**
     * Setter of PersonAddress.medications.
     *
     * @param medications list of medications needed by the person to set
     */
    public void setMedications(final List<String> medications) {
        this.medications = medications;
    }

    /**
     * Getter of PersonAddress.allergies.
     *
     * @return The list of allergies name of the person
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * Setter of PersonAddress.allergies.
     *
     * @param allergies list of allergies name of the person to set
     */
    public void setAllergies(final List<String> allergies) {
        this.allergies = allergies;
    }
}

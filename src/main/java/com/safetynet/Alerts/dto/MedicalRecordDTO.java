package com.safetynet.Alerts.dto;

import java.util.List;

/**
 * Permits the storage and retrieving data of a medical record.
 *
 * @author Laura Habdul
 */
public class MedicalRecordDTO {

    /**
     * First name of the person whose the medical record belongs.
     */
    private String firstName;

    /**
     * Last name of the person whose the medical record belongs.
     */
    private String lastName;

    /**
     * BirthDate of the person whose the medical record belongs.
     */
    private String birthDate;

    /**
     * List of medications needed by the person whose the medical record belongs.
     */
    private List<String> medications;

    /**
     * List of allergies of the person whose the medical record belongs.
     */
    private List<String> allergies;

    /**
     * Constructor of class MedicalRecordDTO.
     * Initialize firstName, lastName, birthDate, medications and allergies.
     *
     * @param firstName first name of the person whose the medical record belongs
     * @param lastName last name of the person whose the medical record belongs
     * @param birthDate birthDate of the person whose the medical record belongs
     * @param medications list of medications needed by the person whose the medical record belongs
     * @param allergies list of allergies of the person whose the medical record belongs
     */
    public MedicalRecordDTO(final String firstName, final String lastName, final String birthDate,
                         final List<String> medications, final List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    /**
     * Empty constructor of class MedicalRecordDTO.
     */
    public MedicalRecordDTO() {
    }

    /**
     * Getter of MedicalRecordDTO.firstName.
     *
     * @return The first name of the person whose the medical record belongs
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter of MedicalRecordDTO.firstName.
     *
     * @param firstName first name of the person whose the medical record belongs to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter of MedicalRecordDTO.lastName.
     *
     * @return The last name of the person whose the medical record belongs
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Setter of MedicalRecordDTO.lastName.
     *
     * @param lastName last name of the person whose the medical record belongs to set
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Getter of MedicalRecordDTO.birthDate.
     *
     * @return The birthDate of the person whose the medical record belongs
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Setter of MedicalRecordDTO.birthDate.
     *
     * @param birthDate birthDate of the person whose the medical record belongs to set
     */
    public void setBirthDate(final String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Getter of MedicalRecordDTO.medications.
     *
     * @return The list of medications needed by the person whose the medical record belongs
     */
    public List<String> getMedications() {
        return medications;
    }

    /**
     * Setter of MedicalRecordDTO.medications.
     *
     * @param medications list of medications needed by the person whose the medical record belongs to set
     */
    public void setMedications(final List<String> medications) {
        this.medications = medications;
    }

    /**
     * Getter of MedicalRecordDTO.allergies.
     *
     * @return The list of allergies of the person whose the medical record belongs
     */
    public List<String> getAllergies() {
        return allergies;
    }

    /**
     * Setter of MedicalRecord.allergies.
     *
     * @param allergies list of allergies of the person whose the medical record belongs to set
     */
    public void setAllergies(final List<String> allergies) {
        this.allergies = allergies;
    }
}

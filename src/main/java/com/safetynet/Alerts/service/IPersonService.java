package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;

import java.util.List;

public interface IPersonService {

    /**
     * Saves a new person.
     *
     * @param person the person to be saved
     * @return The person saved converted to a PersonDTO object
     */
    PersonDTO createPerson(PersonDTO person);

    /**
     * Updates a registered person.
     *
     * @param person the person to be updated
     * @return The person updated converted to a medicalRecordDTO object
     */
    PersonDTO updatePerson(PersonDTO person);

    /**
     * Deletes person with the given identity.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     */
    void deletePerson(String firstName, String lastName);

    /**
     * Retrieves the person with the given identity.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person whose medical record belong to
     * @return the person retrieved converted to a PersonDTO object
     */
    PersonDTO getPersonById(String firstName, String lastName);

    /**
     * Retrieves the person list.
     *
     * @return The person list retrieved
     */
    List<Person> getPersonList();

    /**
     * Retrieves the persons living at the given city.
     *
     * @return The persons by city list retrieved
     */
    List<Person> getPersonsByCity(String city);

    /**
     * Retrieves the persons living at the given address.
     *
     * @return The persons by address list retrieved
     */
    List<Person> getPersonsByAddress(String address);
}
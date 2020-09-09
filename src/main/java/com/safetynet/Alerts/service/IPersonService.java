package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;

import java.util.List;

public interface IPersonService {

    PersonDTO createPerson(PersonDTO person);

    PersonDTO updatePerson(PersonDTO person);

    void deletePerson(String firstName, String lastName);

    PersonDTO getPersonById(String firstName, String lastName);

    List<Person> getPersonList();

    List<Person> getPersonsByCity(String city);

    List<Person> getPersonsByAddress(String address);
}
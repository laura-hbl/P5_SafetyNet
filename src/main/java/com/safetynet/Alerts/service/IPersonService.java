package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;

import java.util.List;

public interface IPersonService {

    Person createPerson(PersonDTO person);

    Person updatePerson(PersonDTO person);

    void deletePerson(PersonDTO person);

    List<Person> getPersonList();

    List<Person> getPersonsByCity(String city);

    List<Person> getPersonsByAddress(String address);

}

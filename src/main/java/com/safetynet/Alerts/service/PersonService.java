package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.repository.PersonRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    private static final Logger LOGGER = LogManager.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person createPerson(PersonDTO pers) {
        LOGGER.debug("Inside PersonService.createPerson");
        Person personToSave = new Person(pers.getFirstName(), pers.getLastName(), pers.getAddress(),
                pers.getCity(), pers.getZip(), pers.getPhone(), pers.getEmail());
        Person person = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (person != null) {
            throw new DataAlreadyRegisteredException("Person already registered");
        }

        return personRepository.save(personToSave);
    }

    public Person updatePerson(PersonDTO pers) {
        LOGGER.debug("Inside PersonService.updatePerson");
        Person personToUpdate = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personToUpdate == null) {
            throw new DataNotFoundException("Person not found");
        }

        personToUpdate.setAddress(pers.getAddress());
        personToUpdate.setCity(pers.getCity());
        personToUpdate.setZip(pers.getZip());
        personToUpdate.setPhone(pers.getPhone());
        personToUpdate.setEmail(pers.getEmail());
        return personToUpdate;
    }

    public void deletePerson(PersonDTO pers) {
        LOGGER.debug("Inside PersonService.deletePerson : " + pers.getFirstName(), pers.getLastName());
        Person personToDelete = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personToDelete == null) {
            throw new DataNotFoundException("Person not found");
        }

        personRepository.delete(personToDelete);
    }

    public List<Person> getPersonList() {
        LOGGER.debug("Inside PersonService.getPersonList");
        List<Person> personList = personRepository.getPersonList();

        if (personList.isEmpty()) {
            throw new DataNotFoundException("Failed to get person list");
        }

        return personList;
    }

    public List<Person> getPersonsByCity(String city) {
        LOGGER.debug("Inside PersonService.getPersonsByCity method for city : " + city);

        List<Person> personsByCity = personRepository.findByCity(city);

        if (personsByCity.isEmpty()) {
            throw new DataNotFoundException("Failed to get persons for city : " + city);
        }

        return personsByCity;
    }

    public List<Person> getPersonsByAddress(String address) {
        LOGGER.debug("Inside PersonService.getPersonsByAddress for address : " +address);

        List<Person> personsByAddress = personRepository.findByAddress(address);

        if (personsByAddress.isEmpty()) {
            throw new DataNotFoundException("Failed to get persons for address : " +address);
        }

        return personsByAddress;
    }
}


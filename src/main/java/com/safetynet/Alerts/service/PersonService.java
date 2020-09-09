package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.DataAlreadyRegisteredException;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.repository.PersonRepository;
import com.safetynet.Alerts.util.DTOConverter;
import com.safetynet.Alerts.util.ModelConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    private static final Logger LOGGER = LogManager.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    private final ModelConverter modelConverter;

    private final DTOConverter dtoConverter;

    @Autowired
    public PersonService(PersonRepository personRepository, ModelConverter modelConverter, DTOConverter dtoConverter) {
        this.personRepository = personRepository;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
    }

    public PersonDTO createPerson(PersonDTO pers) {
        LOGGER.debug("Inside PersonService.createPerson");
        Person personFound = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personFound != null) {
            throw new DataAlreadyRegisteredException("Person already registered");
        }
        Person personToSave = modelConverter.toPerson(pers);
        Person personSaved = personRepository.save(personToSave);

        return dtoConverter.toPersonDTO(personSaved);
    }

    public PersonDTO updatePerson(PersonDTO pers) {
        LOGGER.debug("Inside PersonService.updatePerson");
        Person personFound = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personFound == null) {
            throw new DataNotFoundException("Person not found");
        }

        personFound.setAddress(pers.getAddress());
        personFound.setCity(pers.getCity());
        personFound.setZip(pers.getZip());
        personFound.setPhone(pers.getPhone());
        personFound.setEmail(pers.getEmail());

        return dtoConverter.toPersonDTO(personFound);
    }

    public void deletePerson(String firstName, String lastName) {
        LOGGER.debug("Inside PersonService.deletePerson : " + firstName, lastName);
        Person personToDelete = personRepository.findByIdentity(firstName, lastName);

        if (personToDelete == null) {
            throw new DataNotFoundException("Person not found");
        }

        personRepository.delete(personToDelete);
    }

    public PersonDTO getPersonById(String firstName, String lastName) {
        LOGGER.debug("Inside PersonService.getPerson for address : " +firstName, lastName);

        Person person = personRepository.findByIdentity(firstName, lastName);

        if (person == null) {
            throw new DataNotFoundException("Failed to get person with Id : " +firstName +lastName);
        }

        return dtoConverter.toPersonDTO(person);
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


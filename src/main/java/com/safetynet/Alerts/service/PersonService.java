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

/**
 * Contains methods that allow interaction between Person business logic and Person repository.
 *
 * @author Laura Habdul
 * @see IPersonService
 */
@Service
public class PersonService implements IPersonService {

    /**
     * PersonService logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PersonService.class);

    /**
     * PersonRepository instance.
     */
    private final PersonRepository personRepository;

    /**
     * ModelConverter instance.
     */
    private final ModelConverter modelConverter;

    /**
     * DTOConverter instance.
     */
    private final DTOConverter dtoConverter;

    /**
     * Constructor of class PersonService.
     * Initialize personRepository, modelConverter and dtoConverter.
     *
     * @param personRepository PersonRepository instance
     * @param modelConverter   ModelConverter instance
     * @param dtoConverter     DTOConverter instance
     */
    @Autowired
    public PersonService(final PersonRepository personRepository, final ModelConverter modelConverter,
                         final DTOConverter dtoConverter) {
        this.personRepository = personRepository;
        this.modelConverter = modelConverter;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Checks if the given person to add is already registered by calling PersonRepository's
     * findByIdentity method, if so DataAlreadyRegisteredException is thrown. Else, it is converted to model object
     * by calling ModelConverter's toPerson and then saved by calling PersonRepository's save method.
     *
     * @param pers the person to be saved
     * @return The person saved converted to a PersonDTO object
     */
    public PersonDTO createPerson(final PersonDTO pers) {
        LOGGER.debug("Inside PersonService.createPerson");
        Person personFound = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personFound != null) {
            throw new DataAlreadyRegisteredException("Person already registered");
        }
        Person personToSave = modelConverter.toPerson(pers);
        Person personSaved = personRepository.save(personToSave);

        return dtoConverter.toPersonDTO(personSaved);
    }

    /**
     * Checks if the given person to update is registered by calling PersonRepository's
     * findByIdentity method, if so person is updated. Else, DataNotFoundException is thrown.
     *
     * @param pers the person to be updated
     * @return The person updated converted to a PersonDTO object
     */
    public PersonDTO updatePerson(final PersonDTO pers) {
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

    /**
     * Checks if the given person to delete is registered by calling PersonRepository's
     * findByIdentity method, if so person is deleted by calling PersonRepository's delete method.
     * Else, DataNotFoundException is thrown.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     */
    public void deletePerson(final String firstName, final String lastName) {
        LOGGER.debug("Inside PersonService.deletePerson : " + firstName, lastName);
        Person personToDelete = personRepository.findByIdentity(firstName, lastName);

        if (personToDelete == null) {
            throw new DataNotFoundException("Person not found");
        }

        personRepository.delete(personToDelete);
    }

    /**
     * Retrieves the person with the given identity by calling PersonRepository's
     * findByIdentity method and returned by being first converted to a model object by calling ModelConverter's
     * toPerson. If it is not found, DataNotFoundException is thrown.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person whose medical record belong to
     * @return the person retrieved converted to a PersonDTO object
     */
    public PersonDTO getPersonById(final String firstName, final String lastName) {
        LOGGER.debug("Inside PersonService.getPerson for address : " + firstName, lastName);

        Person person = personRepository.findByIdentity(firstName, lastName);

        if (person == null) {
            throw new DataNotFoundException("Failed to get person with Id : " + firstName + lastName);
        }

        return dtoConverter.toPersonDTO(person);
    }

    /**
     * Retrieves the person list by calling PersonRepository's getPersonList method.
     *
     * @return The person list retrieved
     */
    public List<Person> getPersonList() {
        LOGGER.debug("Inside PersonService.getPersonList");
        List<Person> personList = personRepository.getPersonList();

        if (personList.isEmpty()) {
            throw new DataNotFoundException("Failed to get person list");
        }

        return personList;
    }

    /**
     * Retrieves persons living at the given city by calling PersonRepository's findByCity method.
     *
     * @return The persons by city list retrieved
     */
    public List<Person> getPersonsByCity(final String city) {
        LOGGER.debug("Inside PersonService.getPersonsByCity method for city : " + city);

        List<Person> personsByCity = personRepository.findByCity(city);

        if (personsByCity.isEmpty()) {
            throw new DataNotFoundException("Failed to get persons for city : " + city);
        }

        return personsByCity;
    }

    /**
     * Retrieves persons living at the given address by calling PersonRepository's findByAddress method.
     *
     * @return The persons by address list retrieved
     */
    public List<Person> getPersonsByAddress(final String address) {
        LOGGER.debug("Inside PersonService.getPersonsByAddress for address : " + address);

        List<Person> personsByAddress = personRepository.findByAddress(address);

        if (personsByAddress.isEmpty()) {
            throw new DataNotFoundException("Failed to get persons for address : " + address);
        }

        return personsByAddress;
    }
}

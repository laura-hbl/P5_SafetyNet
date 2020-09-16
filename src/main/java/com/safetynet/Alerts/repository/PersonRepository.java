package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * PersonRepository class.
 * Contains set of methods to interact with personsMap, a HashMap where each Person retrieved from data store
 * is mapped, firstName+lastName of the person as key identifier and the Person object as value.
 *
 * @author Laura Habdul
 */
@Repository
public class PersonRepository {

    /**
     * PersonRepository logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PersonRepository.class);

    /**
     * Creates a HashMap instance to map Persons data.
     */
    private final Map<String, Person> personsMap = new HashMap<>();

    /**
     * Constructor of class PersonRepository.
     * Adds each Person from DataStore person list to a HashMap, firstName+lastName of the person
     * key identifier and the Person object as value.
     *
     * @param dataStore DataStore instance
     */
    @Autowired
    public PersonRepository(final DataStore dataStore) {
        LOGGER.debug("Map PersonList");
        dataStore.getPersonList().forEach(person -> personsMap.put(person.getFirstName()
                + person.getLastName(), person));
    }

    /**
     * Gets the person list from personMap.
     *
     * @return The person list
     */
    public List<Person> getPersonList() {
        LOGGER.debug("Inside PersonRepository.getPersonList");
        Collection personList = personsMap.values();
        return new ArrayList<>(personList);

    }

    /**
     * Adds the given Person to personMap.
     *
     * @param pers person to be saved
     * @return The person saved
     */
    public Person save(final Person pers) {
        LOGGER.debug("Inside PersonRepository.save for : " + pers.getFirstName(), pers.getLastName());
        personsMap.put(pers.getFirstName() + pers.getLastName(), pers);

        return personsMap.get(pers.getFirstName() + pers.getLastName());
    }

    /**
     * Deletes the given Person from personMap.
     *
     * @param pers person to be deleted
     */
    public void delete(final Person pers) {
        LOGGER.debug("Inside PersonRepository.delete for : " + pers.getFirstName(), pers.getLastName());
        personsMap.remove(pers.getFirstName() + pers.getLastName());
    }

    /**
     * Gets Person with the given key Id from personMap.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     * @return The Person retrieved
     */
    public Person findByIdentity(final String firstName, final String lastName) {
        LOGGER.debug("Inside PersonRepository.findByIdentity for : " + firstName, lastName);
        return personsMap.get(firstName + lastName);
    }

    /**
     * Loops through HashMap values in order to detect Persons living at the given city and them to an ArrayList.
     *
     * @param city city whose inhabitants are sought
     * @return The List of persons living at the given city retrieved
     */
    public List<Person> findByCity(final String city) {
        LOGGER.debug("Inside PersonRepository.findByCity for city : " + city);
        Collection<Person> persons = personsMap.values();
        List<Person> personsByCity = new ArrayList<>();

        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                personsByCity.add(person);
            }
        }

        return personsByCity;
    }

    /**
     * Loops through HashMap values in order to detect Persons living at the given address and them to an ArrayList.
     *
     * @param address address whose inhabitants are sought
     * @return The List of persons living at the given address retrieved
     */
    public List<Person> findByAddress(final String address) {
        LOGGER.debug("Inside PersonRepository.findByAddress for address : " + address);
        Collection<Person> persons = personsMap.values();
        List<Person> personsByAddress = new ArrayList<>();

        for (Person person : persons) {
            if (person.getAddress().equals(address)) {
                personsByAddress.add(person);
            }
        }

        return personsByAddress;
    }
}
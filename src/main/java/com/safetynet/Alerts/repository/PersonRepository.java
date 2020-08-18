package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository {

    private static final Logger LOGGER = LogManager.getLogger(PersonRepository.class);

    Map<String, Person> personsMap = new HashMap<>();

    @Autowired
    public PersonRepository(StoredData storedData) {
        LOGGER.debug("Map PersonList");
        storedData.getPersonList().forEach(person -> personsMap.put(person.getFirstName()
                + person.getLastName(), person));
    }

    public List<Person> getPersonList() {
        LOGGER.debug("Inside PersonRepository.getPersonList");
        Collection personList = personsMap.values();
        return new ArrayList<>(personList);

    }

    public Person save(Person pers) {
        LOGGER.debug("Inside PersonRepository.save for : " +pers.getFirstName(), pers.getLastName());
        personsMap.put(pers.getFirstName() + pers.getLastName(), pers);

        return pers;
    }

    public void delete(Person pers) {
        LOGGER.debug("Inside PersonRepository.delete for : " +pers.getFirstName(), pers.getLastName());
        personsMap.remove(pers.getFirstName() + pers.getLastName());
    }

    public Person findByIdentity(String firstName, String lastName) {
        LOGGER.debug("Inside PersonRepository.findByIdentity for : " +firstName, lastName);
        return personsMap.get(firstName + lastName);
    }

    public List<Person> findByCity(String city) {
        LOGGER.debug("Inside PersonRepository.findByCity for city : " +city);
        Collection<Person> persons = personsMap.values();
        List<Person> personsByCity = new ArrayList<>();

        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                personsByCity.add(person);
            }
        }

        return personsByCity;
    }

    public List<Person> findByAddress(String address) {
        LOGGER.debug("Inside PersonRepository.findByAddress for address : " +address);
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
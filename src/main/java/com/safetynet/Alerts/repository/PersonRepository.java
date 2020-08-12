package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PersonRepository {

    Map<String, Person> personsMap = new HashMap<>();

    @Autowired
    public PersonRepository(StoredData storedData) {
        storedData.getPersonList().forEach(person -> personsMap.put(person.getFirstName()
                + person.getLastName(), person));
    }

    public List<Person> getPersonList() {
        Collection personList = personsMap.values();
        return new ArrayList<>(personList);

    }

    public Person save(Person pers) {
        personsMap.put(pers.getFirstName() + pers.getLastName(), pers);

        return pers;
    }

    public void delete(Person pers) {
        personsMap.remove(pers.getFirstName() + pers.getLastName());
    }

    public Person findByIdentity(String firstName, String lastName) {
        return personsMap.get(firstName + lastName);
    }

    public List<Person> findByCity(String city) {
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
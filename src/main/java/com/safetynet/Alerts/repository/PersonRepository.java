package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepository {

    @Autowired
    StoredData storedData;

    public List<Person> getPersonList() {
        return storedData.getPersonList();
    }

    public void save(Person person) {
        storedData.getPersonList().add(person);
    }

    public void delete(Person person) {
        storedData.getPersonList().remove(person);

    }

    public Person findByIdentity(String firstName, String lastName) {
        List<Person> persons = storedData.getPersonList();

        for (Person person : persons) {
            if (person.getFirstName().equals(firstName) && person.getLastName().equals(lastName)) {
                return person;
            }
        }
        return null;
    }
    
}
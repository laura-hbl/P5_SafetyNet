package com.safetynet.Alerts.service;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements IPersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(PersonDTO pers) {
        Person personToSave = new Person(pers.getFirstName(), pers.getLastName(), pers.getAddress(),
                pers.getCity(), pers.getZip(), pers.getPhone(), pers.getEmail());
        Person person = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (person == null) {
            return personRepository.save(personToSave);
        }

        return null;
    }

    public Person updatePerson(PersonDTO pers) {

        Person personToUpdate = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personToUpdate != null) {
            personToUpdate.setAddress(pers.getAddress());
            personToUpdate.setCity(pers.getCity());
            personToUpdate.setZip(pers.getZip());
            personToUpdate.setPhone(pers.getPhone());
            personToUpdate.setEmail(pers.getEmail());
            return personToUpdate;
        }

        return null;
    }

    public void deletePerson(PersonDTO pers) {
        Person personToDelete = personRepository.findByIdentity(pers.getFirstName(), pers.getLastName());

        if (personToDelete != null) {
            personRepository.delete(personToDelete);
        }
    }

    public List<Person> getPersonList() {
        return personRepository.getPersonList();
    }

    public List<Person> getPersonsByCity(String city) {
        List<Person> personsByCity = personRepository.findByCity(city);

        if (personsByCity != null) {
            return personsByCity;
        }

        return null;
    }

    public List<Person> getPersonsByAddress(String address) {
        List<Person> personsByAddress = personRepository.findByAddress(address);

        if (personsByAddress!= null) {
            return personsByAddress;
        }

        return null;
    }
}


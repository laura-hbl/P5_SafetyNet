package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.model.Person;
import com.safetynet.Alerts.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

    PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody PersonDTO person) {
        LOGGER.debug("Person CREATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        Person personCreated = personService.createPerson(person);

        LOGGER.info("Person POST request - SUCCESS");
        return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody PersonDTO person) {
        LOGGER.debug("Person UPDATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        Person personUpdated = personService.updatePerson(person);

        LOGGER.info("Person PUT request - SUCCESS");
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(@RequestBody PersonDTO person) {
        LOGGER.debug("Person DELETE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        personService.deletePerson(person);

        LOGGER.info("Person DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

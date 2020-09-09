package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IPersonService;
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

    IPersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO person) {
        LOGGER.debug("Person CREATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person.getFirstName() == null || person.getFirstName().isEmpty() || person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        PersonDTO personCreated = personService.createPerson(person);

        LOGGER.info("Person POST request - SUCCESS");
        return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
    }

    @PutMapping("/person")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO person) {
        LOGGER.debug("Person UPDATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person.getFirstName() == null || person.getFirstName().isEmpty() || person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        PersonDTO personUpdated = personService.updatePerson(person);

        LOGGER.info("Person PUT request - SUCCESS");
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(@RequestParam("firstName") String firstName,
                                             @RequestParam("lastName") String lastName) {
        LOGGER.debug("Person DELETE Request on : " + firstName + " " + lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        personService.deletePerson(firstName, lastName);

        LOGGER.info("Person DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/person")
    public ResponseEntity<PersonDTO> getPerson(@RequestParam("firstName") String firstName,
                                         @RequestParam("lastName") String lastName) {
        LOGGER.debug("Person GET Request on : {} {}", firstName, lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        PersonDTO personDTO = personService.getPersonById(firstName, lastName);

        LOGGER.info("Person GET Request - SUCCESS");
        return new ResponseEntity<>(personDTO, HttpStatus.OK);
    }
}

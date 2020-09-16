package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.PersonDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IPersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Creates REST endpoints for crud operations on person data.
 *
 * @see IPersonService
 *
 * @author Laura Habdul
 */
@RestController
public class PersonController {

    /**
     * PersonController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(PersonController.class);

    /**
     * IPersonService's implement class reference.
     */
    private final IPersonService personService;

    /**
     * Constructor of class PersonController.
     * Initialize personService.
     *
     * @param personService IPersonService's implement class reference.
     */
    @Autowired
    public PersonController(final IPersonService personService) {
        this.personService = personService;
    }

    /**
     * Adds a new person.
     *
     * @param person the person to be saved
     * @return ResponseEntity<PersonDTO> The response object and Http Status generated
     */
    @PostMapping("/person")
    public ResponseEntity<PersonDTO> createPerson(@RequestBody final PersonDTO person) {
        LOGGER.debug("Person CREATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person.getFirstName() == null || person.getFirstName().isEmpty() || person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        PersonDTO personCreated = personService.createPerson(person);

        LOGGER.info("Person POST request - SUCCESS");
        return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
    }

    /**
     * Updates a stored person.
     *
     * @param person the person with updated data
     * @return ResponseEntity<PersonDTO> The response object and Http Status generated
     */
    @PutMapping("/person")
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody final PersonDTO person) {
        LOGGER.debug("Person UPDATE Request on : " + person.getFirstName() + " " + person.getLastName());

        if (person.getFirstName() == null || person.getFirstName().isEmpty() || person.getLastName() == null
                || person.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        PersonDTO personUpdated = personService.updatePerson(person);

        LOGGER.info("Person PUT request - SUCCESS");
        return new ResponseEntity<>(personUpdated, HttpStatus.OK);
    }

    /**
     * Deletes a stored person.
     *
     * @param firstName firstName of the person to be deleted
     * @param lastName  lastName of the person to be deleted
     * @return ResponseEntity<Void> The Http Status generated
     */
    @DeleteMapping("/person")
    public ResponseEntity<Void> deletePerson(@RequestParam("firstName") final String firstName,
                                             @RequestParam("lastName") final String lastName) {
        LOGGER.debug("Person DELETE Request on : " + firstName + " " + lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        personService.deletePerson(firstName, lastName);

        LOGGER.info("Person DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves a stored person.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     * @return ResponseEntity<PersonDTO> The response object and Http Status generated
     */
    @GetMapping("/person")
    public ResponseEntity<PersonDTO> getPerson(@RequestParam("firstName") final String firstName,
                                               @RequestParam("lastName") final String lastName) {
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

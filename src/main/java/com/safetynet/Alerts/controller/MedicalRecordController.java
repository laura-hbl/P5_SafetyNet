package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IMedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Creates REST endpoints for crud operations on medical record data.
 *
 * @author Laura Habdul
 * @see IMedicalRecordService
 */
@RestController
public class MedicalRecordController {

    /**
     * MedicalRecordController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordController.class);

    /**
     * IMedicalRecordService's implement class reference.
     */
    private final IMedicalRecordService medicalRecordService;

    /**
     * Constructor of class MedicalRecordController.
     * Initialize medicalRecordService.
     *
     * @param medicalRecordService IMedicalRecordService's implement class reference.
     */
    @Autowired
    public MedicalRecordController(final IMedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Adds a new medicalRecord.
     *
     * @param med the medicalRecord to be saved
     * @return ResponseEntity<MedicalRecordDTO> The response object and Http Status generated
     */
    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody final MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord POST Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med.getFirstName() == null || med.getFirstName().isEmpty() || med.getLastName() == null ||
                med.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        MedicalRecordDTO medicalRecordCreated = medicalRecordService.createMedicalRecord(med);

        LOGGER.info("MedicalRecord POST request - SUCCESS");
        return new ResponseEntity<>(medicalRecordCreated, HttpStatus.CREATED);
    }

    /**
     * Updates a stored medicalRecord.
     *
     * @param med the medicalRecord with updated data
     * @return ResponseEntity<MedicalRecordDTO> The response object and Http Status generated
     */
    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@RequestBody final MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord PUT Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med.getFirstName() == null || med.getFirstName().isEmpty() || med.getLastName() == null ||
                med.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        MedicalRecordDTO medicalRecordUpdated = medicalRecordService.updateMedicalRecord(med);

        LOGGER.info("MedicalRecord PUT request - SUCCESS");
        return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);

    }

    /**
     * Deletes a stored medicalRecord.
     *
     * @param firstName firstName of the person whose medicalRecord is to be deleted
     * @param lastName  lastName of the person whose medicalRecord is to be deleted
     * @return ResponseEntity<Void> The Http Status generated
     */
    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam("firstName") final String firstName,
                                                    @RequestParam("lastName") final String lastName) {
        LOGGER.debug("MedicalRecord DELETE Request on : " + firstName + " " + lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        LOGGER.info("MedicalRecord DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves a stored medicalRecord.
     *
     * @param firstName firstName of the person whose medicalRecord is to be retrieved
     * @param lastName  lastName of the person whose medicalRecord is to be retrieved
     * @return ResponseEntity<MedicalRecordDTO> The response object and Http Status generated
     */
    @GetMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@RequestParam("firstName") final String firstName,
                                                             @RequestParam("lastName") final String lastName) {
        LOGGER.debug("MedicalRecord GET Request on : {} {}", firstName, lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        MedicalRecordDTO medDTO = medicalRecordService.getMedicalRecordById(firstName, lastName);

        LOGGER.info("MedicalRecord GET Request - SUCCESS");
        return new ResponseEntity<>(medDTO, HttpStatus.OK);
    }
}

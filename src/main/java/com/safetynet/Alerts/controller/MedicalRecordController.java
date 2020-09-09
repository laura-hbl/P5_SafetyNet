package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IMedicalRecordService;
import com.safetynet.Alerts.service.MedicalRecordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    private static final Logger LOGGER = LogManager.getLogger(MedicalRecordController.class);

    IMedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> createMedicalRecord(@RequestBody MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord POST Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med.getFirstName() == null || med.getFirstName().isEmpty() || med.getLastName() == null ||
                med.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        MedicalRecordDTO medicalRecordCreated = medicalRecordService.createMedicalRecord(med);

        LOGGER.info("MedicalRecord POST request - SUCCESS");
        return new ResponseEntity<>(medicalRecordCreated, HttpStatus.CREATED);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(@RequestBody MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord PUT Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med.getFirstName() == null || med.getFirstName().isEmpty() || med.getLastName() == null ||
                med.getLastName().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        MedicalRecordDTO medicalRecordUpdated = medicalRecordService.updateMedicalRecord(med);

        LOGGER.info("MedicalRecord PUT request - SUCCESS");
        return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);

    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deleteMedicalRecord(@RequestParam("firstName") String firstName,
                                                    @RequestParam("lastName") String lastName) {
        LOGGER.debug("MedicalRecord DELETE Request on : " + firstName + " " + lastName);

        if (firstName == null || firstName.trim().length() == 0 || lastName == null
                || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        medicalRecordService.deleteMedicalRecord(firstName, lastName);

        LOGGER.info("MedicalRecord DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/medicalRecord")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@RequestParam("firstName") String firstName,
                                                             @RequestParam("lastName") String lastName) {
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

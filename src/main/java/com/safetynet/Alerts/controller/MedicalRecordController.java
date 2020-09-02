package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.MedicalRecordDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.model.MedicalRecord;
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

    MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord POST Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        MedicalRecord medicalRecordCreated = medicalRecordService.createMedicalRecord(med);

        LOGGER.info("MedicalRecord POST request - SUCCESS");
        return new ResponseEntity<>(medicalRecordCreated, HttpStatus.CREATED);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord PUT Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        MedicalRecord medicalRecordUpdated = medicalRecordService.updateMedicalRecord(med);

        LOGGER.info("MedicalRecord PUT request - SUCCESS");
        return new ResponseEntity<>(medicalRecordUpdated, HttpStatus.OK);

    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<Void> deletePerson(@RequestBody MedicalRecordDTO med) {
        LOGGER.debug("MedicalRecord DELETE Request on : " + med.getFirstName() + " " + med.getLastName());

        if (med == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        medicalRecordService.deleteMedicalRecord(med);

        LOGGER.info("MedicalRecord DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

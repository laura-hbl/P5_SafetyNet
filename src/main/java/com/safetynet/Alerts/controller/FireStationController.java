package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.service.FireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    private static final Logger LOGGER = LogManager.getLogger(FireStationController.class);

    FireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<FireStation> createFireStation(@RequestBody FireStationDTO fireS) {
        LOGGER.debug("FireStation POST Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        FireStation fireStationCreated = fireStationService.createFireStation(fireS);

        LOGGER.info("FireStation POST request - SUCCESS");
        return new ResponseEntity<>(fireStationCreated, HttpStatus.CREATED);
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStation> updateMedicalRecord(@RequestBody FireStationDTO fireS) {
        LOGGER.debug("FireStation PUT Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        FireStation fireStationUpdated = fireStationService.updateFireStation(fireS);

        LOGGER.info("FireStation PUT request - SUCCESS");
        return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deletePerson(@RequestBody FireStationDTO fireS) {
        LOGGER.debug("FireStation DELETE Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS == null) {
            throw new BadRequestException("Bad request : missing request body");
        }
        fireStationService.deleteFireStation(fireS);

        LOGGER.info("FireStation DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

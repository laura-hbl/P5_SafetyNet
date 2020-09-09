package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.FireStationService;
import com.safetynet.Alerts.service.IFireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FireStationController {

    private static final Logger LOGGER = LogManager.getLogger(FireStationController.class);

    IFireStationService fireStationService;

    @Autowired
    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @PostMapping("/firestation")
    public ResponseEntity<FireStationDTO> createFireStation(@RequestBody FireStationDTO fireS) {
        LOGGER.debug("FireStation POST Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS.getAddress() == null || fireS.getAddress().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        FireStationDTO fireStationCreated = fireStationService.createFireStation(fireS);

        LOGGER.info("FireStation POST request - SUCCESS");
        return new ResponseEntity<>(fireStationCreated, HttpStatus.CREATED);
    }

    @PutMapping("/firestation")
    public ResponseEntity<FireStationDTO> updateFireStation(@RequestBody FireStationDTO fireS) {
        LOGGER.debug("FireStation PUT Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS.getAddress() == null || fireS.getAddress().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        FireStationDTO fireStationUpdated = fireStationService.updateFireStation(fireS);

        LOGGER.info("FireStation PUT request - SUCCESS");
        return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(@RequestParam("address") String address,
                                                  @RequestParam("station") Integer station) {
        LOGGER.debug("FireStation DELETE Request on : {} {}", address, station);

        if (address == null || address.trim().length() == 0 || station == null) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        fireStationService.deleteFireStation(address, station);

        LOGGER.info("FireStation DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fireStation")
    public ResponseEntity<FireStationDTO> getFireStation(@RequestParam("address") String address,
                                                         @RequestParam("station") Integer station) {
        LOGGER.debug("FireStation GET Request on : {} {}", address, station);

        if (address == null || address.trim().length() == 0 || station == null) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        FireStationDTO fireDTO = fireStationService.getFireStationByKeyId(address, station);

        LOGGER.info("FireStation GET Request - SUCCESS");
        return new ResponseEntity<>(fireDTO, HttpStatus.OK);
    }
}

package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.FireStationDTO;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IFireStationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Creates REST endpoints for crud operations on fire station data.
 *
 * @author Laura Habdul
 * @see IFireStationService
 */
@RestController
public class FireStationController {

    /**
     * FireStationController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(FireStationController.class);

    /**
     * IFireStationService's implement class reference.
     */
    private final IFireStationService fireStationService;

    /**
     * Constructor of class FireStationController.
     * Initialize fireStationService.
     *
     * @param fireStationService IFireStationService's implement class reference.
     */
    @Autowired
    public FireStationController(final IFireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    /**
     * Adds a new fire station.
     *
     * @param fireS the fire station to be saved
     * @return ResponseEntity<FireStationDTO> The response object and Http Status generated
     */
    @PostMapping("/firestation")
    public ResponseEntity<FireStationDTO> createFireStation(@RequestBody final FireStationDTO fireS) {
        LOGGER.debug("FireStation POST Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS.getAddress() == null || fireS.getAddress().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        FireStationDTO fireStationCreated = fireStationService.createFireStation(fireS);

        LOGGER.info("FireStation POST request - SUCCESS");
        return new ResponseEntity<>(fireStationCreated, HttpStatus.CREATED);
    }

    /**
     * Updates a stored fire station.
     *
     * @param fireS the fire station with updated data
     * @return ResponseEntity<FireStationDTO> The response object and Http Status generated
     */
    @PutMapping("/firestation")
    public ResponseEntity<FireStationDTO> updateFireStation(@RequestBody final FireStationDTO fireS) {
        LOGGER.debug("FireStation PUT Request with address : {} and station number : {}",
                fireS.getAddress(), fireS.getStation());

        if (fireS.getAddress() == null || fireS.getAddress().isEmpty()) {
            throw new BadRequestException("Bad request : missing or incomplete body request");
        }
        FireStationDTO fireStationUpdated = fireStationService.updateFireStation(fireS);

        LOGGER.info("FireStation PUT request - SUCCESS");
        return new ResponseEntity<>(fireStationUpdated, HttpStatus.OK);
    }

    /**
     * Deletes a stored fire station.
     *
     * @param address address of the house covered by this fire station
     * @param station station number of the fire station
     * @return ResponseEntity<Void> The Http Status generated
     */
    @DeleteMapping("/firestation")
    public ResponseEntity<Void> deleteFireStation(@RequestParam("address") final String address,
                                                  @RequestParam("station") final Integer station) {
        LOGGER.debug("FireStation DELETE Request on : {} {}", address, station);

        if (address == null || address.trim().length() == 0 || station == null) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        fireStationService.deleteFireStation(address, station);

        LOGGER.info("FireStation DELETE request - SUCCESS");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Retrieves a stored fire station.
     *
     * @param address address of the house covered by this fire station
     * @param station station number of the fire station
     * @return ResponseEntity<FireStationDTO> The response object and Http Status generated
     */
    @GetMapping("/fireStation")
    public ResponseEntity<FireStationDTO> getFireStation(@RequestParam("address") final String address,
                                                         @RequestParam("station") final Integer station) {
        LOGGER.debug("FireStation GET Request on : {} {}", address, station);

        if (address == null || address.trim().length() == 0 || station == null) {
            throw new BadRequestException("Bad request : missing or incomplete parameter");
        }
        FireStationDTO fireDTO = fireStationService.getFireStationByKeyId(address, station);

        LOGGER.info("FireStation GET Request - SUCCESS");
        return new ResponseEntity<>(fireDTO, HttpStatus.OK);
    }
}

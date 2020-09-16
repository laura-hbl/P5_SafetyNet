package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.IAlertsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Creates REST endpoints on the application data.
 *
 * @author Laura Habdul
 * @see IAlertsService
 */
@RestController
public class AlertsController {

    /**
     * AlertsController logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(AlertsController.class);

    /**
     * IAlertsService's implement class reference.
     */
    private final IAlertsService alertsService;

    /**
     * Constructor of class AlertsController.
     * Initialize alertsService.
     *
     * @param alertsService IAlertsService's implement class reference.
     */
    @Autowired
    public AlertsController(final IAlertsService alertsService) {
        this.alertsService = alertsService;
    }

    /**
     * Retrieves the list of all persons covered by the given fire station number and the count of
     * children and adults inside that list of people.
     *
     * @param station fire station number
     * @return ResponseEntity<PersonsByStationDTO> The response object and Http Status generated
     */
    @GetMapping("/firestation")
    public ResponseEntity<PersonsByStationDTO> getPersonsByStation(@RequestParam("stationNumber") final Integer station) {
        LOGGER.debug("GET Request on /firestation with station number {}", station);

        if (station == null) {
            throw new BadRequestException("Bad request : missing station parameter");
        }
        PersonsByStationDTO personsByStationDTO = alertsService.getPersonsByStation(station);

        LOGGER.info("GET Request on /firestation - SUCCESS");
        return new ResponseEntity<>(personsByStationDTO, HttpStatus.OK);
    }

    /**
     * Retrieves the list of children and other household members living at the given address.
     *
     * @param address household address
     * @return ResponseEntity<ChildAlertDTO> The response object and Http Status generated
     */
    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildByAddress(@RequestParam("address") final String address) {

        LOGGER.debug("GET Request on /childAlert with address {}", address);

        if (address.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing address parameter");
        }
        ChildAlertDTO childAlertDTO = alertsService.getChildByAddress(address);

        LOGGER.info("GET Request on /childAlert - SUCCESS");
        return new ResponseEntity<>(childAlertDTO, HttpStatus.OK);
    }

    /**
     * Retrieves the list of phone numbers of all inhabitants covered by the given station number.
     *
     * @param station fire station number
     * @return ResponseEntity<PhoneAlertDTO> The response object and Http Status generated
     */
    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertDTO> getPhonesByStation(@RequestParam("firestation") final Integer station) {

        LOGGER.debug("GET Request on /phoneAlert with station number {}", station);

        if (station == null) {
            throw new BadRequestException("Bad request : missing station parameter");
        }
        PhoneAlertDTO phoneAlertDTO = alertsService.getPhonesByStation(station);

        LOGGER.info("GET Request on /phoneAlert - SUCCESS");
        return new ResponseEntity<>(phoneAlertDTO, HttpStatus.OK);
    }

    /**
     * Retrieves the list of inhabitants living at the given address as well as fire station number which
     * covers it.
     *
     * @param address household address
     * @return ResponseEntity<FireDTO> The response object and Http Status generated
     */
    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getPersonsByAddress(@RequestParam("address") final String address) {

        LOGGER.debug("GET Request on /fire with address {}", address);

        if (address.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing address parameter");
        }
        FireDTO fireDTO = alertsService.getPersonsByAddress(address);

        LOGGER.info("GET Request on /fire - SUCCESS");
        return new ResponseEntity<>(fireDTO, HttpStatus.OK);
    }

    /**
     * Retrieves the list of all households covered by given fire stations, grouping persons by address.
     *
     * @param stations fire station numbers
     * @return ResponseEntity<FloodDTO> The response object and Http Status generated
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<FloodDTO> getHouseholdsByStation(@RequestParam("stations") final List<Integer> stations) {

        LOGGER.debug("GET Request on /flood with stations numbers {}", stations);

        if (stations.isEmpty()) {
            throw new BadRequestException("Bad request : missing stations parameters");
        }
        FloodDTO floodDTO = alertsService.getHouseholdsByStation(stations);

        LOGGER.info("GET Request on /flood - SUCCESS");
        return new ResponseEntity<>(floodDTO, HttpStatus.OK);
    }

    /**
     * Retrieves a list composed of the person with the given identity as well as persons with the same name.
     *
     * @param firstName firstName of the person
     * @param lastName  lastName of the person
     * @return ResponseEntity<PersonInfoDTO> The response object and Http Status generated
     */
    @GetMapping("/personInfo")
    public ResponseEntity<PersonInfoDTO> getPersonInfoByIdentity(@RequestParam("firstName") final String firstName,
                                                                 @RequestParam("lastName") final String lastName) {

        LOGGER.debug("GET Request on /personInfo with firstName {} and lastName {}", firstName, lastName);

        if (firstName.trim().length() == 0 || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing identity parameters");
        }
        PersonInfoDTO personInfoDTO = alertsService.getInfoByIdentity(firstName, lastName);

        LOGGER.info("GET Request on /personInfo - SUCCESS");
        return new ResponseEntity<>(personInfoDTO, HttpStatus.OK);
    }

    /**
     * Retrieves the list of e-mail of all inhabitants living at the given city.
     *
     * @param city the city that we want inhabitants e-mails.
     * @return ResponseEntity<CommunityEmailDTO> The response object and Http Status generated
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam("city") final String city) {

        LOGGER.debug("GET Request on /communityEmail with city {}", city);

        if (city.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing city parameter");
        }
        CommunityEmailDTO communityEmailDTO = alertsService.getEmailsByCity(city);

        LOGGER.info("GET Request on /communityEmail - SUCCESS");
        return new ResponseEntity<>(communityEmailDTO, HttpStatus.OK);
    }
}

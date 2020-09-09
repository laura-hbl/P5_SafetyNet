package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.exception.BadRequestException;
import com.safetynet.Alerts.service.AlertsService;
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

@RestController
public class AlertsController {

    private static final Logger LOGGER = LogManager.getLogger(AlertsController.class);

    IAlertsService alertsService;

    @Autowired
    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }



    @GetMapping("/firestation")
    public ResponseEntity<PersonsByStationDTO> getPersonsByStation(@RequestParam("stationNumber") Integer station) {
        LOGGER.debug("GET Request on /firestation with station number {}", station);

        if (station == null) {
            throw new BadRequestException("Bad request : missing station parameter");
        }
        PersonsByStationDTO personsByStationDTO = alertsService.getPersonsByStation(station);

        LOGGER.info("GET Request on /firestation - SUCCESS");
        return new ResponseEntity<>(personsByStationDTO, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildByAddress(@RequestParam("address") String address) {

        LOGGER.debug("GET Request on /childAlert with address {}", address);

        if (address.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing address parameter");
        }
        ChildAlertDTO childAlertDTO = alertsService.getChildByAddress(address);

        LOGGER.info("GET Request on /childAlert - SUCCESS");
        return new ResponseEntity<>(childAlertDTO, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertDTO> getPhonesByStation(@RequestParam("firestation") Integer station) {

        LOGGER.debug("GET Request on /phoneAlert with station number {}", station);

        if (station == null) {
            throw new BadRequestException("Bad request : missing station parameter");
        }
        PhoneAlertDTO phoneAlertDTO = alertsService.getPhonesByStation(station);

        LOGGER.info("GET Request on /phoneAlert - SUCCESS");
        return new ResponseEntity<>(phoneAlertDTO, HttpStatus.OK);
    }

    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getPersonsByAddress(@RequestParam("address") String address) {

        LOGGER.debug("GET Request on /fire with address {}", address);

        if (address.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing address parameter");
        }
        FireDTO fireDTO = alertsService.getPersonsByAddress(address);

        LOGGER.info("GET Request on /fire - SUCCESS");
        return new ResponseEntity<>(fireDTO, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<FloodDTO> getHouseholdsByStation(@RequestParam("stations") List<Integer> stations) {

        LOGGER.debug("GET Request on /flood with stations numbers {}", stations);

        if (stations.isEmpty()) {
            throw new BadRequestException("Bad request : missing stations parameters");
        }
        FloodDTO floodDTO = alertsService.getHouseholdsByStation(stations);

        LOGGER.info("GET Request on /flood - SUCCESS");
        return new ResponseEntity<>(floodDTO, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<PersonInfoDTO> getPersonInfoByIdentity(@RequestParam("firstName") String firstName,
                                                                 @RequestParam("lastName") String lastName) {

        LOGGER.debug("GET Request on /personInfo with firstName {} and lastName {}", firstName, lastName);

        if (firstName.trim().length() == 0 || lastName.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing identity parameters");
        }
        PersonInfoDTO personInfoDTO = alertsService.getInfoByIdentity(firstName, lastName);

        LOGGER.info("GET Request on /personInfo - SUCCESS");
        return new ResponseEntity<>(personInfoDTO, HttpStatus.OK);
    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam("city") String city) {

        LOGGER.debug("GET Request on /communityEmail with city {}", city);

        if (city.trim().length() == 0) {
            throw new BadRequestException("Bad request : missing city parameter");
        }
        CommunityEmailDTO communityEmailDTO = alertsService.getEmailsByCity(city);

        LOGGER.info("GET Request on /communityEmail - SUCCESS");
        return new ResponseEntity<>(communityEmailDTO, HttpStatus.OK);
    }
}

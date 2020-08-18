package com.safetynet.Alerts.controller;

import com.safetynet.Alerts.dto.*;
import com.safetynet.Alerts.exception.DataNotFoundException;
import com.safetynet.Alerts.service.AlertsService;
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

    AlertsService alertsService;

    @Autowired
    public AlertsController(AlertsService alertsService) {
        this.alertsService = alertsService;
    }

    @GetMapping("/firestation")
    public ResponseEntity<PersonsByStationDTO> getPersonsByStation(@RequestParam("stationNumber") int station)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /firestation with station number {}", station);

        PersonsByStationDTO personsByStationDTO = alertsService.getPersonsByStation(station);

        LOGGER.info("GET Request on /firestation - SUCCESS");
        return new ResponseEntity<>(personsByStationDTO, HttpStatus.OK);
    }

    @GetMapping("/childAlert")
    public ResponseEntity<ChildAlertDTO> getChildByAddress(@RequestParam("address") String address)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /childAlert with address {}", address);

        ChildAlertDTO childAlertDTO = alertsService.getChildByAddress(address);

        LOGGER.info("GET Request on /childAlert - SUCCESS");
        return new ResponseEntity<>(childAlertDTO, HttpStatus.OK);
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<PhoneAlertDTO> getPhonesByStation(@RequestParam("firestation") int station)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /phoneAlert with station number {}", station);

        PhoneAlertDTO phoneAlertDTO = alertsService.getPhonesByStation(station);

        LOGGER.info("GET Request on /phoneAlert - SUCCESS");
        return new ResponseEntity<>(phoneAlertDTO, HttpStatus.OK);

    }

    @GetMapping("/fire")
    public ResponseEntity<FireDTO> getPersonsByAddress(@RequestParam("address") String address)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /fire with address {}", address);

        FireDTO fireDTO = alertsService.getPersonsByAddress(address);

        LOGGER.info("GET Request on /fire - SUCCESS");
        return new ResponseEntity<>(fireDTO, HttpStatus.OK);
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<FloodDTO> getHouseholdsByStation(@RequestParam("stations") List<Integer> stations)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /flood with stations numbers {}", stations);

        FloodDTO floodDTO = alertsService.getHouseholdsByStation(stations);

        LOGGER.info("GET Request on /flood - SUCCESS");
        return new ResponseEntity<>(floodDTO, HttpStatus.OK);
    }

    @GetMapping("/personInfo")
    public ResponseEntity<PersonInfoDTO> getPersonInfoByIdentity(@RequestParam("firstName") String firstName,
                                                                 @RequestParam("lastName") String lastName)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /personInfo with firstName {} and lastName {}", firstName, lastName);

        PersonInfoDTO personInfoDTO = alertsService.getInfoByIdentity(firstName, lastName);

        LOGGER.info("GET Request on /personInfo - SUCCESS");
        return new ResponseEntity<>(personInfoDTO, HttpStatus.OK);

    }

    @GetMapping("/communityEmail")
    public ResponseEntity<CommunityEmailDTO> getEmailsByCity(@RequestParam("city") String city)
            throws DataNotFoundException {
        LOGGER.debug("GET Request on /communityEmail with city {}", city);

        CommunityEmailDTO communityEmailDTO = alertsService.getEmailsByCity(city);

        LOGGER.info("GET Request on /communityEmail - SUCCESS");
        return new ResponseEntity<>(communityEmailDTO, HttpStatus.OK);
    }
}

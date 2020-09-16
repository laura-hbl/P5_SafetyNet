package com.safetynet.Alerts.repository;

import com.safetynet.Alerts.data.DataStore;
import com.safetynet.Alerts.model.FireStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * FireStationRepository class.
 * Contains set of methods to interact with fireStationMap, a HashMap where each FireStation retrieved
 * from data store is mapped, address+station of FireStation as key identifier and the FireStation object as value.
 *
 * @author Laura Habdul
 */
@Repository
public class FireStationRepository {

    /**
     * FireStationRepository logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(FireStationRepository.class);

    /**
     * Creates a HashMap instance to map FireStations data.
     */
    private final Map<String, FireStation> fireStationMap = new HashMap<>();

    /**
     * Constructor of class FireStationRepository.
     * Adds each FireStation from data store to a HashMap, address+station of the fire station as key identifier
     * and the FireStation object as value.
     *
     * @param dataStore DataStore instance
     */
    @Autowired
    public FireStationRepository(final DataStore dataStore) {
        LOGGER.debug("Map FireStationList");
        dataStore.getFireStationList().forEach(fireS -> fireStationMap.put(fireS.getAddress() + fireS.getStation(),
                fireS));
    }

    /**
     * Adds the given fire station to fireStationMap.
     *
     * @param fireS the FireStation to be saved
     * @return The FireStation saved
     */
    public FireStation save(final FireStation fireS) {
        LOGGER.debug("Inside FireStationRepository.save for fireStation : " + fireS.getAddress(),
                fireS.getStation());
        fireStationMap.put(fireS.getAddress() + fireS.getStation(), fireS);
        return fireStationMap.get(fireS.getAddress() + fireS.getStation());
    }

    /**
     * Deletes the given fire station from fireStationMap.
     *
     * @param fireS the FireStation to be deleted
     */
    public void delete(final FireStation fireS) {
        LOGGER.debug("Inside FireStationRepository.delete for fireStation : " + fireS.getAddress(),
                fireS.getStation());
        fireStationMap.remove(fireS.getAddress() + fireS.getStation());
    }

    /**
     * Gets the fire station with the given key identifier from medicalRecordsMap.
     *
     * @param address address of the house covered by the fire station
     * @param station fire station number
     * @return The FireStation to be retrieved
     */
    public FireStation find(final String address, final Integer station) {
        LOGGER.debug("Inside FireStationRepository.find for fireStation : " + address,
                station);
        return fireStationMap.get(address + station);
    }

    /**
     * Loops through HashMap values in order to detect the fire station which covers the given address.
     *
     * @param address address of the house covered by the fire station
     * @return The fire station which covers the given address, or null if no fire station is found
     */
    public FireStation findByAddress(final String address) {
        LOGGER.debug("Inside FireStationRepository.findByAddress for address : " + address);
        Collection<FireStation> fireStations = fireStationMap.values();

        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(address)) {
                return fireStation;
            }
        }

        return null;
    }

    /**
     * Loops through HashMap values in order to detect fire stations with the given station number and add them
     * to an ArrayList.
     *
     * @param station fire station number
     * @return The List of fire stations with the given station number retrieved
     */
    public List<FireStation> findByStation(final int station) {
        LOGGER.debug("Inside FireStationRepository.findByStation for station : " + station);
        Collection<FireStation> fireStations = fireStationMap.values();
        List<FireStation> fireStationsByStation = new ArrayList<>();

        for (FireStation fireS : fireStations) {
            if (fireS.getStation() == station) {
                fireStationsByStation.add(fireS);
            }
        }

        return fireStationsByStation;
    }
}

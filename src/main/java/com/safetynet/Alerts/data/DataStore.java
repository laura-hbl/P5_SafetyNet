package com.safetynet.Alerts.data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;
import javax.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads Application Data and stores it into ArrayLists.
 *
 * @author Laura Habdul
 */
@Component
public class DataStore {

    /**
     * DataStore logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(DataStore.class);

    /**
     * The list of all persons.
     */
    private final List<Person> personList = new ArrayList<>();

    /**
     * The list of all fire stations.
     */
    private final List<FireStation> fireStationList = new ArrayList<>();

    /**
     * The list of all medical records.
     */
    private final List<MedicalRecord> medicalRecordList = new ArrayList<>();

    /**
     * Json data filepath, defined in application.properties file.
     */
    @Value("${dataFile}")
    private String dataFilePath;

    @PostConstruct
    public void loadData() throws Exception {

        LOGGER.debug("Inside DataStore.loadData() method");

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream fileInputStream = new FileInputStream(dataFilePath)) {
            JsonNode dataRead = mapper.readTree(fileInputStream);

            // Stores each person into personList
            JsonNode persons = dataRead.at("/persons");
            for (JsonNode node : persons) {
                Person person = new Person(node.get("firstName").asText(),
                        node.get("lastName").asText(),
                        node.get("address").asText(),
                        node.get("city").asText(),
                        node.get("zip").asInt(),
                        node.get("phone").asText(),
                        node.get("email").asText());

                personList.add(person);
            }

            // Stores each fire station into fireStationList
            JsonNode fireStations = dataRead.at("/firestations");
            for (JsonNode node : fireStations) {
                FireStation firestation = new FireStation(node.get("address").asText(),
                        node.get("station").asInt());

                fireStationList.add(firestation);
            }

            // Stores each medical record file into medicalRecordList
            JsonNode medicalRecords = dataRead.at("/medicalrecords");
            for (JsonNode node : medicalRecords) {
                JsonNode lastName = node.at("/lastName");
                JsonNode firstName = node.at("/firstName");
                JsonNode birthDate = node.at("/birthdate");

                List<String> medicationsList = new ArrayList<>();
                List<String> allergiesList = new ArrayList<>();

                JsonNode medications = node.at("/medications");
                for (JsonNode nodeMed : medications) {
                    medicationsList.add(nodeMed.textValue());
                }

                JsonNode allergies = node.at("/allergies");
                for (JsonNode nodeAll : allergies) {
                    allergiesList.add(nodeAll.textValue());
                }

                MedicalRecord medicalRecord = new MedicalRecord(firstName.asText(), lastName.asText(),
                        birthDate.asText(), medicationsList, allergiesList);

                medicalRecordList.add(medicalRecord);
            }
        }
    }

    /**
     * Getter of DataStore.personList.
     *
     * @return The list of all persons
     */
    public List<Person> getPersonList() {
        return personList;
    }

    /**
     * Getter of DataStore.fireStationList.
     *
     * @return The list of all fire stations
     */
    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    /**
     * Getter of DataStore.medicalRecordList.
     *
     * @return The list of all medical records
     */
    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }
}

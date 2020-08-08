package com.safetynet.Alerts.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.Alerts.data.StoredData;
import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DataReader {

    private final static List<Person> personList = new ArrayList<>();
    private final static List<FireStation> fireStationList = new ArrayList<>();
    private final static List<MedicalRecord> medicalRecordList = new ArrayList<>();

    public static StoredData readFile(final String dataFilePath) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream fileInputStream = new FileInputStream(dataFilePath)) {
            JsonNode dataRead = mapper.readTree(fileInputStream);

            JsonNode persons = dataRead.at("/persons");
            for (JsonNode node : persons) {
                Person person = new Person(node.get("firstName").asText(), node.get("lastName").asText(),
                        node.get("address").asText(), node.get("city").asText(), node.get("zip").asInt(),
                        node.get("phone").asText(), node.get("email").asText());

                personList.add(person);
            }

            JsonNode fireStations = dataRead.at("/firestations");
            for (JsonNode node : fireStations) {
                FireStation firestation = new FireStation(node.get("address").asText(), node.get("station").asInt());

                fireStationList.add(firestation);
            }

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

                searchPerson(firstName.asText(), lastName.asText()).setMedicalRecord(medicalRecord);
                medicalRecordList.add(medicalRecord);
            }
        }
        return new StoredData(personList, fireStationList, medicalRecordList);
    }

    public static Person searchPerson(String firstName, String lastName) {
        for (Person person: personList) {
            if (person.getLastName().equals(lastName) && person.getFirstName().equals(firstName)) {

                return person;
            }
        }
        return null;
    }
}

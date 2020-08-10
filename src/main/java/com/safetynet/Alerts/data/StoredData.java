package com.safetynet.Alerts.data;

import com.safetynet.Alerts.model.FireStation;
import com.safetynet.Alerts.model.MedicalRecord;
import com.safetynet.Alerts.model.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoredData {

    private List<Person> personList;
    private List<FireStation> fireStationList;
    private List<MedicalRecord> medicalRecordList;

    public StoredData(List<Person> personList, List<FireStation> fireStationList, List<MedicalRecord>
            medicalRecordList) {
        this.personList = personList;
        this.fireStationList = fireStationList;
        this.medicalRecordList = medicalRecordList;
    }

    public Map<Integer, List<Person>> getPersonsMapToStation() {

        Map<Integer, List<Person>> personsByStation = new HashMap<>();

        for (Person person : personList) {
            for (FireStation firestation : fireStationList) {

                if (person.getAddress().equals(firestation.getAddress())) {
                    int station = firestation.getStation();

                    if (personsByStation.containsKey(station)) {
                        List<Person> list = personsByStation.get(station);
                        list.add(person);
                        personsByStation.put(station, list);
                    } else {
                        List<Person> newList = new ArrayList<>();
                        newList.add(person);
                        personsByStation.put(station, newList);
                    }
                }
            }
        }

        return personsByStation;
    }

    public Map<Integer, List<String>> getAddressMapToStation() {

        Map<Integer, List<String>> addressMapToStation = new HashMap<>();

        for (FireStation fireStation : fireStationList) {
            String address = fireStation.getAddress();
            int station = fireStation.getStation();

            if (addressMapToStation.containsKey(station)) {
                List<String> addressList = addressMapToStation.get(station);
                addressList.add(address);
                addressMapToStation.put(station, addressList);
            } else {
                List<String> addList = new ArrayList<>();
                addList.add(address);
                addressMapToStation.put(station, addList);
            }
        }

        return addressMapToStation;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<FireStation> getFireStationList() {
        return fireStationList;
    }

    public List<MedicalRecord> getMedicalRecordList() {
        return medicalRecordList;
    }

}

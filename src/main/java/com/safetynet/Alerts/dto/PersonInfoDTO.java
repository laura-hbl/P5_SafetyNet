package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonInfo;

import java.util.List;

public class PersonInfoDTO {

    private List<PersonInfo> personsInfo;

    public PersonInfoDTO(List<PersonInfo> personsInfo) {
        this.personsInfo = personsInfo;
    }

    public List<PersonInfo> getPersonsInfo() {
        return personsInfo;
    }

    public void setPersonsInfo(List<PersonInfo> personsInfo) {
        this.personsInfo = personsInfo;
    }
}

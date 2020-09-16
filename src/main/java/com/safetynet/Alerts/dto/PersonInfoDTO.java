package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.PersonInfo;

import java.util.List;

/**
 * Permits the storage and retrieving of a list composed of the person with the given identity as well as persons
 * with the same name.
 *
 * @author Laura Habdul
 */
public class PersonInfoDTO {

    /**
     * List composed of the person with the given identity as well as persons with the same name.
     */
    private List<PersonInfo> personsInfo;

    /**
     * Constructor of class PersonInfoDTO.
     * Initialize personsInfo.
     *
     * @param personsInfo
     */
    public PersonInfoDTO(final List<PersonInfo> personsInfo) {
        this.personsInfo = personsInfo;
    }

    /**
     * Empty constructor of class PersonInfoDTO.
     */
    public PersonInfoDTO() {
    }

    /**
     * Getter of PersonInfoDTO.personsInfo.
     *
     * @return The list composed of the person with the given identity as well as persons with the same name
     */
    public List<PersonInfo> getPersonsInfo() {
        return personsInfo;
    }

    /**
     * Setter of PersonInfoDTO.personsInfo.
     *
     * @param personsInfo list composed of the person with the given identity as well as persons with the same name
     */
    public void setPersonsInfo(final List<PersonInfo> personsInfo) {
        this.personsInfo = personsInfo;
    }
}

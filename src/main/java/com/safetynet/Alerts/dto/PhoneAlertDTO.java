package com.safetynet.Alerts.dto;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of phone numbers of all inhabitants covered by a given station number.
 *
 * @author Laura Habdul
 */
public class PhoneAlertDTO {

    /**
     * The list of persons covered by the given fire station number.
     */
    private List<String> phones;

    /**
     * Constructor of class PhoneAlertDTO.
     * Initialize phones
     *
     * @param phones list of phone numbers
     */
    public PhoneAlertDTO(final List<String> phones) {
        this.phones = phones;
    }

    /**
     * Empty constructor of class PhoneAlertDTO.
     */
    public PhoneAlertDTO() {
    }

    /**
     * Getter of PhoneAlertDTO.phones.
     *
     * @return The list of phone numbers of all inhabitants covered by a given station number
     */
    public List<String> getPhones() {
        return phones;
    }

    /**
     * Setter of PhoneAlertDTO.phones.
     *
     * @param phones list of phone numbers of all inhabitants covered by a given station number to set
     */
    public void setPhones(final List<String> phones) {
        this.phones = phones;
    }
}

package com.safetynet.Alerts.dto;

import java.util.List;

public class PhoneAlertDTO {

    private List<String> phones;

    public PhoneAlertDTO(List<String> phones) {
        this.phones = phones;
    }

    public PhoneAlertDTO() {
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}

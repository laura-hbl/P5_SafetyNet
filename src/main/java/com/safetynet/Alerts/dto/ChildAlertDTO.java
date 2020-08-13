package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.Child;

import java.util.List;

public class ChildAlertDTO {

    private List<Child> Child;
    private List<String> homeMembers;

    public ChildAlertDTO(List<com.safetynet.Alerts.model.Child> child, List<String> homeMembers) {
        Child = child;
        this.homeMembers = homeMembers;
    }

    public List<com.safetynet.Alerts.model.Child> getChild() {
        return Child;
    }

    public void setChild(List<com.safetynet.Alerts.model.Child> child) {
        Child = child;
    }

    public List<String> getHomeMembers() {
        return homeMembers;
    }

    public void setHomeMembers(List<String> homeMembers) {
        this.homeMembers = homeMembers;
    }
}

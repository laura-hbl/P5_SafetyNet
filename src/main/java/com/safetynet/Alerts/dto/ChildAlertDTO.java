package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.Child;

import java.util.List;

public class ChildAlertDTO {

    private List<Child> child;
    private List<String> homeMembers;

    public ChildAlertDTO(List<Child> child, List<String> homeMembers) {
        this.child = child;
        this.homeMembers = homeMembers;
    }

    public ChildAlertDTO() {
    }

    public List<Child> getChild() {
        return child;
    }

    public void setChild(List<Child> child) {
        this.child = child;
    }

    public List<String> getHomeMembers() {
        return homeMembers;
    }

    public void setHomeMembers(List<String> homeMembers) {
        this.homeMembers = homeMembers;
    }
}

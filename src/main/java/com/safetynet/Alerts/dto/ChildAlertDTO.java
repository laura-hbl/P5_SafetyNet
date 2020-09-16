package com.safetynet.Alerts.dto;

import com.safetynet.Alerts.model.Child;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of children and other household members living at a given address.
 *
 * @author Laura Habdul
 */
public class ChildAlertDTO {

    /**
     * List of children in the household.
     */
    private List<Child> child;

    /**
     * List of other household members.
     */
    private List<String> homeMembers;

    /**
     * Constructor of class ChildAlertDTO.
     * Initialize child and homeMembers.
     *
     * @param child
     * @param homeMembers
     */
    public ChildAlertDTO(final List<Child> child, final List<String> homeMembers) {
        this.child = child;
        this.homeMembers = homeMembers;
    }

    /**
     * Empty constructor of class ChildAlertDTO.
     */
    public ChildAlertDTO() {
    }

    /**
     * Getter of ChildAlertDTO.child.
     *
     * @return The list of children in the household.
     */
    public List<Child> getChild() {
        return child;
    }

    /**
     * Setter of ChildAlertDTO.child.
     *
     * @param child list of children in the household to set
     */
    public void setChild(final List<Child> child) {
        this.child = child;
    }

    /**
     * Getter of ChildAlertDTO.homeMembers.
     *
     * @return The list of other household members.
     */
    public List<String> getHomeMembers() {
        return homeMembers;
    }

    /**
     * Setter of ChildAlertDTO.homeMembers.
     *
     * @param homeMembers list of other household members to set
     */
    public void setHomeMembers(final List<String> homeMembers) {
        this.homeMembers = homeMembers;
    }
}

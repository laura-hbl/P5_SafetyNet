package com.safetynet.Alerts.dto;

import java.util.List;

/**
 * Permits the storage and retrieving of the list of e-mail of all inhabitants living at a given city.
 *
 * @author Laura Habdul
 */
public class CommunityEmailDTO {

    /**
     * List of e-mail of all inhabitants living at the given city.
     */
    private List<String> emails;

    /**
     * Constructor of class CommunityEmailDTO.
     * Initialize emails.
     *
     * @param emails list of e-mail of all inhabitants living at a given city
     */
    public CommunityEmailDTO(final List<String> emails) {
        this.emails = emails;
    }

    /**
     * Empty constructor of class CommunityEmailDTO.
     */
    public CommunityEmailDTO() {
    }

    /**
     * Getter of CommunityEmailDTO.emails.
     *
     * @return The list of e-mail of all inhabitants living at the given city.
     */
    public List<String> getEmails() {
        return emails;
    }

    /**
     * Setter of CommunityEmailDTO.emails.
     *
     * @param emails list of e-mail of all inhabitants living at a given city to set
     */
    public void setEmails(final List<String> emails) {
        this.emails = emails;
    }
}

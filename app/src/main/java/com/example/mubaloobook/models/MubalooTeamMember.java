package com.example.mubaloobook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Model representing an individual employed by Mubaloo, holding information such as their name,
 * job description, etc
 */

@DatabaseTable(tableName = "mubaloo_team_member")
public class MubalooTeamMember {

    @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "mubaloo_team")
    private MubalooTeam mubalooTeam;

    public void setMubalooTeam(MubalooTeam mubalooTeam) {
        this.mubalooTeam = mubalooTeam;
    }

    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private String firstName;

    @DatabaseField
    private String lastName;

    @DatabaseField
    private String role;

    @DatabaseField
    private String profileImageURL;

    @DatabaseField
    private Boolean teamLead;

    public MubalooTeamMember() {
        // empty constructor required for GSON serialisation & ORMLite
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public Boolean isTeamLead() {
        return teamLead;
    }

    public void setTeamLead(boolean teamLead) {
        this.teamLead = teamLead;
    }

    /**
     * Construct a name from the firstName and lastName fields
     * @return a name in the format 'John Smith'
     */
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }

}

package com.example.mubaloobook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Model that holds a list of MubalooTeamMembers and team name, thus representing an internal team
 * at Mubaloo e.g. Web Developers
 */

@DatabaseTable(tableName = "mubaloo_team")
public class MubalooTeam {

    @DatabaseField(id = true)
    private String teamName;

    private List<MubalooTeamMember> members;

    @ForeignCollectionField(eager = true)
    private Collection<MubalooTeamMember> membersCollection;

    public MubalooTeam() {
        // empty constructor required for GSON serialisation & ORMLite
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<MubalooTeamMember> getMembers() {
        if (this.members == null) {
            this.members = new ArrayList<>(this.membersCollection);
        }
        return members;
    }

    public void setMembers(List<MubalooTeamMember> members) {
        this.members = members;
        this.membersCollection = members;
    }

    public Collection<MubalooTeamMember> getMembersCollection() {
        return membersCollection;
    }

}

package com.example.mubaloobook.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Model that holds a list of MubalooTeamMembers and team name, thus representing an internal team
 * at Mubaloo e.g. Web Developers
 */

@DatabaseTable(tableName = "mubaloo_team")
public class MubalooTeam {

    @DatabaseField(generatedId = true)
    private int genId;

    @DatabaseField
    private String teamName;

    // populated via GSON, persisted via foreigncollection
    @DatabaseField
    private List<MubalooTeamMember> members;

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
        return members;
    }

    public void setMembers(List<MubalooTeamMember> members) {
        this.members = members;
    }

}

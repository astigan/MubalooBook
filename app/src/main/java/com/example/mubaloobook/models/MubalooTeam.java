package com.example.mubaloobook.models;

import java.util.List;

/**
 * Model that holds a list of MubalooTeamMembers and team name, thus representing an internal team
 * at Mubaloo e.g. Web Developers
 */
public class MubalooTeam {

    private String teamName;
    private List<MubalooTeamMember> members;

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

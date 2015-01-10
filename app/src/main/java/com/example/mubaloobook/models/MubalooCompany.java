package com.example.mubaloobook.models;

import com.example.mubaloobook.network.MubalooApiResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Model that holds a list of all teams in the company
 */
public class MubalooCompany {

    private List<MubalooTeam> teamList;

    public MubalooCompany(MubalooApiResponse apiResponse) {

        MubalooTeam corporateTeam = new MubalooTeam();
        corporateTeam.setTeamName("Corporate");

        List<MubalooTeamMember> corporateList = new ArrayList<>();
        corporateTeam.setMembers(corporateList);

        MubalooTeamMember ceo = new MubalooTeamMember(apiResponse);
        corporateList.add(ceo);

        // TODO set teamList
        teamList.add(corporateTeam);
    }

    public List<MubalooTeam> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<MubalooTeam> teamList) {
        this.teamList = teamList;
    }

}

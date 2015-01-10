
package com.example.mubaloobook.network;

import com.example.mubaloobook.models.MubalooTeam;

import java.util.List;

public class MubalooApiResponse {

    private List<MubalooTeam> members;

    public List<MubalooTeam> getMembers() {
        return members;
    }

    public void setMembers(List<MubalooTeam> members) {
        this.members = members;
    }

}

package com.example.mubaloobook.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mubaloobook.R;
import com.example.mubaloobook.models.MubalooTeam;

import java.util.List;

import butterknife.ButterKnife;

public class TeamMemberListFragment extends Fragment {

    public static final String TAG = "TeamMemberListFragment";

    public static TeamMemberListFragment newInstance() {
        TeamMemberListFragment fragment = new TeamMemberListFragment();
        Bundle extras = new Bundle();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_member_list, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    public void setDisplayedTeams(List<MubalooTeam> teamsList) {
        // TODO update UI
    }

}

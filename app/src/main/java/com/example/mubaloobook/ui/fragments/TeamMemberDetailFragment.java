package com.example.mubaloobook.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mubaloobook.R;

import butterknife.ButterKnife;

public class TeamMemberDetailFragment extends Fragment {

    public static final String TAG = "TeamMemberDetailFragment";

    public static TeamMemberDetailFragment newInstance() {
        TeamMemberDetailFragment fragment = new TeamMemberDetailFragment();
        Bundle extras = new Bundle();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_team_member_detail, container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    public void setDisplayedTeamMember() {
        // TODO update UI
    }

}

package com.example.mubaloobook.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mubaloobook.R;
import com.example.mubaloobook.models.MubalooTeamMember;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeamMemberDetailFragment extends Fragment {

    public static final String TAG = "TeamMemberDetailFragment";

    @InjectView(R.id.member_profile_pic) CircleImageView profilePic;
    @InjectView(R.id.member_profile_name) TextView profileName;
    @InjectView(R.id.member_role_desc) TextView roleDescription;

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

    public void setDisplayedTeamMember(MubalooTeamMember teamMember) {

        String fullName = teamMember.getFirstName() + " " + teamMember.getLastName();
        String roleText = getString(R.string.role_wildcard, teamMember.getRole());

        profileName.setText(fullName);
        roleDescription.setText(roleText);

        Context context = getActivity();

        if (context != null) {
            Picasso.with(context).load(teamMember.getProfileImageURL()).into(profilePic);
        }
    }

}

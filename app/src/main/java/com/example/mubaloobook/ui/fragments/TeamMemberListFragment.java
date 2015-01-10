package com.example.mubaloobook.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.mubaloobook.MainActivity;
import com.example.mubaloobook.R;
import com.example.mubaloobook.models.MubalooTeam;
import com.example.mubaloobook.models.MubalooTeamMember;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TeamMemberListFragment extends Fragment {

    public static final String TAG = "TeamMemberListFragment";

    public interface ListFragmentListener {
        public void onTeamMemberSelected(MubalooTeamMember teamMember);
    }

    private ListFragmentListener listener;

    @InjectView(R.id.team_list_view) ExpandableListView teamListView;

    public static TeamMemberListFragment newInstance() {
        TeamMemberListFragment fragment = new TeamMemberListFragment();
        Bundle extras = new Bundle();
        fragment.setArguments(extras);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();

        if (activity != null && activity instanceof ListFragmentListener) {
            listener = (ListFragmentListener) activity;
        }
        else {
            throw new RuntimeException("Host Activity must implement ListFragmentListener!");
        }
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

    private class TeamListAdapter extends BaseExpandableListAdapter {

        @Override
        public int getGroupCount() {
            return 0;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return 0;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return null;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return null;
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            return null;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}

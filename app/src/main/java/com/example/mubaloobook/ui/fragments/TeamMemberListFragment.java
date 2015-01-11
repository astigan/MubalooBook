package com.example.mubaloobook.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.mubaloobook.MainActivity;
import com.example.mubaloobook.R;
import com.example.mubaloobook.models.MubalooTeam;
import com.example.mubaloobook.models.MubalooTeamMember;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeamMemberListFragment extends Fragment {

    public static final String TAG = "TeamMemberListFragment";

    public interface ListFragmentListener {
        public void onTeamMemberSelected(MubalooTeamMember teamMember);
        public void onListFragmentLoaded();
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (listener != null) {
            listener.onListFragmentLoaded();
        }
    }

    public void setDisplayedTeams(List<MubalooTeam> teamsList) {

        if (teamsList != null) {
            // TODO update UI
            final TeamListAdapter adapter = new TeamListAdapter(getActivity(), teamsList);

            teamListView.setAdapter(adapter);
            teamListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    if (listener != null) {
                        MubalooTeamMember selectedMember = (MubalooTeamMember) adapter.getChild(groupPosition, childPosition);
                        listener.onTeamMemberSelected(selectedMember);
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    private class TeamListAdapter extends BaseExpandableListAdapter {

        private LayoutInflater inflater;
        private List<MubalooTeam> teamList;
        private Context context;

        public TeamListAdapter(Context context, List<MubalooTeam> teamList) {
            this.teamList = teamList;
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getGroupCount() {
            return this.teamList.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return this.teamList.get(groupPosition).getMembers().size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return this.teamList.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return this.teamList.get(groupPosition).getMembers().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition; // FIXME not unique
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            View groupView = inflater.inflate(R.layout.team_list_item, parent, false);
            MubalooTeam currentTeam = (MubalooTeam) getGroup(groupPosition);

            TextView teamName = (TextView) groupView.findViewById(R.id.team_name);
            teamName.setText(currentTeam.getTeamName());

            return groupView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            View childView = inflater.inflate(R.layout.member_list_item, parent, false);
            MubalooTeamMember currentMember = (MubalooTeamMember) getChild(groupPosition, childPosition);

            TextView memberName = (TextView) childView.findViewById(R.id.member_name_label);
            CircleImageView memberProfilePic = (CircleImageView) childView.findViewById(R.id.item_profile_pic);

            memberName.setText(currentMember.getFullName());
            Picasso.with(context).load(currentMember.getProfileImageURL()).into(memberProfilePic);

            return childView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

}

package com.example.mubaloobook;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mubaloobook.log.Logger;
import com.example.mubaloobook.models.MubalooTeam;
import com.example.mubaloobook.models.MubalooTeamMember;
import com.example.mubaloobook.network.RestClient;
import com.example.mubaloobook.ui.fragments.TeamMemberDetailFragment;
import com.example.mubaloobook.ui.fragments.TeamMemberListFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity implements
        TeamMemberListFragment.ListFragmentListener, TeamMemberDetailFragment.DetailFragmentListener {

    private TeamMemberListFragment teamMemberListFragment;
    private TeamMemberDetailFragment teamMemberDetailFragment;

    private MubalooTeamMember currentTeamMember;
    private List<MubalooTeam> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isTablet()) {
            teamMemberListFragment = TeamMemberListFragment.newInstance();
            teamMemberDetailFragment = TeamMemberDetailFragment.newInstance();
            addFragmentToContainer(R.id.list_fragment_container, teamMemberListFragment, TeamMemberListFragment.TAG);
            addFragmentToContainer(R.id.detail_fragment_container, teamMemberDetailFragment, TeamMemberDetailFragment.TAG);
        }
        else {
            teamMemberListFragment = TeamMemberListFragment.newInstance();
            addFragmentToContainer(R.id.fragment_container, teamMemberListFragment, TeamMemberListFragment.TAG);
        }

        if (!isNetworkAvailable()) {
            Log.i(Logger.TAG, "Not connected to a network");
            // TODO display feedback in UI
        }

        requestTeamInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = cm.getActiveNetworkInfo();
        return (network != null && network.isConnectedOrConnecting());
    }

    // FIXME first object in array is not serialised because it is a different type
    private void requestTeamInfo() {

        RestClient.get().getMubalooTeam(new Callback<List<JsonElement>>() {
            @Override
            public void success(List<JsonElement> jsonElements, Response response) {

                List<MubalooTeam> teamList = new ArrayList<MubalooTeam>();
                Gson gson = new Gson();

                for (int i=0; i<jsonElements.size(); i++) {

                    // if Json object has attr teamName, assume it is of type MubalooTeam
                    JsonElement currentElement = jsonElements.get(i);
                    boolean isTeam = currentElement.getAsJsonObject().has("teamName");

                    if (isTeam) {
                        MubalooTeam corporateTeam = new MubalooTeam();
                        corporateTeam.setTeamName("Corporate");

                        MubalooTeamMember ceo = gson.fromJson(jsonElements.get(i), MubalooTeamMember.class);
                        List<MubalooTeamMember> corporateList = new ArrayList<>();

                        corporateList.add(ceo);
                        corporateTeam.setMembers(corporateList);

                        teamList.add(corporateTeam);
                    }
                    else {
                        MubalooTeam team = gson.fromJson(jsonElements.get(i), MubalooTeam.class);
                        teamList.add(team);
                    }
                }

                teamMemberListFragment.setDisplayedTeams(teamList);


//                Collection collection = teamList.get(1).getMembers();
                int i = 0;
                Log.i(Logger.TAG, "Successful request for mubaloo team info");
                // TODO store in DB
            }

            @Override
            public void failure(RetrofitError error) {
                Log.w(Logger.TAG, "Failed request for mubaloo team info ", error);
                // TODO display UI feedback
            }
        });
    }

    private void addFragmentToContainer(int containerId, Fragment fragment, String tag) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction =  fm.beginTransaction();

        transaction.replace(containerId, fragment, tag);
        transaction.addToBackStack(tag);
        transaction.commit();

        fm.executePendingTransactions();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getFragmentManager();

        if (!isTablet() && fm.getBackStackEntryCount() > 1) {
            fm.popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    // *** Fragment callbacks ***

    @Override
    public void onTeamMemberSelected(MubalooTeamMember teamMember) {

        currentTeamMember = teamMember;


//        DbHelper dbHelper = new DbHelper(this);
//        try {
//            Dao<MubalooTeam, Integer> daoTeam = dbHelper.getMubalooTeamDao();
//            MubalooTeam team = teamList.get(1);
//            daoTeam.createOrUpdate(team);
//
//            List<MubalooTeam> teamData = daoTeam.queryForAll();
//            List<MubalooTeamMember> teamMembersData = teamData.get(0).getMembers();
//            int i = 0;
//
//        } catch (SQLException e) {
//            Log.e(Logger.TAG, "DAO exception", e);
//        }



        if (!isTablet()) {
            teamMemberDetailFragment = TeamMemberDetailFragment.newInstance();
            addFragmentToContainer(R.id.fragment_container, teamMemberDetailFragment, TeamMemberDetailFragment.TAG);
        }
        else {
            teamMemberDetailFragment.setDisplayedTeamMember(teamMember);
        }
        // TODO set selected team member when attached
    }

    @Override
    public void onDetailFragmentLoaded() {
        if (!isTablet()) {
            teamMemberDetailFragment.setDisplayedTeamMember(currentTeamMember);
        }
    }

    @Override
    public void onListFragmentLoaded() {
        teamMemberListFragment.setDisplayedTeams(teamList);
    }

}

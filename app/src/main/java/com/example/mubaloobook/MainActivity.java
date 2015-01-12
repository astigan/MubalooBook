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
import android.widget.Toast;

import com.example.mubaloobook.db.DbHelper;
import com.example.mubaloobook.log.Logger;
import com.example.mubaloobook.models.MubalooTeam;
import com.example.mubaloobook.models.MubalooTeamMember;
import com.example.mubaloobook.network.RestClient;
import com.example.mubaloobook.ui.fragments.TeamMemberDetailFragment;
import com.example.mubaloobook.ui.fragments.TeamMemberListFragment;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
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
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

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
            Toast.makeText(this, getString(R.string.no_connection_message), Toast.LENGTH_LONG).show();
            updateUiOnDataChange();
        }
        else {
            requestTeamInfo();
        }
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

    private void requestTeamInfo() {

        RestClient.get().getMubalooTeam(new Callback<List<JsonElement>>() {
            @Override
            public void success(List<JsonElement> jsonElements, Response response) {

                MubalooTeam corporateTeam = new MubalooTeam();
                corporateTeam.setTeamName("Corporate");

                List<MubalooTeamMember> corporateList = new ArrayList<>();
                corporateTeam.setMembers(corporateList);

                teamList = new ArrayList<>();
                teamList.add(corporateTeam);

                Gson gson = new Gson();

                // if Json object has attr teamName, assume it is of type MubalooTeam
                for (int i=0; i<jsonElements.size(); i++) {
                    JsonElement currentElement = jsonElements.get(i);
                    boolean isTeam = currentElement.getAsJsonObject().has("teamName");

                    if (isTeam) {
                        MubalooTeam team = gson.fromJson(jsonElements.get(i), MubalooTeam.class);
                        teamList.add(team);
                    }
                    else {
                        MubalooTeamMember ceo = gson.fromJson(jsonElements.get(i), MubalooTeamMember.class);
                        corporateList.add(ceo);
                    }
                }
                updateDatabaseRecords(teamList);
                teamMemberListFragment.setDisplayedTeams(teamList);
//                updateUiOnDataChange();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(context, "API request failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDatabaseRecords(List<MubalooTeam> teamList) {
        DbHelper dbHelper = new DbHelper(this);

        try {
            Dao<MubalooTeam, Integer> dao = dbHelper.getMubalooTeamDao();

            for (MubalooTeam team : teamList) {
                dao.createOrUpdate(team);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Caught SQL exception updating team members");
        }
    }

    private List<MubalooTeam> getTeamListFromDb() {
        DbHelper dbHelper = new DbHelper(this);
        List<MubalooTeam> teamList;

        try {
            Dao<MubalooTeam, Integer> dao = dbHelper.getMubalooTeamDao();
            teamList = dao.queryForAll();

            for (MubalooTeam team : teamList) {
                dao.createOrUpdate(team);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Caught SQL exception loading team members from DB");
        }
        return teamList;
    }

    private void updateUiOnDataChange() {
        teamList = getTeamListFromDb();
        teamMemberListFragment.setDisplayedTeams(teamList);
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

        DbHelper dbHelper = new DbHelper(this);
        try {
            Dao<MubalooTeam, Integer> daoTeam = dbHelper.getMubalooTeamDao();
            MubalooTeam team = teamList.get(1);
            team.setMembers(team.getMembers());
            daoTeam.createOrUpdate(team);

            Dao<MubalooTeamMember, Integer> daoMember = dbHelper.getMubalooTeamMemberDao();

            for (MubalooTeamMember member : team.getMembers()) {
                member.setMubalooTeam(team);
                daoMember.createOrUpdate(member);
            }

        } catch (SQLException e) {
            Log.e(Logger.TAG, "DAO exception", e);
        }

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
        updateUiOnDataChange();
        teamMemberListFragment.setDisplayedTeams(teamList);
    }

}

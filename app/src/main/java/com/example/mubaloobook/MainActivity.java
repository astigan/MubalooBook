package com.example.mubaloobook;

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
import com.example.mubaloobook.network.RestClient;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    private void requestTeamInfo() {

        RestClient.get().getMubalooTeam(new Callback<List<MubalooTeam>>() {
            @Override
            public void success(List<MubalooTeam> mubalooTeamResponses, Response response) {
                List<MubalooTeam> teams = mubalooTeamResponses;
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

}

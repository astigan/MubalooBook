package com.example.mubaloobook.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mubaloobook.log.Logger;
import com.example.mubaloobook.models.MubalooTeam;
import com.example.mubaloobook.models.MubalooTeamMember;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DbHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "mubaloobook.db";
    private static final int DB_VERSION = 1; // update whenever schema is changed

    private Dao<MubalooTeamMember, Integer> teamMemberDao = null;
    private Dao<MubalooTeam, Integer> teamDao = null;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MubalooTeam.class);
            TableUtils.createTable(connectionSource, MubalooTeamMember.class);
        }
        catch (SQLException e) {
            Log.e(Logger.TAG, "Error creating DB", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        // Not applicable
    }

    public Dao<MubalooTeamMember, Integer> getMubalooTeamMemberDao() throws SQLException {
        if (teamMemberDao == null) {
            teamMemberDao = getDao(MubalooTeamMember.class);
        }
        return teamMemberDao;
    }

    public Dao<MubalooTeam, Integer> getMubalooTeamDao() throws SQLException {
        if (teamDao == null) {
            teamDao = getDao(MubalooTeam.class);
        }
        return teamDao;
    }

}

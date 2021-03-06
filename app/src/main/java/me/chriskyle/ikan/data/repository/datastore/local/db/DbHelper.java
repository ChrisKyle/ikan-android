package me.chriskyle.ikan.data.repository.datastore.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import me.chriskyle.ikan.data.entity.SegmentEntity;
import me.chriskyle.ikan.data.entity.WatchHistoryEntity;
import me.chriskyle.ikan.presentation.module.feed.download.DownloadEntity;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Description :
 * <p>
 * Created by Chris Kyle on 2017/10/26.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Ikan.db";
    private static final int DATABASE_VERSION = 1;

    private static SQLiteDatabase database;

    static {
        cupboard().register(DownloadEntity.class);
        cupboard().register(WatchHistoryEntity.class);
        cupboard().register(SegmentEntity.class);
    }

    public synchronized static SQLiteDatabase getConnection(Context context) {
        if (database == null) {
            database = new DbHelper(context.getApplicationContext()).getWritableDatabase();
        }
        return database;
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}

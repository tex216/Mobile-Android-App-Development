package edu.gatech.seclass.jobcompare6300;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {JOB_DETAILS.class, COMPARISON_SETTINGS_WEIGHT.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "database-jobcompare";
    private static volatile AppDatabase appDatabase;

    static synchronized  AppDatabase getInstance(Context context) {
        return getInstance(context, false);
    }

    static synchronized AppDatabase getInstance(Context context, boolean resetDatabase){
        if (resetDatabase) context.deleteDatabase(DB_NAME);
        if (appDatabase == null) appDatabase = create(context);
        return appDatabase;
    }

    private static AppDatabase create(final Context context){
        return Room.databaseBuilder(context,AppDatabase.class,DB_NAME).build();
    }

    public abstract JobDetailsDao jobDetailsDao();
    public abstract ComparisonSettingsWeightDao comparisonSettingsWeightDao();
}

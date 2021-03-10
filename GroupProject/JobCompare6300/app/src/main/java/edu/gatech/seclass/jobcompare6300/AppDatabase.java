package edu.gatech.seclass.jobcompare6300;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {JOB_DETAILS.class, COMPARISON_SETTINGS_WEIGHT.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobDetailsDao jobDetailsDao();
    public abstract ComparisonSettingsWeightDao comparisonSettingsweightDao();
}

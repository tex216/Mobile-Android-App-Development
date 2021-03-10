package edu.gatech.seclass.jobcompare6300;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {JobDetails.class, ComparisonSettings.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobDetailsDao JobDetailsDao();
}

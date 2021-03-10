package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "JobDetails.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table JobDetails (JobID Int primary key, Title TEXT, Company TEXT, City TEXT, State TEXT, " +
                "CostofLivingIndex INT, workRemote INT, YearlySalary DOUBLE, YearlyBonus DOUBLE, PecentageMatched DOUBLE, " +
                "LeaveTime INT, isCurrentJob BOOL, Score INT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists JobDetails");

    }
}


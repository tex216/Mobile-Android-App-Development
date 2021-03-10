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
        db.execSQL("create Table JobDetails (JobID INT primary key, Title TEXT, Company TEXT, City TEXT, State TEXT, " +
                "CostofLivingIndex INT, WorkRemote INT, YearlySalary DOUBLE, YearlyBonus DOUBLE, PecentageMatched DOUBLE, " +
                "LeaveTime INT, isCurrentJob BOOL, Score INT)");

        db.execSQL("create Table JobOffers (JobID INT primary key, Title TEXT, Company TEXT)");

        db.execSQL("create Table ComparisonSettings ( RemoteWorkPossibility INT, YearlySalary INT, YearlyBonus INT, " +
                "RetirementBenefits INT, LeaveTime INT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists JobDetails");
        db.execSQL("drop Table if exists JobOffers");
        db.execSQL("drop Table if exists ComparisonSettings");

    }
}


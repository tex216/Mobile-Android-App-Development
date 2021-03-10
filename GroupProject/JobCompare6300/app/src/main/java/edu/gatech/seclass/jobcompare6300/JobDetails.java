package edu.gatech.seclass.jobcompare6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JobDetails {
    @PrimaryKey(autoGenerate = true)
    public int JOB_ID;

    @ColumnInfo(name = "TITLE")
    public String TITLE;

    @ColumnInfo(name = "COMPANY")
    public String COMPANY;

    @ColumnInfo(name = "CITY")
    public String CITY;

    @ColumnInfo(name = "STATE")
    public String STATE;

    @ColumnInfo(name = "COST_OF_LIVING_INDEX")
    public int COST_OF_LIVING_INDEX;

    @ColumnInfo(name = "WORK_REMOTE")
    public int WORK_REMOTE;

    @ColumnInfo(name = "YEARLY_SALARY")
    public Double YEARLY_SALARY;

    @ColumnInfo(name = "YEARLY_BONUS")
    public Double YEARLY_BONUS;

    @ColumnInfo(name = "PERCENTAGE_MATCHED")
    public Double PERCENTAGE_MATCHED;

    @ColumnInfo(name = "LEAVE_TIME")
    public Double LEAVE_TIME;

    @ColumnInfo(name = "IS_CURRENT_JOB")
    public Boolean IS_CURRENT_JOB;

    @ColumnInfo(name = "SCORE")
    public Double SCORE;
}
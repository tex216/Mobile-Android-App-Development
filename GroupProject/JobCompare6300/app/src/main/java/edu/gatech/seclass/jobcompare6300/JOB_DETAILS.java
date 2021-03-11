package edu.gatech.seclass.jobcompare6300;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.security.PublicKey;

@Entity
public class JOB_DETAILS {
    @PrimaryKey(autoGenerate = true)
    private int JOB_ID;

    @ColumnInfo(name = "TITLE")
    private String TITLE;

    @ColumnInfo(name = "COMPANY")
    private String COMPANY;

    @ColumnInfo(name = "CITY")
    private String CITY;

    @ColumnInfo(name = "STATE")
    private String STATE;

    @ColumnInfo(name = "COST_OF_LIVING_INDEX")
    private int COST_OF_LIVING_INDEX;

    @ColumnInfo(name = "WORK_REMOTE")
    private int WORK_REMOTE;

    @ColumnInfo(name = "YEARLY_SALARY")
    private double YEARLY_SALARY;

    @ColumnInfo(name = "YEARLY_BONUS")
    private double YEARLY_BONUS;

    @ColumnInfo(name = "PERCENTAGE_MATCHED")
    private double PERCENTAGE_MATCHED;

    @ColumnInfo(name = "LEAVE_TIME")
    private int LEAVE_TIME;

    @ColumnInfo(name = "IS_CURRENT_JOB")
    private boolean IS_CURRENT_JOB;

    @ColumnInfo(name = "SCORE")
//    @Nullable
    private double SCORE;

    public JOB_DETAILS(@NonNull String TITLE, String COMPANY, String CITY, String STATE,
                       int COST_OF_LIVING_INDEX, int WORK_REMOTE, double YEARLY_SALARY,double YEARLY_BONUS,
                       double PERCENTAGE_MATCHED, int LEAVE_TIME, boolean IS_CURRENT_JOB, double SCORE) {
        this.TITLE = TITLE;
        this.COMPANY = COMPANY;
        this.CITY = CITY;
        this.STATE = STATE;
        this.COST_OF_LIVING_INDEX = COST_OF_LIVING_INDEX;
        this.WORK_REMOTE = WORK_REMOTE;
        this.YEARLY_SALARY = YEARLY_SALARY;
        this.YEARLY_BONUS = YEARLY_BONUS;
        this.PERCENTAGE_MATCHED = PERCENTAGE_MATCHED;
        this.LEAVE_TIME = LEAVE_TIME;
        this.IS_CURRENT_JOB = IS_CURRENT_JOB;
        this.SCORE = SCORE;
    }

    public int getJOB_ID() { return this.JOB_ID; }

    public String getTITLE() {
        return this.TITLE;
    }

    public String getCOMPANY() {
        return this.COMPANY;
    }

    public String getCITY(){
        return this.CITY;
    }

    public String getSTATE(){
        return this.STATE;
    }

    public int getCOST_OF_LIVING_INDEX(){
        return this.COST_OF_LIVING_INDEX;
    }

    public int getWORK_REMOTE(){
        return this.WORK_REMOTE;
    }

    public double getYEARLY_SALARY(){
        return this.YEARLY_SALARY;
    }

    public double getYEARLY_BONUS(){
        return this.YEARLY_BONUS;
    }

    public double getPERCENTAGE_MATCHED(){
        return this.PERCENTAGE_MATCHED;
    }

    public int getLEAVE_TIME(){
        return this.LEAVE_TIME;
    }

    public boolean getIS_CURRENT_JOB(){
        return this.IS_CURRENT_JOB;
    }

    public double getSCORE(){
        return this.SCORE;
    }
}
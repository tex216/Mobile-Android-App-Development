package edu.gatech.seclass.jobcompare6300;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.security.PublicKey;

@Entity
public class JOB_DETAILS implements Serializable {
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
    @Nullable
    private Double SCORE;

    @Ignore
    boolean selectedItem = false;

    @Ignore
    public JOB_DETAILS(String TITLE, String COMPANY, String CITY, String STATE,
                       int COST_OF_LIVING_INDEX, int WORK_REMOTE, double YEARLY_SALARY,double YEARLY_BONUS,
                       double PERCENTAGE_MATCHED, int LEAVE_TIME, boolean IS_CURRENT_JOB) {
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
    }

    public JOB_DETAILS(String TITLE, String COMPANY, String CITY, String STATE,
                       int COST_OF_LIVING_INDEX, int WORK_REMOTE, double YEARLY_SALARY,double YEARLY_BONUS,
                       double PERCENTAGE_MATCHED, int LEAVE_TIME, boolean IS_CURRENT_JOB, Double SCORE) {
        this(TITLE, COMPANY, CITY, STATE, COST_OF_LIVING_INDEX, WORK_REMOTE, YEARLY_SALARY, YEARLY_BONUS, PERCENTAGE_MATCHED, LEAVE_TIME, IS_CURRENT_JOB);
        this.SCORE = SCORE;
    }


    public void setJOB_ID(int job_id) {
        this.JOB_ID = job_id;
    }

    public int getJOB_ID() {
        return this.JOB_ID;
    }

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

    public Double getSCORE(){
        return this.SCORE;
    }

    public boolean active(boolean selectedItem){
        return selectedItem = !selectedItem;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public void setCOMPANY(String COMPANY) {
        this.COMPANY = COMPANY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public void setCOST_OF_LIVING_INDEX(int COST_OF_LIVING_INDEX) {
        this.COST_OF_LIVING_INDEX = COST_OF_LIVING_INDEX;
    }

    public void setWORK_REMOTE(int WORK_REMOTE) {
        this.WORK_REMOTE = WORK_REMOTE;
    }

    public void setYEARLY_SALARY(double YEARLY_SALARY) {
        this.YEARLY_SALARY = YEARLY_SALARY;
    }

    public void setYEARLY_BONUS(double YEARLY_BONUS) {
        this.YEARLY_BONUS = YEARLY_BONUS;
    }

    public void setPERCENTAGE_MATCHED(double PERCENTAGE_MATCHED) {
        this.PERCENTAGE_MATCHED = PERCENTAGE_MATCHED;
    }

    public void setLEAVE_TIME(int LEAVE_TIME) {
        this.LEAVE_TIME = LEAVE_TIME;
    }

    public void setIS_CURRENT_JOB(boolean IS_CURRENT_JOB) {
        this.IS_CURRENT_JOB = IS_CURRENT_JOB;
    }

    public void setSCORE(@Nullable Double SCORE) {
        this.SCORE = SCORE;
    }
}
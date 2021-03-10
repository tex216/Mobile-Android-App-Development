package edu.gatech.seclass.jobcompare6300;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class JobDetails {
    @PrimaryKey
    public int jobID;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "company")
    public String company;

    @ColumnInfo(name = "city")
    public String city;
}
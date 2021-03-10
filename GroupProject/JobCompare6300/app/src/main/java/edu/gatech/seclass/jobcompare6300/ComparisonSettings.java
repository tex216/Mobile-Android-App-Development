package edu.gatech.seclass.jobcompare6300;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ComparisonSettings {
    @ColumnInfo(name = "WEIGHT")
    public String WEIGHT;

    @ColumnInfo(name = "DATA_TYPE")
    public String DATA_TYPE;

    @ColumnInfo(name = "WEIGHT_VALUE")
    public int WEIGHT_VALUE;
}

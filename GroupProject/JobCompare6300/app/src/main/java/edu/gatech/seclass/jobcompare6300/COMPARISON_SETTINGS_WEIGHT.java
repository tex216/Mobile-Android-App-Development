package edu.gatech.seclass.jobcompare6300;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class COMPARISON_SETTINGS_WEIGHT {
    @PrimaryKey
    @NonNull
    public String WEIGHT;

    @ColumnInfo(name = "WEIGHT_VALUE")
    public int WEIGHT_VALUE;

}

package edu.gatech.seclass.jobcompare6300.data;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class COMPARISON_SETTINGS_WEIGHT {
    @PrimaryKey
    @NonNull
    private String WEIGHT;

    @ColumnInfo(name = "WEIGHT_VALUE")
    private int WEIGHT_VALUE;

    @NonNull
    public String getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(@NonNull String WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public int getWEIGHT_VALUE() {
        return WEIGHT_VALUE;
    }

    public void setWEIGHT_VALUE(int WEIGHT_VALUE) {
        this.WEIGHT_VALUE = WEIGHT_VALUE;
    }
}

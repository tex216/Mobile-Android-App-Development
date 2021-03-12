package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdjustComparisonSettingsActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private EditText remoteWork;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirementBenefits;
    private EditText leaveTime;
    private final Context context = this;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_comparison_settings);

        appDatabase = AppDatabase.getInstance(context);
        save = (Button) findViewById(R.id.btn_save_weight);
        cancel = (Button) findViewById(R.id.btn_cancel_weight);

        retirementBenefits = (EditText)findViewById(R.id.text_retirement_benefits_weight);
        leaveTime = (EditText)findViewById(R.id.text_leave_time_weight);
        yearlySalary = (EditText)findViewById(R.id.text_salary_weight);
        remoteWork = (EditText)findViewById(R.id.text_remote_work_weight);
        yearlyBonus = (EditText)findViewById(R.id.text_bonus_weight);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelClick();
            }
        });

    }

    public void handleSaveClick() {
        ComparisonSettingsWeightDao comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            int remoteWorkPossibilityWeight = Integer.parseInt(remoteWork.getText().toString());
            int yearlySalaryWeight = Integer.parseInt(yearlySalary.getText().toString());
            int yearlyBonusWeight = Integer.parseInt(yearlyBonus.getText().toString());
            int retirementBenefitsWeight = Integer.parseInt(retirementBenefits.getText().toString());
            int leaveTimeWeight = Integer.parseInt(leaveTime.getText().toString());
            comparisonSettingsWeightDao.updateComparisonWeights(remoteWorkPossibilityWeight, yearlySalaryWeight, yearlyBonusWeight, retirementBenefitsWeight, leaveTimeWeight);
            handler.post(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        });
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
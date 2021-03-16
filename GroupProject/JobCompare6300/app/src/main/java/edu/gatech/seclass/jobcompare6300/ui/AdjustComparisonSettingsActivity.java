package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.HashMap;

import edu.gatech.seclass.jobcompare6300.core.COMPARISON_SETTINGS_OPTIONS;
import edu.gatech.seclass.jobcompare6300.core.System;
import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.COMPARISON_SETTINGS_WEIGHT;
import edu.gatech.seclass.jobcompare6300.data.ComparisonSettingsWeightDao;
import edu.gatech.seclass.jobcompare6300.R;

public class AdjustComparisonSettingsActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private EditText remoteWork;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirementBenefits;
    private EditText leaveTime;
    private final Context context = this;
    System system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adjust_comparison_settings);
        this.system = System.getInstance(this.context);

        save = (Button) findViewById(R.id.btn_save_weight);
        cancel = (Button) findViewById(R.id.btn_cancel_weight);

        retirementBenefits = (EditText)findViewById(R.id.text_retirement_benefits_weight);
        leaveTime = (EditText)findViewById(R.id.text_leave_time_weight);
        yearlySalary = (EditText)findViewById(R.id.text_salary_weight);
        remoteWork = (EditText)findViewById(R.id.text_remote_work_weight);
        yearlyBonus = (EditText)findViewById(R.id.text_bonus_weight);

        int remoteWorkPossibilityWeight = 0;
        int yearlySalaryWeight = 0;
        int yearlyBonusWeight = 0;
        int retirementBenefitsWeight = 0;
        int leaveTimeWeight = 0;

        try {
            remoteWorkPossibilityWeight = system.getWeight(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT);
            yearlySalaryWeight = system.getWeight(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT);
            yearlyBonusWeight = system.getWeight(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT);
            retirementBenefitsWeight = system.getWeight(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT);
            leaveTimeWeight = system.getWeight(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        retirementBenefits.setText(String.valueOf(retirementBenefitsWeight));
        leaveTime.setText(String.valueOf(leaveTimeWeight));
        yearlySalary.setText(String.valueOf(yearlySalaryWeight));
        remoteWork.setText(String.valueOf(remoteWorkPossibilityWeight));
        yearlyBonus.setText(String.valueOf(yearlyBonusWeight));

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
        if (this.checkForEmptyFields()) {
            return;
        }

        HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights = new HashMap<>();
        int remoteWorkPossibilityWeight = Integer.parseInt(remoteWork.getText().toString());
        weights.put(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT, remoteWorkPossibilityWeight);

        int yearlySalaryWeight = Integer.parseInt(yearlySalary.getText().toString());
        weights.put(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT, yearlySalaryWeight);

        int yearlyBonusWeight = Integer.parseInt(yearlyBonus.getText().toString());
        weights.put(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT, yearlyBonusWeight);

        int retirementBenefitsWeight = Integer.parseInt(retirementBenefits.getText().toString());
        weights.put(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT, retirementBenefitsWeight);

        int leaveTimeWeight = Integer.parseInt(leaveTime.getText().toString());
        weights.put(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT, leaveTimeWeight);

        if (this.checkForInvalidValues(weights)) {
            this.setInvalidValueErrorMessage();
            return;
        }

        this.system.updateComparisonWeights(weights);

        Toast.makeText(this,"Comparison settings successfully changed!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private boolean checkForEmptyFields() {
        boolean hasErrors = false;
        if(TextUtils.isEmpty(remoteWork.getText())){
            remoteWork.setError("Weight is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(yearlySalary.getText())){
            yearlySalary.setError("Weight is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(yearlyBonus.getText())){
            yearlyBonus.setError("Weight is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(retirementBenefits.getText())){
            retirementBenefits.setError("Weight is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(leaveTime.getText())){
            leaveTime.setError("Weight is required");
            hasErrors = true;
        }
        return hasErrors;
    }

    private boolean checkForInvalidValues(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights) {
        boolean hasErrors = false;
        int invalidEntries = 0;

        for (Map.Entry<COMPARISON_SETTINGS_OPTIONS,Integer> entry : weights.entrySet()) {
            if (entry.getValue() <= 0) {
                invalidEntries++;
            }
        }

        if (invalidEntries == weights.size()) {
            hasErrors = true;
        }
        return hasErrors;
    }

    private void setInvalidValueErrorMessage() {
        String errorMessage = "At least 1 weight must be positive";
        remoteWork.setError(errorMessage);
        yearlySalary.setError(errorMessage);
        yearlyBonus.setError(errorMessage);
        retirementBenefits.setError(errorMessage);
        leaveTime.setError(errorMessage);
    }
}
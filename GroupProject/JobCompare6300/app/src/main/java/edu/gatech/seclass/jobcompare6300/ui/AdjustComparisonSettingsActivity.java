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

public class AdjustComparisonSettingsActivity extends BaseActivity {
    private Button save;
    private Button cancel;
    private EditText remoteWork;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirementBenefits;
    private EditText leaveTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
        try {
            this.populateDefaultWeights();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_adjust_comparison_settings;
    }

    @Override
    protected void initializeUI() {
        this.save = (Button) findViewById(R.id.btn_save_weight);
        this.cancel = (Button) findViewById(R.id.btn_cancel_weight);
        this.retirementBenefits = (EditText)findViewById(R.id.text_retirement_benefits_weight);
        this.leaveTime = (EditText)findViewById(R.id.text_leave_time_weight);
        this.yearlySalary = (EditText)findViewById(R.id.text_salary_weight);
        this.remoteWork = (EditText)findViewById(R.id.text_remote_work_weight);
        this.yearlyBonus = (EditText)findViewById(R.id.text_bonus_weight);
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

    private void handleSaveClick() {
        if (this.checkForEmptyFields()) {
            return;
        }
        HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights = this.getEnteredInputs();
        if (this.checkForInvalidValues(weights)) {
            this.setInvalidValueErrorMessage();
            return;
        }
        this.system.updateComparisonWeights(weights);
        Toast.makeText(this,"Comparison settings successfully changed!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void populateDefaultWeights() throws Exception {
        int remoteWorkPossibilityWeight = this.system.getWeight(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT);
        int yearlySalaryWeight = this.system.getWeight(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT);
        int yearlyBonusWeight = this.system.getWeight(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT);
        int retirementBenefitsWeight = this.system.getWeight(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT);
        int leaveTimeWeight = this.system.getWeight(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT);
        this.retirementBenefits.setText(String.valueOf(retirementBenefitsWeight));
        this.leaveTime.setText(String.valueOf(leaveTimeWeight));
        this.yearlySalary.setText(String.valueOf(yearlySalaryWeight));
        this.remoteWork.setText(String.valueOf(remoteWorkPossibilityWeight));
        this.yearlyBonus.setText(String.valueOf(yearlyBonusWeight));
    }

    private HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> getEnteredInputs() {
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
        return weights;
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
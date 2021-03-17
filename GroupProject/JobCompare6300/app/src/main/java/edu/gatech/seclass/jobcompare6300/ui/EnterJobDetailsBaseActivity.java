package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;

public abstract class EnterJobDetailsBaseActivity extends BaseActivity {
    protected Button save;
    protected Button cancel;
    protected EditText title;
    protected EditText company;
    protected EditText city;
    protected EditText state;
    protected EditText costOfLiving;
    protected Spinner remoteWork;
    protected EditText yearlySalary;
    protected EditText yearlyBonus;
    protected EditText retirement;
    protected EditText leaveTime;

    protected String newTitle;
    protected String newCompany;
    protected String newCity;
    protected String newState;
    protected int newCostOfLiving;
    protected int newRemoteWork;
    protected double newYearlySalary;
    protected double newYearlyBonus;
    protected double newRetirement;
    protected int newLeaveTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected abstract int getLayoutResourceId();

    @Override
    protected void initializeUI() {
        this.save = (Button) findViewById(R.id.btn_save);
        this.cancel = (Button) findViewById(R.id.btn_cancel);
        this.title = (EditText)findViewById(R.id.text_title);
        this.company = (EditText)findViewById(R.id.text_company);
        this.city = (EditText)findViewById(R.id.text_city);
        this.state = (EditText)findViewById(R.id.text_state);
        this.costOfLiving = (EditText)findViewById(R.id.number_cost_of_living);
        this.remoteWork = (Spinner) findViewById(R.id.dropdown_remote_work);
        this.yearlySalary = (EditText)findViewById(R.id.number_salary);
        this.yearlyBonus = (EditText)findViewById(R.id.number_bonus);
        this.retirement = (EditText)findViewById(R.id.number_retirement);
        this.leaveTime = (EditText)findViewById(R.id.number_leave_time);

        this.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveClick();
            }
        });

        this.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelClick();
            }
        });
    }

    protected abstract void handleSaveClick();

    protected abstract void handleCancelClick();

    protected void getJobDetails() {
        this.newTitle = title.getText().toString();
        this.newCompany = company.getText().toString();
        this.newCity = city.getText().toString();
        this.newState = state.getText().toString();
        this.newCostOfLiving = Integer.parseInt(costOfLiving.getText().toString());
        this.newRemoteWork = Integer.parseInt(remoteWork.getSelectedItem().toString());
        this.newYearlySalary = Double.parseDouble(yearlySalary.getText().toString());
        this.newYearlyBonus = Double.parseDouble(yearlyBonus.getText().toString());
        this.newRetirement = Double.parseDouble(retirement.getText().toString());
        this.newLeaveTime = Integer.parseInt(leaveTime.getText().toString());
    }

    protected boolean checkForEmptyFields() {
        boolean hasErrors = false;
        if(TextUtils.isEmpty(title.getText())){
            title.setError("Title is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(company.getText())){
            company.setError("Company is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(city.getText())){
            city.setError("City is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(state.getText())){
            state.setError("State is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(costOfLiving.getText())){
            costOfLiving.setError("Cost of living is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(yearlySalary.getText())){
            yearlySalary.setError("Salary is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(yearlyBonus.getText())){
            yearlyBonus.setError("Bonus is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(retirement.getText())){
            retirement.setError("Retirement is required");
            hasErrors = true;
        }
        if(TextUtils.isEmpty(leaveTime.getText())){
            leaveTime.setError("Leave time is required");
            hasErrors = true;
        }
        return hasErrors;
    }
}
package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import edu.gatech.seclass.jobcompare6300.R;
import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
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
}
package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterCurrentJobActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private EditText title;
    private EditText company;
    private EditText city;
    private EditText state;
    private EditText costofLiveing;
    private EditText remoteWork;
    private EditText salary;
    private EditText yearlyBonus;
    private EditText retirement;
    private EditText leaveTime;
    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-jobcompare").allowMainThreadQueries().build();
        save = (Button) findViewById(R.id.btn_save_cj);
        cancel = (Button) findViewById(R.id.btn_cancel_cj);
        title = (EditText)findViewById(R.id.text_title);
        company = (EditText)findViewById(R.id.text_company);;
        city = (EditText)findViewById(R.id.text_city);;
        state = (EditText)findViewById(R.id.text_state);;
        costofLiveing = (EditText)findViewById(R.id.number_cost_of_living);;
//        remoteWork = (EditText)findViewById(R.id.dropdown_remote_work);;
        salary = (EditText)findViewById(R.id.number_salary);;
        yearlyBonus = (EditText)findViewById(R.id.number_bonus);;
        retirement = (EditText)findViewById(R.id.number_retirement);;
        leaveTime = (EditText)findViewById(R.id.number_leave_time);;


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
        JobDetailsDao jobDetailsDao = db.jobDetailsDao();
        JOB_DETAILS jobDetails = new JOB_DETAILS();
        jobDetails.TITLE = title.getText().toString();
        jobDetails.COMPANY = company.getText().toString();
        jobDetails.CITY = city.getText().toString();
        jobDetails.STATE = state.getText().toString();
        jobDetails.YEARLY_SALARY = Double.parseDouble(salary.getText().toString());
        jobDetails.YEARLY_BONUS = Double.parseDouble(yearlyBonus.getText().toString());
        jobDetails.COST_OF_LIVING_INDEX = 5;
        jobDetails.IS_CURRENT_JOB = false;
        jobDetails.LEAVE_TIME = Integer.parseInt(leaveTime.getText().toString());
        jobDetails.PERCENTAGE_MATCHED = Double.parseDouble(retirement.getText().toString());
        jobDetails.WORK_REMOTE = Integer.parseInt(remoteWork.getText().toString());
        jobDetails.SCORE = null;
        jobDetailsDao.insertJob(jobDetails);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
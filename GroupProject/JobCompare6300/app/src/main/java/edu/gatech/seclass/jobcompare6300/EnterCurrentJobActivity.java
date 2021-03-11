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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EnterCurrentJobActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private EditText title;
    private EditText company;
    private EditText city;
    private EditText state;
    private EditText costOfLiving;
    private Spinner remoteWork;
    private EditText salary;
    private EditText yearlyBonus;
    private EditText retirement;
    private EditText leaveTime;
    private final Context context = this;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job);
        appDatabase = AppDatabase.getInstance(context);
        save = (Button) findViewById(R.id.btn_save_cj);
        cancel = (Button) findViewById(R.id.btn_cancel_cj);
        title = (EditText)findViewById(R.id.text_title_cj);
        company = (EditText)findViewById(R.id.text_company_cj);
        city = (EditText)findViewById(R.id.text_city_cj);
        state = (EditText)findViewById(R.id.text_state_cj);
        costOfLiving = (EditText)findViewById(R.id.number_cost_of_living_cj);
        remoteWork = (Spinner) findViewById(R.id.dropdown_remote_work_cj);
        salary = (EditText)findViewById(R.id.number_salary_cj);
        yearlyBonus = (EditText)findViewById(R.id.number_bonus_cj);
        retirement = (EditText)findViewById(R.id.number_retirement_cj);
        leaveTime = (EditText)findViewById(R.id.number_leave_time_cj);

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
        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

//        executor.execute(() -> {
//            //Background work here
//            JOB_DETAILS jobDetails = new JOB_DETAILS();
//            jobDetails.TITLE = "Test Title";
//            jobDetails.COMPANY = "Test Company";
//            jobDetails.CITY = "Test City";
//            jobDetails.STATE = "Test State";
//            jobDetails.YEARLY_SALARY = 100000.00;
//            jobDetails.YEARLY_BONUS = 20000.00;
//            jobDetails.COST_OF_LIVING_INDEX = 5;
//            jobDetails.IS_CURRENT_JOB = false;
//            jobDetails.LEAVE_TIME = 3;
//            jobDetails.PERCENTAGE_MATCHED = 6.5;
//            jobDetails.WORK_REMOTE = 3;
//            jobDetails.SCORE = null;
//            jobDetailsDao.insertJob(jobDetails);
//            handler.post(() -> {
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
//            });
//        });
        executor.execute(() -> {
            JOB_DETAILS jobDetails = new JOB_DETAILS();
            jobDetails.TITLE = title.getText().toString();
            jobDetails.COMPANY = company.getText().toString();
            jobDetails.CITY = city.getText().toString();
            jobDetails.STATE = state.getText().toString();
            jobDetails.YEARLY_SALARY = Double.parseDouble(salary.getText().toString());
            jobDetails.YEARLY_BONUS = Double.parseDouble(yearlyBonus.getText().toString());
            jobDetails.COST_OF_LIVING_INDEX = Integer.parseInt(costOfLiving.getText().toString());
            jobDetails.IS_CURRENT_JOB = true;
            jobDetails.LEAVE_TIME = Integer.parseInt(leaveTime.getText().toString());
            jobDetails.PERCENTAGE_MATCHED = Double.parseDouble(retirement.getText().toString());
            jobDetails.WORK_REMOTE = Integer.parseInt(remoteWork.getSelectedItem().toString());
            jobDetails.SCORE = null;
            jobDetailsDao.insertJob(jobDetails);
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
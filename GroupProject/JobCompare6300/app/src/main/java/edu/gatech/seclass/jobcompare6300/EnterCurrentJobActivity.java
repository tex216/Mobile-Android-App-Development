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
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirement;
    private EditText leaveTime;
    private final Context context = this;
    private AppDatabase appDatabase;

    JobDetailsDao jobDetailsDao;
    private JOB_DETAILS currentJob;
    private ExecutorService executor;
    private Handler handler;

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
        yearlySalary = (EditText)findViewById(R.id.number_salary_cj);
        yearlyBonus = (EditText)findViewById(R.id.number_bonus_cj);
        retirement = (EditText)findViewById(R.id.number_retirement_cj);
        leaveTime = (EditText)findViewById(R.id.number_leave_time_cj);

        this.jobDetailsDao = this.appDatabase.jobDetailsDao();
        this.executor = Executors.newSingleThreadExecutor();
        this.handler = new Handler(Looper.getMainLooper());

        this.executor.execute(() -> {
            this.currentJob = jobDetailsDao.getCurrentJob();
        });

        if (this.currentJob!=null) { //pre-populate form if current job exists
            title.setText(this.currentJob.getTITLE());
            company.setText(this.currentJob.getCOMPANY());
            city.setText(this.currentJob.getCITY());
            state.setText(this.currentJob.getSTATE());
            costOfLiving.setText(String.valueOf(this.currentJob.getCOST_OF_LIVING_INDEX()));
            remoteWork.setSelection(this.currentJob.getWORK_REMOTE());
            yearlySalary.setText(String.valueOf(this.currentJob.getYEARLY_SALARY()));
            yearlyBonus.setText(String.valueOf(this.currentJob.getYEARLY_BONUS()));
            retirement.setText(String.valueOf(this.currentJob.getPERCENTAGE_MATCHED()));
            leaveTime.setText(String.valueOf(this.currentJob.getLEAVE_TIME()));
        }

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
        this.executor.execute(() -> {
            String newTitle = title.getText().toString();
            String newCompany = company.getText().toString();
            String newCity = city.getText().toString();
            String newState = state.getText().toString();
            int newCostOfLiving = Integer.parseInt(costOfLiving.getText().toString());
            int newRemoteWork = Integer.parseInt(remoteWork.getSelectedItem().toString());
            double newYearlySalary = Double.parseDouble(yearlySalary.getText().toString());
            double newYearlyBonus = Double.parseDouble(yearlyBonus.getText().toString());
            double newRetirement = Double.parseDouble(retirement.getText().toString());
            int newLeaveTime = Integer.parseInt(leaveTime.getText().toString());

            if (currentJob == null) { //no existing current job exists, add it
                JOB_DETAILS jobDetails = new JOB_DETAILS(
                        newTitle,
                        newCompany,
                        newCity,
                        newState,
                        newCostOfLiving,
                        newRemoteWork,
                        newYearlySalary,
                        newYearlyBonus,
                        newRetirement,
                        newLeaveTime,
                        true,
                        null);
                this.jobDetailsDao.insertJob(jobDetails);
            } else { //there is an existing current job, update details
                this.currentJob.setTITLE(newTitle);
                this.currentJob.setCOMPANY(newCompany);
                this.currentJob.setCITY(newCity);
                this.currentJob.setSTATE(newState);
                this.currentJob.setCOST_OF_LIVING_INDEX(newCostOfLiving);
                this.currentJob.setWORK_REMOTE(newRemoteWork);
                this.currentJob.setYEARLY_SALARY(newYearlySalary);
                this.currentJob.setYEARLY_BONUS(newYearlyBonus);
                this.currentJob.setPERCENTAGE_MATCHED(newRetirement);
                this.currentJob.setLEAVE_TIME(newLeaveTime);
                this.currentJob.setSCORE(null);
                this.jobDetailsDao.updateCurrentJob(this.currentJob);
            }
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
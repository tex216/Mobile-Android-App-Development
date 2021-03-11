package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button enterCurrentJob;
    private Button enterJobOffers;
    private Button adjustComparisonSettings;
    private Button compareJobs;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database-jobcompare").allowMainThreadQueries().build();

        enterCurrentJob = (Button) findViewById(R.id.btn_enter_current_job);
        enterJobOffers = (Button) findViewById(R.id.btn_enter_job_offers);
        adjustComparisonSettings = (Button) findViewById(R.id.btn_adjust_comp_settings);
        compareJobs = (Button) findViewById(R.id.btn_compare_job_offers);

        enterCurrentJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddCurrentJobClick();
            }
        });

        enterJobOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddJobOffersClick();
            }
        });

        adjustComparisonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleComparisonSettingsClick();
            }
        });

        compareJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleRankedListClick();
            }
        });

    }

    public void handleAddJobOffersClick() {
//        JobDetailsDao jobDetailsDao = db.jobDetailsDao();
//        JOB_DETAILS jobDetails = new JOB_DETAILS();
//        jobDetails.TITLE = "Test Title";
//        jobDetails.COMPANY = "Test Company";
//        jobDetails.CITY = "Test City";
//        jobDetails.STATE = "Test State";
//        jobDetails.YEARLY_SALARY = 100000.00;
//        jobDetails.YEARLY_BONUS = 20000.00;
//        jobDetails.COST_OF_LIVING_INDEX = 5;
//        jobDetails.IS_CURRENT_JOB = false;
//        jobDetails.LEAVE_TIME = 3;
//        jobDetails.PERCENTAGE_MATCHED = 6.5;
//        jobDetails.WORK_REMOTE = 3;
//        jobDetails.SCORE = null;
//        jobDetailsDao.insertJob(jobDetails);
        Intent intent = new Intent(this, EnterJobOffersActivity.class);
        startActivity(intent);
    }

    public void handleAddCurrentJobClick() {
        Intent intent = new Intent(this, EnterCurrentJobActivity.class);
        startActivity(intent);
    }

    public void handleComparisonSettingsClick() {
        Intent intent = new Intent(this, AdjustComparisonSettingsActivity.class);
        startActivity(intent);
    }

    public void handleRankedListClick() {
        Intent intent = new Intent(this, RankedListActivity.class);
        startActivity(intent);
    }
}
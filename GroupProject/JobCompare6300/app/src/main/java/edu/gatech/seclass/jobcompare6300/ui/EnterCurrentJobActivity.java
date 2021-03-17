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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;
import edu.gatech.seclass.jobcompare6300.R;

public class EnterCurrentJobActivity extends EnterJobDetailsBaseActivity {

    private JOB_DETAILS currentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
        try {
            this.currentJob = this.system.getCurrentJob();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.currentJob!=null) { //pre-populate form if current job exists
            this.prepopulateCurrentJob();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_enter_job_offers;
    }

    @Override
    protected void initializeUI() {
        super.initializeUI();
        TextView jobOffersHeader = (TextView) findViewById(R.id.label_job_offers_header);
        jobOffersHeader.setText("Enter current job");
    }

    @Override
    protected void handleSaveClick() {
        if (this.checkForEmptyFields()) {
            return;
        }
        this.getJobDetails();
        Toast.makeText(this,"Current job successfully saved!", Toast.LENGTH_SHORT).show();

        this.addOrUpdateCurrentJob();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void prepopulateCurrentJob() {
        this.title.setText(this.currentJob.getTITLE());
        this.company.setText(this.currentJob.getCOMPANY());
        this.city.setText(this.currentJob.getCITY());
        this.state.setText(this.currentJob.getSTATE());
        this.costOfLiving.setText(String.valueOf(this.currentJob.getCOST_OF_LIVING_INDEX()));
        this.remoteWork.setSelection(this.currentJob.getWORK_REMOTE()-1);
        this.yearlySalary.setText(String.valueOf(this.currentJob.getYEARLY_SALARY()));
        this.yearlyBonus.setText(String.valueOf(this.currentJob.getYEARLY_BONUS()));
        this.retirement.setText(String.valueOf(this.currentJob.getPERCENTAGE_MATCHED()));
        this.leaveTime.setText(String.valueOf(this.currentJob.getLEAVE_TIME()));
    }

    private void addOrUpdateCurrentJob() {
        if (this.currentJob == null) { //no existing current job exists, add it
            this.system.addJob(
                    this.newTitle, this.newCompany, this.newCity, this.newState,
                    this.newCostOfLiving, this.newRemoteWork, this.newYearlySalary, this.newYearlyBonus,
                    this.newRetirement, this.newLeaveTime, true);
        } else { //there is an existing current job, update details
            this.updateCurrentJob();
            this.system.updateJob(this.currentJob);
        }
    }

    private void updateCurrentJob() {
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
    }

}
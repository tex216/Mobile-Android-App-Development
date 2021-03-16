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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;
import edu.gatech.seclass.jobcompare6300.R;

public class EnterCurrentJobActivity extends EnterJobDetailsBaseActivity {

    private final Context context = this;

    private AppDatabase appDatabase = AppDatabase.getInstance(context);
    private JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
    private JOB_DETAILS currentJob;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();

        this.executor.execute(() -> {
            this.currentJob = jobDetailsDao.getCurrentJob();
            handler.post(() -> {
                if (this.currentJob!=null) { //pre-populate form if current job exists
                    this.prepopulateCurrentJob();
                }
            });
        });
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
        Toast.makeText(this,"Current Job is Saved!", Toast.LENGTH_SHORT).show();
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

            if (this.currentJob == null) { //no existing current job exists, add it
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

    private boolean checkForEmptyFields() {
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
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;
import edu.gatech.seclass.jobcompare6300.R;

public class EnterJobOffersActivity extends EnterJobDetailsBaseActivity {
    private final Context context = this;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
        this.appDatabase = AppDatabase.getInstance(context);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_enter_job_offers;
    }

    @Override
    protected void handleSaveClick() {
        if (this.checkForEmptyFields()) {
            return;
        } else {
            JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                JOB_DETAILS jobDetails = new JOB_DETAILS
                        (
                                title.getText().toString(),
                                company.getText().toString(),
                                city.getText().toString(),
                                state.getText().toString(),
                                Integer.parseInt(costOfLiving.getText().toString()),Integer.parseInt(remoteWork.getSelectedItem().toString()),
                                Double.parseDouble(yearlySalary.getText().toString()), Double.parseDouble(yearlyBonus.getText().toString()),
                                Double.parseDouble(retirement.getText().toString()),Integer.parseInt(leaveTime.getText().toString()),
                                false,
                                null
                        );
                jobDetailsDao.insertJob(jobDetails);
                handler.post(() -> {
                    Intent intent = new Intent(this, AfterEnterJobOfferActivity.class);
                    intent.putExtra("new_job", jobDetails);
                    startActivity(intent);
                });
            });
        }
    }

    @Override
    protected void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
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
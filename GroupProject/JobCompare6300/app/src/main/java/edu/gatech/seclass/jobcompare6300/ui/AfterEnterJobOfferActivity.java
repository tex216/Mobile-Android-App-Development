package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;
import edu.gatech.seclass.jobcompare6300.R;

public class AfterEnterJobOfferActivity extends BaseActivity {
    private Button addAnotherOffer;
    private Button returnMainMenu;
    private Button compareCurrentOffer;

    private final Context context = this;
    private AppDatabase appDatabase = AppDatabase.getInstance(context);
    private JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    JOB_DETAILS currentJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
        executor.execute(() -> {
            this.currentJob = this.jobDetailsDao.getCurrentJob();
            handler.post(() -> {
                if (currentJob == null) {
                    compareCurrentOffer.setEnabled(false);
                    compareCurrentOffer.setClickable(false);
                }
            });
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_after_enter_job_offer;
    }

    @Override
    protected void initializeUI() {
        this.addAnotherOffer = (Button) findViewById(R.id.btn_add_another_job);
        this.returnMainMenu = (Button) findViewById(R.id.btn_return_after_job);
        this.compareCurrentOffer = (Button) findViewById(R.id.btn_compare_current_offer);
        addAnotherOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddAnotherOfferClick();
            }
        });
        returnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReturnMainMenuClick();
            }
        });
        compareCurrentOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCompareCurrentOfferClick();
            }
        });
    }

    public void handleReturnMainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleAddAnotherOfferClick() {
        Intent intent = new Intent(this, EnterJobOffersActivity.class);
        startActivity(intent);
    }

    public void handleCompareCurrentOfferClick() {
        JOB_DETAILS newJobOffer = (JOB_DETAILS) getIntent().getSerializableExtra("new_job");
        Intent intent = new Intent(this, CompareJobsActivity.class);
        List<JOB_DETAILS> jobsToCompare = new ArrayList<>();
        jobsToCompare.add(this.currentJob);
        jobsToCompare.add(newJobOffer);
        intent.putExtra("selected_jobs", (ArrayList<JOB_DETAILS>)jobsToCompare);
        startActivity(intent);
    }
}
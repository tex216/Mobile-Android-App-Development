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

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Button enterCurrentJob;
    private Button enterJobOffers;
    private Button adjustComparisonSettings;
    private Button compareJobs;
    private final Context context = this;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = AppDatabase.getInstance(context, true);
        ComparisonSettingsWeightDao comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        enterCurrentJob = (Button) findViewById(R.id.btn_enter_current_job);
        enterJobOffers = (Button) findViewById(R.id.btn_enter_job_offers);
        adjustComparisonSettings = (Button) findViewById(R.id.btn_adjust_comp_settings);
        compareJobs = (Button) findViewById(R.id.btn_compare_job_offers);

        executor.execute(() -> {
            if (comparisonSettingsWeightDao.getAllWeights().size() == 0) {
                comparisonSettingsWeightDao.setDefaultWeight();
            };
            int numberOfJobs = jobDetailsDao.getAllJobs().size();

            handler.post(() -> {
                if (numberOfJobs < 2) {
                    compareJobs.setEnabled(false);
                    compareJobs.setClickable(false);
                }
            });
        });

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
package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.gatech.seclass.jobcompare6300.core.System;
import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.ComparisonSettingsWeightDao;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;
import edu.gatech.seclass.jobcompare6300.R;

public class MainActivity extends AppCompatActivity {

    private Button enterCurrentJob;
    private Button enterJobOffers;
    private Button adjustComparisonSettings;
    private Button compareJobs;
    private final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System system = System.getInstance(this.context);

        enterCurrentJob = (Button) findViewById(R.id.btn_enter_current_job);
        enterJobOffers = (Button) findViewById(R.id.btn_enter_job_offers);
        adjustComparisonSettings = (Button) findViewById(R.id.btn_adjust_comp_settings);
        compareJobs = (Button) findViewById(R.id.btn_compare_job_offers);

        system.initialize();
        int numberOfJobs = 0;
        try {
            numberOfJobs = system.getNumberOfJobs();
            if (numberOfJobs < 2) {
                compareJobs.setEnabled(false);
                compareJobs.setClickable(false);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
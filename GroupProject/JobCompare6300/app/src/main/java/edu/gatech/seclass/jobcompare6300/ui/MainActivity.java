package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.concurrent.ExecutionException;
import edu.gatech.seclass.jobcompare6300.R;

public class MainActivity extends BaseActivity {
    private Button enterCurrentJob;
    private Button enterJobOffers;
    private Button adjustComparisonSettings;
    private Button compareJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
        this.system.initialize();
        try {
            this.shouldDisableCompareButton();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initializeUI() {
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

    private void handleAddJobOffersClick() {
        Intent intent = new Intent(this, EnterJobOffersActivity.class);
        startActivity(intent);
    }

    private void handleAddCurrentJobClick() {
        Intent intent = new Intent(this, EnterCurrentJobActivity.class);
        startActivity(intent);
    }

    private void handleComparisonSettingsClick() {
        Intent intent = new Intent(this, AdjustComparisonSettingsActivity.class);
        startActivity(intent);
    }

    private void handleRankedListClick() {
        Intent intent = new Intent(this, RankedListActivity.class);
        startActivity(intent);
    }

    private void shouldDisableCompareButton() throws ExecutionException, InterruptedException {
        int numberOfJobs = this.system.getNumberOfJobs();
        if (numberOfJobs < 2) {
            compareJobs.setEnabled(false);
            compareJobs.setClickable(false);
        }
    }
}
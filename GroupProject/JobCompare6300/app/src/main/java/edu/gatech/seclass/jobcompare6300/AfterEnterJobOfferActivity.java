package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterEnterJobOfferActivity extends AppCompatActivity {
    private Button addAnotherOffer;
    private Button returnMainMenu;
    private Button compareCurrentOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_enter_job_offer);

        addAnotherOffer = (Button) findViewById(R.id.btn_add_another_job);
        returnMainMenu = (Button) findViewById(R.id.btn_return_after_job);
        compareCurrentOffer = (Button) findViewById(R.id.btn_compare_current_offer);

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
        Intent intent = new Intent(this, CompareJobsActivity.class);
        startActivity(intent);
    }

}
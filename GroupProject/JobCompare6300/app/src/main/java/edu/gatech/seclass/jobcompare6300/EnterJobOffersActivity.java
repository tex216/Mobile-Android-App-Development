package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnterJobOffersActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private Button addMoreOffers;
    private Button compareCurrentOffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offers);

        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);

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
        Intent intent = new Intent(this, AfterEnterJobOfferActivity.class);
        startActivity(intent);
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
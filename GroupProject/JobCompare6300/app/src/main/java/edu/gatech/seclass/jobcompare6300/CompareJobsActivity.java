package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CompareJobsActivity extends AppCompatActivity {
    private Button returntoMainMenu;
    private Button anotherCompare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);

        returntoMainMenu = (Button) findViewById(R.id.btn_return_compare);
        anotherCompare = (Button) findViewById(R.id.btn_another_compare);

        returntoMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleReturnMainMenuClick();
            }
        });

        anotherCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAnotherComparisonClick();
            }
        });

    }
    public void handleReturnMainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleAnotherComparisonClick() {
        Intent intent = new Intent(this, RankedListActivity.class);
        startActivity(intent);
    }
}
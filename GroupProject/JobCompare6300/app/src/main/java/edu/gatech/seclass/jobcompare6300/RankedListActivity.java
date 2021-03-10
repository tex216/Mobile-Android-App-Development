package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RankedListActivity extends AppCompatActivity {

    private Button compare;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_list);

        compare = (Button) findViewById(R.id.btn_compare_ranked_list);
        cancel = (Button) findViewById(R.id.btn_return_ranked_list);

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCompareJobsClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelClick();
            }
        });
    }

    public void handleCompareJobsClick() {
        Intent intent = new Intent(this, CompareJobsActivity.class);
        startActivity(intent);
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
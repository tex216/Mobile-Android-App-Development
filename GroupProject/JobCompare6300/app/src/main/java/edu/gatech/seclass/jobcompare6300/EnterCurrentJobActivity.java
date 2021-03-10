package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnterCurrentJobActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job);

        save = (Button) findViewById(R.id.btn_save_cj);
        cancel = (Button) findViewById(R.id.btn_cancel_cj);

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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
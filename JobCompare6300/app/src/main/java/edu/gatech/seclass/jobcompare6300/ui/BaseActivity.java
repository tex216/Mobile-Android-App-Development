package edu.gatech.seclass.jobcompare6300.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import edu.gatech.seclass.jobcompare6300.core.System;

public abstract class BaseActivity extends AppCompatActivity {
    protected System system;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        this.system = System.getInstance(getApplicationContext());
    }

    protected abstract int getLayoutResourceId();
    protected abstract void initializeUI();
}
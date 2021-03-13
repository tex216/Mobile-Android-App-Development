package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EnterJobOffersActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;

    private EditText title;
    private EditText company;
    private EditText city;
    private EditText state;
    private EditText costOfLiving;
    private Spinner remoteWork;
    private EditText salary;
    private EditText yearlyBonus;
    private EditText retirement;
    private EditText leaveTime;
    private final Context context = this;
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_job_offers);

        save = (Button) findViewById(R.id.btn_save);
        cancel = (Button) findViewById(R.id.btn_cancel);
        appDatabase = AppDatabase.getInstance(context);

        title = (EditText)findViewById(R.id.text_title);
        company = (EditText)findViewById(R.id.text_company);
        city = (EditText)findViewById(R.id.text_city);
        state = (EditText)findViewById(R.id.text_state);
        costOfLiving = (EditText)findViewById(R.id.number_cost_of_living);
        remoteWork = (Spinner) findViewById(R.id.dropdown_remote_work);
        salary = (EditText)findViewById(R.id.number_salary);
        yearlyBonus = (EditText)findViewById(R.id.number_bonus);
        retirement = (EditText)findViewById(R.id.number_retirement);
        leaveTime = (EditText)findViewById(R.id.number_leave_time);

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
        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            JOB_DETAILS jobDetails = new JOB_DETAILS
                (
                    title.getText().toString(),
                    company.getText().toString(),
                    city.getText().toString(),
                    state.getText().toString(),
                    Integer.parseInt(costOfLiving.getText().toString()),Integer.parseInt(remoteWork.getSelectedItem().toString()),
                    Double.parseDouble(salary.getText().toString()), Double.parseDouble(yearlyBonus.getText().toString()),
                    Double.parseDouble(retirement.getText().toString()),Integer.parseInt(leaveTime.getText().toString()),
                    false,
                    null
                );
            jobDetailsDao.insertJob(jobDetails);
            handler.post(() -> {
                Intent intent = new Intent(this, AfterEnterJobOfferActivity.class);
                intent.putExtra("new_job", jobDetails);
                startActivity(intent);
            });
        });
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
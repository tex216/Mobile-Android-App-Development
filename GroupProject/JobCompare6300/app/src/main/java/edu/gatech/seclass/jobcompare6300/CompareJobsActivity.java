package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompareJobsActivity extends AppCompatActivity {
    private Button returnToMainMenu;
    private Button anotherCompare;
    private TextView title_1;
    private TextView title_2;
    private TextView company_1;
    private TextView company_2;
    private TextView location_1;
    private TextView location_2;
    private TextView yearlySalary_1;
    private TextView yearlySalary_2;
    private TextView yearlyBonus_1;
    private TextView yearlyBonus_2;
    private TextView retirementBenefits_1;
    private TextView retirementBenefits_2;
    private TextView leaveTime_1;
    private TextView leaveTime_2;
    private final Context context = this;
    private AppDatabase appDatabase;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_jobs);
        appDatabase = AppDatabase.getInstance(context);

        returnToMainMenu = (Button) findViewById(R.id.btn_return_compare);
        anotherCompare = (Button) findViewById(R.id.btn_another_compare);
        title_1 = (TextView)findViewById(R.id.value_title_1);
        title_2 = (TextView)findViewById(R.id.value_title_2);
        company_1 = (TextView)findViewById(R.id.value_company_1);
        company_2 = (TextView)findViewById(R.id.value_company_2);
        location_1 = (TextView)findViewById(R.id.value_location_1);
        location_2 = (TextView)findViewById(R.id.value_location_2);
        yearlySalary_1 = (TextView)findViewById(R.id.value_salary_1);
        yearlySalary_2 = (TextView)findViewById(R.id.value_salary_2);
        yearlyBonus_1 = (TextView)findViewById(R.id.value_bonus_1);
        yearlyBonus_2 = (TextView)findViewById(R.id.value_bonus_2);
        retirementBenefits_1 = (TextView)findViewById(R.id.value_retirement_1);
        retirementBenefits_2 = (TextView)findViewById(R.id.value_retirement_2);
        leaveTime_1 = (TextView)findViewById(R.id.value_leave_time_1);
        leaveTime_2 = (TextView)findViewById(R.id.value_leave_time_2);

        returnToMainMenu.setOnClickListener(new View.OnClickListener() {
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

        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        int first_job_id = 10;
        int second_job_id = 20;

        List<JOB_DETAILS> selectedJobs = (List<JOB_DETAILS>)getIntent().getSerializableExtra("selected_jobs");

//        executor.execute(() -> {
//            this.selectJobs = jobDetailsDao.getSelectedJobs(first_job_id, second_job_id);
//        });

        String Title_1 = selectedJobs.get(0).getTITLE();
        title_1.setText(Title_1);
        String Title_2 = selectedJobs.get(1).getTITLE();
        title_2.setText(Title_2);

        String Company_1 = selectedJobs.get(0).getCOMPANY();
        company_1.setText(Company_1);
        String Company_2 = selectedJobs.get(1).getCOMPANY();
        company_2.setText(Company_2);

        String Location_1 = selectedJobs.get(0).getCITY() + ", " + selectedJobs.get(0).getSTATE();
        location_1.setText(Location_1);
        String Location_2 = selectedJobs.get(1).getCITY() + ", " + selectedJobs.get(1).getSTATE();
        location_2.setText(Location_2);

        String YearlySalary_1 = String.valueOf(selectedJobs.get(0).getYEARLY_SALARY());
        yearlySalary_1.setText(YearlySalary_1);
        String YearlySalary_2 = String.valueOf(selectedJobs.get(1).getYEARLY_SALARY());
        yearlySalary_2.setText(YearlySalary_2);

        String YearlyBonus_1 = String.valueOf(selectedJobs.get(0).getYEARLY_BONUS());
        yearlyBonus_1.setText(YearlyBonus_1);
        String YearlyBonus_2 = String.valueOf(selectedJobs.get(1).getYEARLY_BONUS());
        yearlyBonus_2.setText(YearlyBonus_2);

        String RetirementBenefits_1 = String.valueOf(selectedJobs.get(0).getPERCENTAGE_MATCHED());
        retirementBenefits_1.setText(RetirementBenefits_1);
        String RetirementBenefits_2 = String.valueOf(selectedJobs.get(1).getPERCENTAGE_MATCHED());
        retirementBenefits_2.setText(RetirementBenefits_2);

        String LeaveTime_1 = String.valueOf(selectedJobs.get(0).getLEAVE_TIME());
        leaveTime_1.setText(LeaveTime_1);
        String LeaveTime_2 = String.valueOf(selectedJobs.get(1).getLEAVE_TIME());
        leaveTime_2.setText(LeaveTime_2);

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
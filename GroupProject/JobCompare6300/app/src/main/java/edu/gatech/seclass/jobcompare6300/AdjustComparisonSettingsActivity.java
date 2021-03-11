package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AdjustComparisonSettingsActivity extends AppCompatActivity {
    private Button save;
    private Button cancel;
    private EditText remoteWork;
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText retirementBenefits;
    private EditText leaveTime;
    private final Context context = this;
    private AppDatabase appDatabase;
    private List<COMPARISON_SETTINGS_WEIGHT> comparison_settings_weights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_current_job);
        appDatabase = AppDatabase.getInstance(context);
        save = (Button) findViewById(R.id.btn_save_cj);
        cancel = (Button) findViewById(R.id.btn_cancel_cj);

        retirementBenefits = (EditText)findViewById(R.id.text_retirement_benefits_weight);
        leaveTime = (EditText)findViewById(R.id.text_leave_time_weight);
        yearlySalary = (EditText)findViewById(R.id.text_salary_weight);
        remoteWork = (EditText)findViewById(R.id.text_remote_work_weight);
        yearlyBonus = (EditText)findViewById(R.id.text_bonus_weight);

        appDatabase = AppDatabase.getInstance(context);

        save = (Button) findViewById(R.id.btn_save_weight);
        cancel = (Button) findViewById(R.id.btn_cancel_weight);

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
        ComparisonSettingsWeightDao comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            int remoteWorkPossibilityWeight = Integer.parseInt(remoteWork.getText().toString());
            int yearlySalaryWeight = Integer.parseInt(yearlySalary.getText().toString());
            int yearlyBonusWeight = Integer.parseInt(yearlyBonus.getText().toString());
            int retirementBenefitsWeight = Integer.parseInt(retirementBenefits.getText().toString());
            int leaveTimeWeight = Integer.parseInt(leaveTime.getText().toString());
            comparisonSettingsWeightDao.updateComparisonWeights(remoteWorkPossibilityWeight, yearlySalaryWeight, yearlyBonusWeight, retirementBenefitsWeight, leaveTimeWeight);
            handler.post(() -> {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
        });
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        this.calculateJobScores();
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void calculateJobScores() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        this.getAllWeights();
        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        List<JOB_DETAILS> allJobs = jobDetailsDao.getAllJobs();

        int denominator = this.calculateWeightsDenominator();

        HashMap<String, Integer> map = this.convertListToHashmap();
        for (int i = 0; i < allJobs.size(); i++) {
            JOB_DETAILS job = allJobs.get(i);
            double remoteWorkPossibilityWeight = (double)map.get("REMOTE_WORK_POSSIBILITY_WEIGHT");
            double yearlySalaryWeight = (double)map.get("YEARLY_SALARY_WEIGHT");
            double yearlyBonusWeight = (double)map.get("YEARLY_BONUS_WEIGHT");
            double retirementBenefitsWeight = (double)map.get("RETIREMENT_BENEFITS_WEIGHT");
            double leaveTimeWeight = (double)map.get("LEAVE_TIME_WEIGHT");

            double ays = job.getYEARLY_SALARY()/((double)job.getCOST_OF_LIVING_INDEX());
            double ayb = job.getYEARLY_BONUS()/((double)job.getCOST_OF_LIVING_INDEX());
            double rbp = (job.getPERCENTAGE_MATCHED()/100.0)*ays;
            double lt = (double)job.getLEAVE_TIME();
            double rwt = (double)job.getWORK_REMOTE();

            double firstTerm = yearlySalaryWeight/denominator * ays;
            double secondTerm = yearlyBonusWeight/denominator * ayb;
            double thirdTerm = retirementBenefitsWeight/denominator * (rbp * ays);
            double fourthTerm = leaveTimeWeight/denominator * (lt * ays/260.0);
            double fifthTerm = remoteWorkPossibilityWeight/denominator * ((260.0 - 52.0 * rwt) * (ays/260.0) / 8.0);

            double newScore = firstTerm + secondTerm + thirdTerm + fourthTerm - fifthTerm;
            jobDetailsDao.setScore(allJobs.get(i).getJOB_ID(), newScore);
        }
    }

    private int calculateWeightsDenominator() {
        int totalWeights = 0;
        for (COMPARISON_SETTINGS_WEIGHT i : comparison_settings_weights) {
            totalWeights = totalWeights + i.WEIGHT_VALUE;
        }
        return totalWeights;
    }

    private void getAllWeights() {
        ComparisonSettingsWeightDao comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        this.comparison_settings_weights = comparisonSettingsWeightDao.getAllWeights();
    }

    private HashMap<String, Integer> convertListToHashmap() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (COMPARISON_SETTINGS_WEIGHT i : this.comparison_settings_weights) map.put(i.WEIGHT, i.WEIGHT_VALUE);
        return map;
    }
}
package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.R;

public class CompareJobsActivity extends BaseActivity {
    private Button returnToMainMenu;
    private Button anotherCompare;
    private TextView title_1;
    private TextView title_2;
    private TextView company_1;
    private TextView company_2;
    private TextView location_1;
    private TextView location_2;
    private TextView adjYearlySalary_1;
    private TextView adjYearlySalary_2;
    private TextView adjYearlyBonus_1;
    private TextView adjYearlyBonus_2;
    private TextView retirementBenefits_1;
    private TextView retirementBenefits_2;
    private TextView leaveTime_1;
    private TextView leaveTime_2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();

        List<JOB_DETAILS> selectedJobs = (List<JOB_DETAILS>)getIntent().getSerializableExtra("selected_jobs");

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

        String AdjYearlySalary_1 = String.valueOf(selectedJobs.get(0).getYEARLY_SALARY() / selectedJobs.get(0).getCOST_OF_LIVING_INDEX() * 100);
        adjYearlySalary_1.setText(AdjYearlySalary_1);
        String AdjYearlySalary_2 = String.valueOf(selectedJobs.get(1).getYEARLY_SALARY() / selectedJobs.get(1).getCOST_OF_LIVING_INDEX() * 100);
        adjYearlySalary_2.setText(AdjYearlySalary_2);

        String AdjYearlyBonus_1 = String.valueOf(selectedJobs.get(0).getYEARLY_BONUS() / selectedJobs.get(0).getCOST_OF_LIVING_INDEX() * 100);
        adjYearlyBonus_1.setText(AdjYearlyBonus_1);
        String AdjYearlyBonus_2 = String.valueOf(selectedJobs.get(1).getYEARLY_BONUS() / selectedJobs.get(1).getCOST_OF_LIVING_INDEX() * 100);
        adjYearlyBonus_2.setText(AdjYearlyBonus_2);

        String RetirementBenefits_1 = String.valueOf(selectedJobs.get(0).getPERCENTAGE_MATCHED());
        retirementBenefits_1.setText(RetirementBenefits_1);
        String RetirementBenefits_2 = String.valueOf(selectedJobs.get(1).getPERCENTAGE_MATCHED());
        retirementBenefits_2.setText(RetirementBenefits_2);

        String LeaveTime_1 = String.valueOf(selectedJobs.get(0).getLEAVE_TIME());
        leaveTime_1.setText(LeaveTime_1);
        String LeaveTime_2 = String.valueOf(selectedJobs.get(1).getLEAVE_TIME());
        leaveTime_2.setText(LeaveTime_2);

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_compare_jobs;
    }

    @Override
    protected void initializeUI() {
        returnToMainMenu = (Button) findViewById(R.id.btn_return_compare);
        anotherCompare = (Button) findViewById(R.id.btn_another_compare);
        title_1 = (TextView)findViewById(R.id.value_title_1);
        title_2 = (TextView)findViewById(R.id.value_title_2);
        company_1 = (TextView)findViewById(R.id.value_company_1);
        company_2 = (TextView)findViewById(R.id.value_company_2);
        location_1 = (TextView)findViewById(R.id.value_location_1);
        location_2 = (TextView)findViewById(R.id.value_location_2);
        adjYearlySalary_1 = (TextView)findViewById(R.id.value_adj_salary_1);
        adjYearlySalary_2 = (TextView)findViewById(R.id.value_adj_salary_2);
        adjYearlyBonus_1 = (TextView)findViewById(R.id.value_adj_bonus_1);
        adjYearlyBonus_2 = (TextView)findViewById(R.id.value_adj_bonus_2);
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
    }

    private void handleReturnMainMenuClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void handleAnotherComparisonClick() {
        Intent intent = new Intent(this, RankedListActivity.class);
        startActivity(intent);
    }
}
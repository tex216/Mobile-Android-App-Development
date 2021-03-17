package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.R;

public class EnterJobOffersActivity extends EnterJobDetailsBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_enter_job_offers;
    }

    @Override
    protected void handleSaveClick() {
        if (this.checkForEmptyFields()) {
            return;
        }
        this.getJobDetails();
        JOB_DETAILS jobDetails = this.system.addJob(
                this.newTitle, this.newCompany, this.newCity, this.newState,
                this.newCostOfLiving, this.newRemoteWork, this.newYearlySalary, this.newYearlyBonus,
                this.newRetirement, this.newLeaveTime, false);

        Intent intent = new Intent(this, AfterEnterJobOfferActivity.class);
        intent.putExtra("new_job", jobDetails);
        startActivity(intent);
    }

    @Override
    protected void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
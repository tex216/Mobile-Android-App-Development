package edu.gatech.seclass.jobcompare6300.core;

import android.content.Context;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;

public class System {
    private static System system_instance = null;
    private ComparisonSettings comparisonSettings;
    private Job job;

    private System(Context context)
    {
        this.comparisonSettings = new ComparisonSettings(context);
        this.job = new Job(context);
    }

    public static System getInstance(Context context)
    {
        if (system_instance == null)
            system_instance = new System(context);
        return system_instance;
    }

    public void initialize() {
        this.comparisonSettings.setDefaultWeight();
    }

    public int getNumberOfJobs() throws ExecutionException, InterruptedException {
        return this.job.getNumberOfJobs();
    }

    public List<JOB_DETAILS> getAllJobs() throws ExecutionException, InterruptedException {
        return this.job.getAllJobs();
    }

    public JOB_DETAILS getCurrentJob() throws ExecutionException, InterruptedException {
        return this.job.getCurrentJob();
    }

    public JOB_DETAILS addJob(
            String newTitle, String newCompany, String newCity, String newState,
            int newCostOfLiving, int newRemoteWork, double newYearlySalary, double newYearlyBonus,
            double newRetirement, int newLeaveTime, boolean isCurrentJob
    ) {
        JOB_DETAILS jobDetails = new JOB_DETAILS(
                newTitle,
                newCompany,
                newCity,
                newState,
                newCostOfLiving,
                newRemoteWork,
                newYearlySalary,
                newYearlyBonus,
                newRetirement,
                newLeaveTime,
                isCurrentJob,
                null);
        this.job.addJob(jobDetails);
        return jobDetails;
    }

    public void updateJob(JOB_DETAILS currentJob) {
        this.job.updateJob(currentJob);
    }

    public void calculateScore() throws Exception {
        List<JOB_DETAILS> allJobs = this.getAllJobs();
        HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map = this.comparisonSettings.getAllWeights();
        this.job.calculateScore(allJobs, map);
    }

    public int getWeight(COMPARISON_SETTINGS_OPTIONS option) throws Exception {
        this.comparisonSettings.getAllWeights();
        int weight;
        switch(option) {
            case REMOTE_WORK_POSSIBILITY_WEIGHT:
                weight = this.comparisonSettings.getRemoteWorkPossibilityWeight();
                break;
            case YEARLY_SALARY_WEIGHT:
                weight = this.comparisonSettings.getYearlySalaryWeight();
                break;
            case YEARLY_BONUS_WEIGHT:
                weight = this.comparisonSettings.getYearlyBonusWeight();
                break;
            case RETIREMENT_BENEFITS_WEIGHT:
                weight = this.comparisonSettings.getRetirementBenefitsWeight();
                break;
            case LEAVE_TIME_WEIGHT:
                weight = this.comparisonSettings.getLeaveTimeWeight();
                break;
            default:
                throw new Exception("Invalid comparison setting option");
        }
        return weight;
    }

    public void updateComparisonWeights(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights) {
        this.comparisonSettings.updateComparisonWeights(weights);
    }
}

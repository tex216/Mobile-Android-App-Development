package edu.gatech.seclass.jobcompare6300.core;

import android.content.Context;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.ComparisonSettingsWeightDao;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;

public class System {
    private static System system_instance = null;

    private AppDatabase appDatabase;
    private ComparisonSettingsWeightDao comparisonSettingsWeightDao;
    private JobDetailsDao jobDetailsDao;
    private ExecutorService executor;

    private ComparisonSettings comparisonSettings;
    private Job job;

    private System(Context context)
    {
        this.appDatabase = AppDatabase.getInstance(context, false);
        this.comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        this.jobDetailsDao = this.appDatabase.jobDetailsDao();
        this.executor = Executors.newSingleThreadExecutor();
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
        this.executor.execute(() -> {
            if (this.comparisonSettingsWeightDao.getAllWeights().size() == 0) {
                this.comparisonSettingsWeightDao.setDefaultWeight();
            };
        });
    }

    public int getNumberOfJobs() throws ExecutionException, InterruptedException {
        Future obj = this.executor.submit((Callable) () -> jobDetailsDao.getAllJobs().size());
        Integer n = (Integer) obj.get();
        return n;
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

    public void calculateScore() throws Exception {
        List<JOB_DETAILS> allJobs = this.getAllJobs();
        HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map = this.comparisonSettings.getAllWeights();
        this.job.calculateScore(allJobs, map);
    }

    public void updateComparisonWeights(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights) {
        this.comparisonSettings.updateComparisonWeights(weights);
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
        executor.execute(() -> {
            this.job.addJob(jobDetails);
        });
        return jobDetails;
    }

    public void updateJob(JOB_DETAILS currentJob) {
        this.job.updateJob(currentJob);
    }
}

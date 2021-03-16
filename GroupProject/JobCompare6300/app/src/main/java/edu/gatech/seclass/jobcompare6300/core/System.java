package edu.gatech.seclass.jobcompare6300.core;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.ComparisonSettingsWeightDao;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;

public class System {
    private static System system_instance = null;

    private AppDatabase appDatabase;
    private ComparisonSettingsWeightDao comparisonSettingsWeightDao;
    private JobDetailsDao jobDetailsDao;
    private ExecutorService executor;

    private ComparisonSettings comparisonSettings;

    private System(Context context)
    {
        this.appDatabase = AppDatabase.getInstance(context, false);
        this.comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        this.jobDetailsDao = this.appDatabase.jobDetailsDao();
        this.executor = Executors.newSingleThreadExecutor();
        this.comparisonSettings = new ComparisonSettings(context);
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

    public void updateComparisonWeights(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weights) {
        this.comparisonSettings.updateComparisonWeights(weights);
    }
}

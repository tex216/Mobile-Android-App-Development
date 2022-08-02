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
import edu.gatech.seclass.jobcompare6300.data.COMPARISON_SETTINGS_WEIGHT;
import edu.gatech.seclass.jobcompare6300.data.ComparisonSettingsWeightDao;

public class ComparisonSettings {
    private AppDatabase appDatabase;
    private ComparisonSettingsWeightDao comparisonSettingsWeightDao;
    private ExecutorService executor;
    private HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map = new HashMap<>();

    public ComparisonSettings(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        this.comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void setDefaultWeight() {
        this.executor.execute(() -> {
            if (this.comparisonSettingsWeightDao.getAllWeights().size() == 0) {
                this.comparisonSettingsWeightDao.setDefaultWeight();
            };
        });
    }

    public int getRemoteWorkPossibilityWeight() {
        return this.map.get(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT);
    }

    public int getYearlySalaryWeight() {
        return this.map.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT);
    }

    public int getYearlyBonusWeight() {
        return this.map.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT);
    }

    public int getRetirementBenefitsWeight() {
        return this.map.get(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT);
    }

    public int getLeaveTimeWeight() {
        return this.map.get(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT);
    }

    public HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> getAllWeights() throws ExecutionException, InterruptedException {
        Future obj = this.executor.submit((Callable) () -> this.comparisonSettingsWeightDao.getAllWeights());
        List<COMPARISON_SETTINGS_WEIGHT> comparison_settings_weights = (List<COMPARISON_SETTINGS_WEIGHT>) obj.get();
        for (COMPARISON_SETTINGS_WEIGHT i : comparison_settings_weights) {
            COMPARISON_SETTINGS_OPTIONS key = COMPARISON_SETTINGS_OPTIONS.valueOf(i.getWEIGHT());
            int value = i.getWEIGHT_VALUE();
            this.map.put(key, value);
        }
        return this.map;
    }

    public void updateComparisonWeights(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> weight) {
        int remoteWorkPossibilityWeight = weight.get(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT);
        int yearlySalaryWeight = weight.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT);
        int yearlyBonusWeight = weight.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT);
        int retirementBenefitsWeight = weight.get(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT);
        int leaveTimeWeight = weight.get(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT);

        this.executor.execute(() -> {
            this.comparisonSettingsWeightDao.updateComparisonWeights(remoteWorkPossibilityWeight, yearlySalaryWeight, yearlyBonusWeight, retirementBenefitsWeight, leaveTimeWeight);
        });
    }
}

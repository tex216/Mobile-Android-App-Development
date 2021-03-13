package edu.gatech.seclass.jobcompare6300;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ComparisonSettingsWeightDao {

    // Get all comparison settings
    @Query("SELECT * FROM COMPARISON_SETTINGS_WEIGHT")
    List<COMPARISON_SETTINGS_WEIGHT> getAllWeights();

    // Set default weights
    @Query("INSERT INTO COMPARISON_SETTINGS_WEIGHT (WEIGHT, WEIGHT_VALUE) VALUES ('REMOTE_WORK_POSSIBILITY_WEIGHT', 1), ('YEARLY_SALARY_WEIGHT', 1), ('YEARLY_BONUS_WEIGHT', 1), ('RETIREMENT_BENEFITS_WEIGHT', 1), ('LEAVE_TIME_WEIGHT', 1)")
    void setDefaultWeight();
    
    // Update comparison weights
    @Query("UPDATE COMPARISON_SETTINGS_WEIGHT SET WEIGHT_VALUE = CASE WEIGHT WHEN 'REMOTE_WORK_POSSIBILITY_WEIGHT' THEN :remoteWorkPossibilityWeight WHEN 'YEARLY_SALARY_WEIGHT' THEN :yearlySalaryWeight WHEN 'YEARLY_BONUS_WEIGHT' THEN :yearlyBonusWeight WHEN 'RETIREMENT_BENEFITS_WEIGHT' THEN :retirementBenefitsWeight WHEN 'LEAVE_TIME_WEIGHT' THEN :leaveTimeWeight ELSE WEIGHT END")
    int updateComparisonWeights(int remoteWorkPossibilityWeight, int yearlySalaryWeight, int yearlyBonusWeight, int retirementBenefitsWeight, int leaveTimeWeight);
}

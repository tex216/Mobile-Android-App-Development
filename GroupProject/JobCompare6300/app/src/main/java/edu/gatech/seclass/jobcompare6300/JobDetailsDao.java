package edu.gatech.seclass.jobcompare6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface Dao {

        // Get all jobs
        @Query("SELECT * FROM JOB_DETAILS ORDER BY JOB_SCORE DESC")
        List<Job> getAllJobs();

        // Get current job
        @Query("SELECT * FROM JOB_DETAILS WHERE IS_CURRENT_JOB = TRUE")
        List<Job> getCurrentJob();

        // Get selected jobs to compare
        @Query("SELECT * FROM JOB_DETAILS WHERE JOB_ID IN (first_job_id, second_job_id)")
        List<Job> getSelectedJobs(Int first_job_id, Int second_job_id);

        // Insert job detail
        @Insert
        void insertJob(JOB_DETAILS... job_details);

        // Get all comparison settings
        @Query("SELECT * FROM COMPARISON_SETTINGS")
        List<Weights> getAllWeights();

        // Update current job
        @Query("UPDATE JOB_DETAILS SET TITLE = :title ,COMPANY = :company ,CITY = :city ,STATE = :state ,COST_OF_LIVING_INDEX = :costOfLivingIndex ,WORK_REMOTE = :workRemote ,YEARLY_SALARY = :yearlySalary ,YEARLY_BONUS = :yearlyBonus ,PERCENTAGE_MATCHED = :percentageMatch ,LEAVE_TIME = :leaveTime WHERE IS_CURRENT_JOB = TRUE ")
        Int updateCurrentJob(String title, String company, String city, String state, int costOfLivingIndex, int workRemote, double yearlySalary, double yearlybonus, double percentageMatch, int leaveTime);

        // Set default weights
        @Query("INSERT INTO COMPARISON_SETTINGS (WEIGHT, WEIGHT_VALUE) VALUES (REMOTE_WORK_POSSIBILITY_WEIGHT, 1), (YEARLY_SALARY_WEIGHT, 1), (YEARLY_BONUS_WEIGHT, 1), (RETIREMENT_BENEFITS_WEIGHT, 1), (LEAVE_TIME_WEIGHT, 1)")
        void setDefaultWeight();

        // Update comparison weights
        @Query("UPDATE COMPARISON_SETTINGS SET REMOTE_WORK_POSSIBILITY_WEIGHT = :remoteWorkPossibilityWeight ,YEARLY_SALARY_WEIGHT = :yearlySalaryWeight ,YEARLY_BONUS_WEIGHT = :yearlyBonusWeight ,RETIREMENT_BENEFITS_WEIGHT = :retirementBenefitsWeight ,LEAVE_TIME_WEIGHT = :leaveTimeWeight")
        Int updateComparisonWeights(int remoteWorkPossibilityWeight, int yearlySalaryWeight, int yearlyBonusWeight, int retirementBenefitsWeight, int leaveTimeWeight);

}

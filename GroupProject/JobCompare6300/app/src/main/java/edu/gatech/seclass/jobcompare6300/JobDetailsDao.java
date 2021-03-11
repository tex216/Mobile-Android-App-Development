package edu.gatech.seclass.jobcompare6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobDetailsDao {
        // Get all jobs
        @Query("SELECT * FROM JOB_DETAILS ORDER BY SCORE DESC")
        List<JOB_DETAILS> getAllJobs();

        // Get current job
        @Query("SELECT * FROM JOB_DETAILS WHERE IS_CURRENT_JOB = 1")
        List<JOB_DETAILS> getCurrentJob();

        // Get selected jobs to compare
        @Query("SELECT * FROM JOB_DETAILS WHERE JOB_ID IN (:first_job_id, :second_job_id)")
        List<JOB_DETAILS> getSelectedJobs(int first_job_id, int second_job_id);

        // Insert job detail
        @Insert
        void insertJob(JOB_DETAILS... job_details);

        // Update current job
        @Query("UPDATE JOB_DETAILS SET TITLE = :title ,COMPANY = :company ,CITY = :city ,STATE = :state ,COST_OF_LIVING_INDEX = :costOfLivingIndex ,WORK_REMOTE = :workRemote ,YEARLY_SALARY = :yearlySalary ,YEARLY_BONUS = :yearlyBonus ,PERCENTAGE_MATCHED = :percentageMatch ,LEAVE_TIME = :leaveTime WHERE IS_CURRENT_JOB = 1")
        int updateCurrentJob(String title, String company, String city, String state, int costOfLivingIndex, int workRemote, double yearlySalary, double yearlyBonus, double percentageMatch, int leaveTime);

        // Update job score
        @Query("UPDATE JOB_DETAILS SET SCORE = :score WHERE JOB_ID = :jobId")
        void setScore(int jobId, double score);
}

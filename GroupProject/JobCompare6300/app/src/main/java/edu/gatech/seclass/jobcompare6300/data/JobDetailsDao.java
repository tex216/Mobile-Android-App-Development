package edu.gatech.seclass.jobcompare6300.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface JobDetailsDao {
        // Get all jobs
        @Query("SELECT * FROM JOB_DETAILS ORDER BY SCORE DESC")
        List<JOB_DETAILS> getAllJobs();

        // Get current job
        @Query("SELECT * FROM JOB_DETAILS WHERE IS_CURRENT_JOB = 1")
        JOB_DETAILS getCurrentJob();

        // Get selected jobs to compare
        @Query("SELECT * FROM JOB_DETAILS WHERE JOB_ID IN (:first_job_id, :second_job_id)")
        List<JOB_DETAILS> getSelectedJobs(int first_job_id, int second_job_id);

        // Insert job detail
        @Insert
        void insertJob(JOB_DETAILS... job_details);

        // Update current job
        @Update
        int updateCurrentJob(JOB_DETAILS... current_job);

        // Update job score
        @Query("UPDATE JOB_DETAILS SET SCORE = :score WHERE JOB_ID = :jobId")
        void setScore(int jobId, double score);

        // Refactored insert / update current job
        @Query("INSERT OR REPLACE INTO JOB_DETAILS(TITLE, COMPANY, CITY, STATE, COST_OF_LIVING_INDEX, WORK_REMOTE, YEARLY_SALARY, YEARLY_BONUS, PERCENTAGE_MATCHED, LEAVE_TIME, IS_CURRENT_JOB) VALUES(:title, :company, :city, :state, :costOfLiving, :remoteWork, :yearlySalary, :yearlyBonus, :retirement, :leaveTime, :isCurrentJob)")
        int insertReplaceJob(String title, String company, String city, String state, int costOfLiving, int remoteWork, double yearlySalary, double yearlyBonus, int retirement, int leaveTime, boolean isCurrentJob);
}

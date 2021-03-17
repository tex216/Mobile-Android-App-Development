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

        // Insert job detail
        @Insert
        void insertJob(JOB_DETAILS... job_details);

        // Update current job
        @Update
        int updateCurrentJob(JOB_DETAILS... current_job);

        // Update job score
        @Query("UPDATE JOB_DETAILS SET SCORE = :score WHERE JOB_ID = :jobId")
        void setScore(int jobId, double score);
}

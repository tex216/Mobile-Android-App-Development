package edu.gatech.seclass.jobcompare6300;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface JobDetailsDao {
        @Query("SELECT * FROM jobDetails")
        List<JobDetails> getAll();

        @Query("SELECT * FROM user WHERE uid IN (:userIds)")
        List<JobDetails> loadAllByIds(int[] userIds);

        @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
                "last_name LIKE :last LIMIT 1")
        JobDetails findByName(String first, String last);

        @Insert
        void insertAll(JobDetails... users);

        @Delete
        void delete(JobDetails user);
}

package edu.gatech.seclass.jobcompare6300.core;

import android.content.Context;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import edu.gatech.seclass.jobcompare6300.data.AppDatabase;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.data.JobDetailsDao;

public class Job {
    private AppDatabase appDatabase;
    private JobDetailsDao jobDetailsDao;
    private ExecutorService executor;

    public Job(Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
        this.jobDetailsDao = this.appDatabase.jobDetailsDao();
        this.executor = Executors.newSingleThreadExecutor();
    }

    public int getNumberOfJobs() throws ExecutionException, InterruptedException {
        Future obj = this.executor.submit((Callable) () -> this.jobDetailsDao.getAllJobs().size());
        Integer n = (Integer) obj.get();
        return n;
    }

    public List<JOB_DETAILS> getAllJobs() throws ExecutionException, InterruptedException {
        Future obj = this.executor.submit((Callable) () -> this.jobDetailsDao.getAllJobs());
        List<JOB_DETAILS> allJobs = (List<JOB_DETAILS>) obj.get();
        return allJobs;
    }

    public JOB_DETAILS getCurrentJob() throws ExecutionException, InterruptedException {
        Future obj = this.executor.submit((Callable) () -> this.jobDetailsDao.getCurrentJob());
        JOB_DETAILS currentJob = (JOB_DETAILS) obj.get();
        return currentJob;
    }

    public void addJob(JOB_DETAILS jobDetails) {
        executor.execute(() -> {
            this.jobDetailsDao.insertJob(jobDetails);
        });
    }

    public void updateJob(JOB_DETAILS currentJob) {
        executor.execute(() -> {
            this.jobDetailsDao.updateCurrentJob(currentJob);
        });
    }

    public void calculateScore(List<JOB_DETAILS> allJobs, HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map) throws Exception {
        int denominator = this.calculateDenominator(map);

        for (int i = 0; i < allJobs.size(); i++) {
            double newScore = this.calculateNewScore(allJobs.get(i), map, denominator);
            int index = i;
            executor.execute(() -> {
                this.jobDetailsDao.setScore(allJobs.get(index).getJOB_ID(), newScore);
            });
        }
    }

    private int calculateDenominator(HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map) {
        int totalWeights = 0;
        for (Map.Entry<COMPARISON_SETTINGS_OPTIONS, Integer> entry : map.entrySet()) {
            totalWeights += entry.getValue();
        }
        return totalWeights;
    }

    private double calculateNewScore(JOB_DETAILS job, HashMap<COMPARISON_SETTINGS_OPTIONS, Integer> map, int denominator) {
        double remoteWorkPossibilityWeight = (double)map.get(COMPARISON_SETTINGS_OPTIONS.REMOTE_WORK_POSSIBILITY_WEIGHT);
        double yearlySalaryWeight = (double)map.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_SALARY_WEIGHT);
        double yearlyBonusWeight = (double)map.get(COMPARISON_SETTINGS_OPTIONS.YEARLY_BONUS_WEIGHT);
        double retirementBenefitsWeight = (double)map.get(COMPARISON_SETTINGS_OPTIONS.RETIREMENT_BENEFITS_WEIGHT);
        double leaveTimeWeight = (double)map.get(COMPARISON_SETTINGS_OPTIONS.LEAVE_TIME_WEIGHT);

        double ays = job.getYEARLY_SALARY()/(((double)job.getCOST_OF_LIVING_INDEX())/100.0);
        double ayb = job.getYEARLY_BONUS()/(((double)job.getCOST_OF_LIVING_INDEX())/100.0);
        double rbp = (job.getPERCENTAGE_MATCHED()/100.0);
        double lt = (double)job.getLEAVE_TIME();
        double rwt = (double)job.getWORK_REMOTE();

        double firstTerm = yearlySalaryWeight/denominator * ays;
        double secondTerm = yearlyBonusWeight/denominator * ayb;
        double thirdTerm = retirementBenefitsWeight/denominator * (rbp * ays);
        double fourthTerm = leaveTimeWeight/denominator * (lt * ays/260.0);
        double fifthTerm = remoteWorkPossibilityWeight/denominator * ((260.0 - 52.0 * rwt) * (ays/260.0) / 8.0);

        double newScore = firstTerm + secondTerm + thirdTerm + fourthTerm - fifthTerm;
        return newScore;
    }

}

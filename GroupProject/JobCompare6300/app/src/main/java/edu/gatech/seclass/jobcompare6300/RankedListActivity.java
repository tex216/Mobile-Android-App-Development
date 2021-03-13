package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankedListActivity extends AppCompatActivity {
    private Button compare;
    private Button cancel;
    ListView ranked_list;

    private final Context context = this;
    private AppDatabase appDatabase = AppDatabase.getInstance(this.context);
    private JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
    private ComparisonSettingsWeightDao comparisonSettingsWeightDao = this.appDatabase.comparisonSettingsWeightDao();
    private ArrayList<String> listItem = new ArrayList<>();
    private ArrayAdapter adapter;
    private List<JOB_DETAILS> allJobs;
    private List<COMPARISON_SETTINGS_WEIGHT> comparison_settings_weights;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private List<Integer> countList=new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_list);

        compare = (Button) findViewById(R.id.btn_compare_ranked_list);
        cancel = (Button) findViewById(R.id.btn_return_ranked_list);
        ranked_list = (ListView) findViewById(R.id.ranked_list);


        this.executor.execute(() -> {
            //must be in this order
            //get all weights
            //get all the jobs
            //calculate and update the job scores
            //re-get all the jobs with new scores
            this.getAllWeights();
            this.allJobs = jobDetailsDao.getAllJobs();
            this.calculateJobScores();
            this.allJobs = jobDetailsDao.getAllJobs();
            handler.post(() -> {
                this.viewData();
                if(this.countList.size()!=2){
                    compare.setEnabled(false);
                    compare.setClickable(false);
                }
            });
        });

        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCompareJobsClick();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancelClick();
            }
        });

        ranked_list.setChoiceMode(this.ranked_list.CHOICE_MODE_MULTIPLE);
        ranked_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                handleItemSelect(parent, view, position, id);
            }
        });
    }

    private void viewData(){
        for (int i = 0; i < this.allJobs.size(); i++){
            JOB_DETAILS job = this.allJobs.get(i);
            if(job.getIS_CURRENT_JOB() == true){
                listItem.add("Title: " + job.getTITLE() + " (Current Job)"+"\n" + "Company: " + job.getCOMPANY());
            }else{
                listItem.add("Title: " + job.getTITLE() + "\n" + "Company: " + job.getCOMPANY());
            }
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, this.listItem);
        this.ranked_list.setAdapter(adapter);
    }

    private void handleItemSelect(AdapterView<?> parent, View view, int position, long id) {
        CheckedTextView v = (CheckedTextView) view;
        boolean currentCheck = v.isChecked();
        JOB_DETAILS job;
        if(v.isChecked()==true){
            this.countList.add(position);
            if(this.countList.size()<=2){
                job = this.allJobs.get(position);
                job.setSelectedItem(currentCheck);
            }else{
                job = this.allJobs.get(this.countList.get(0));
                job.setSelectedItem(currentCheck);
                this.ranked_list.setItemChecked(this.countList.get(0), false);
                this.countList.remove(0);
                job = this.allJobs.get(position);
                job.setSelectedItem(currentCheck);
            }
        }else{
            this.countList.remove(Integer.valueOf(position));
            this.allJobs.get(position).setSelectedItem(false);
        }
        if(countList.size()==2){
            compare.setEnabled(true);
            compare.setClickable(true);
        }else{
            compare.setEnabled(false);
            compare.setClickable(false);
        }

    }

    private void handleCompareJobsClick() {
        List<JOB_DETAILS> selectedJobs = new ArrayList<JOB_DETAILS>();
        for (int i = 0; i < allJobs.size(); i++) {
            if (allJobs.get(i).isSelectedItem() == true) {
                selectedJobs.add(allJobs.get(i));
            }
        }

        Intent intent = new Intent(this, CompareJobsActivity.class);
        intent.putExtra("selected_jobs", (ArrayList<JOB_DETAILS>)selectedJobs);
        startActivity(intent);
    }

    private void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void calculateJobScores() {
        int denominator = this.calculateWeightsDenominator();

        HashMap<String, Integer> map = this.convertListToHashmap();
        this.executor.execute(() -> {
            for (int i = 0; i < this.allJobs.size(); i++) {
                JOB_DETAILS job = this.allJobs.get(i);
                double remoteWorkPossibilityWeight = (double)map.get("REMOTE_WORK_POSSIBILITY_WEIGHT");
                double yearlySalaryWeight = (double)map.get("YEARLY_SALARY_WEIGHT");
                double yearlyBonusWeight = (double)map.get("YEARLY_BONUS_WEIGHT");
                double retirementBenefitsWeight = (double)map.get("RETIREMENT_BENEFITS_WEIGHT");
                double leaveTimeWeight = (double)map.get("LEAVE_TIME_WEIGHT");

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
                this.jobDetailsDao.setScore(this.allJobs.get(i).getJOB_ID(), newScore);
            }
        });
    }

    private int calculateWeightsDenominator() {
        int totalWeights = 0;
        for (COMPARISON_SETTINGS_WEIGHT i : this.comparison_settings_weights) {
            totalWeights = totalWeights + i.getWEIGHT_VALUE();
        }
        return totalWeights;
    }

    private void getAllWeights() {
        this.comparison_settings_weights = comparisonSettingsWeightDao.getAllWeights();
    }

    private HashMap<String, Integer> convertListToHashmap() {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (COMPARISON_SETTINGS_WEIGHT i : this.comparison_settings_weights) map.put(i.getWEIGHT(), i.getWEIGHT_VALUE());
        return map;
    }
}
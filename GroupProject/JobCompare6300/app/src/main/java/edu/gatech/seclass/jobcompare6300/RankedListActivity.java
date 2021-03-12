package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RankedListActivity extends AppCompatActivity {

    private Button compare;
    private Button cancel;
    private final Context context = this;
    private AppDatabase appDatabase;
    private ArrayList<String> listItem;
    private ArrayAdapter adapter;
    ListView ranked_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranked_list);

        appDatabase = AppDatabase.getInstance(context);
        ranked_list = findViewById(R.id.ranked_list);

        compare = (Button) findViewById(R.id.btn_compare_ranked_list);
        cancel = (Button) findViewById(R.id.btn_return_ranked_list);

        listItem = new ArrayList<>();

        viewData();

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

        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            List<JOB_DETAILS> allJobs = jobDetailsDao.getAllJobs();
            for (int i = 0; i<allJobs.size();i++){

            }

        });
    }
    public void viewData(){
        JobDetailsDao jobDetailsDao = this.appDatabase.jobDetailsDao();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            List<JOB_DETAILS> allJobs = jobDetailsDao.getAllJobs();

            for (int i = 0; i<allJobs.size();i++){
                JOB_DETAILS job = allJobs.get(i);
                if(job.getIS_CURRENT_JOB()==true){
                    listItem.add("Title: " + job.getTITLE() + " (Current Job)"+"\n" + "Company: " + job.getCOMPANY());
                }else{
                    listItem.add("Title: " + job.getTITLE() + "\n" + "Company: " + job.getCOMPANY());
                }
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
            ranked_list.setAdapter(adapter);


        });
    }


    public void handleCompareJobsClick() {
        Intent intent = new Intent(this, CompareJobsActivity.class);
        startActivity(intent);
    }

    public void handleCancelClick() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
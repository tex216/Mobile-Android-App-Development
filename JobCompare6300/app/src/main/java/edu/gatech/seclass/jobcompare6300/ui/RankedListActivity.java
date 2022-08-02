package edu.gatech.seclass.jobcompare6300.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import edu.gatech.seclass.jobcompare6300.data.JOB_DETAILS;
import edu.gatech.seclass.jobcompare6300.R;

public class RankedListActivity extends BaseActivity {
    private Button compare;
    private Button cancel;
    ListView ranked_list;

    private ArrayList<String> listItem = new ArrayList<>();
    private ArrayAdapter adapter;
    private List<JOB_DETAILS> allJobs;
    private List<Integer> countList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initializeUI();

        try {
            //must re-get all the jobs after score calculation
            this.system.calculateScore();
            this.allJobs = this.system.getAllJobs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.viewData();
        this.shouldDisableCompareButton();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_ranked_list;
    }

    @Override
    protected void initializeUI() {
        this.compare = (Button) findViewById(R.id.btn_compare_ranked_list);
        this.cancel = (Button) findViewById(R.id.btn_return_ranked_list);
        this.ranked_list = (ListView) findViewById(R.id.ranked_list);
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
                handleItemSelect(view, position);
            }
        });
    }

    private void shouldDisableCompareButton() {
        if(this.countList.size()!=2){
            compare.setEnabled(false);
            compare.setClickable(false);
        }
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

    private void handleItemSelect(View view, int position) {
        CheckedTextView v = (CheckedTextView) view;
        boolean currentCheck = v.isChecked();
        JOB_DETAILS job;
        if (v.isChecked() == true) {
            this.countList.add(position);
            if (this.countList.size() <= 2) {
                job = this.allJobs.get(position);
                job.setSelectedItem(currentCheck);
            } else {
                job = this.allJobs.get(this.countList.get(0));
                job.setSelectedItem(currentCheck);
                this.ranked_list.setItemChecked(this.countList.get(0), false);
                this.countList.remove(0);
                job = this.allJobs.get(position);
                job.setSelectedItem(currentCheck);
            }
        } else {
            this.countList.remove(Integer.valueOf(position));
            this.allJobs.get(position).setSelectedItem(false);
        }
        if (countList.size() == 2) {
            compare.setEnabled(true);
            compare.setClickable(true);
        } else {
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
}
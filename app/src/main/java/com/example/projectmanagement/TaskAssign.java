package com.example.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import java.util.List;

public class TaskAssign extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SpinnerAdapter {

    DataBaseHelper mydb;
    EditText prid;
    EditText bname;
    EditText status;
    Button addbtn;
    String dtext;
    Spinner spinner;
    Spinner taskes;
    ArrayAdapter<String> dataAdapter;
    ArrayAdapter<String> tasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_assign);
        mydb =new DataBaseHelper(this);
        spinner = (Spinner) findViewById(R.id.spinner);
        taskes = (Spinner) findViewById(R.id.tasks);
        String ppid = getIntent().getStringExtra("pid");
        String pname = mydb.getProname(ppid);
        String ddd = getIntent().getStringExtra("dead");
        List<String> plabels = mydb.getAllTasks(ppid);
        taskes.setOnItemSelectedListener(this);
        spinner.setOnItemSelectedListener(this);
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, plabels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        String teamn = getIntent().getStringExtra("team");
        List<String> elabels = mydb.getAllEmps(teamn);
        tasks = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, elabels);
        tasks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskes.setAdapter(tasks);

        prid = (EditText)findViewById(R.id.proid);
        prid.setText(ppid);
        bname = (EditText)findViewById(R.id.name);
        bname.setText(pname);
        status =  (EditText)findViewById(R.id.status);
        status.setText(ddd);
        addbtn = (Button)findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status.getText().toString().isEmpty() || prid.getText().toString().isEmpty() || bname.getText().toString().isEmpty())
                {
                    prid.setError("Please Enter All fields!!");
                }
                else{
                    String id = prid.getText().toString();
                    String name = bname.getText().toString();
                    String tid = spinner.getSelectedItem().toString();
                    String emid = taskes.getSelectedItem().toString();
                    String deadline = status.getText().toString();
                    boolean  valid  =mydb.insertassign(id,name,emid,tid,deadline);
                    if(valid) {
                        prid.setText("");
                        bname.setText("");
                        status.setText("");
                        Toast.makeText(TaskAssign.this, "Assigned!!", Toast.LENGTH_LONG).show();
                    }//FragmentManager fm = getSupportFragmentManager();
                    //EmployeesFragment fragment = new EmployeesFragment();
                    //fm.beginTransaction().replace(R.id.addid,fragment).commit();
                }

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}


package com.example.projectmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class EmployeeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView proid,proname,teamname,status,dealine,empid;
    EditText stat;
    Spinner task;
    DataBaseHelper db;
    ArrayAdapter<String> dataAdapter;
    Button addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        db = new DataBaseHelper(this);
        proid = (TextView) findViewById(R.id.id);
        proname  = (TextView) findViewById(R.id.name);
        teamname  = (TextView) findViewById(R.id.tname);
        status  = (TextView) findViewById(R.id.stat);
        addbtn = (Button) findViewById(R.id.add);
        dealine  = (TextView) findViewById(R.id.dead);
        empid = (TextView)  findViewById(R.id.textView7);
        stat = (EditText)  findViewById(R.id.percen);
        task = (Spinner)  findViewById(R.id.spinner);
        final String id = getIntent().getStringExtra("eid");
        task.setOnItemSelectedListener(this);
        List<String> labels = db.getidTasks(id);
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        task.setAdapter(dataAdapter);
        //Toast.makeText(this,id,Toast.LENGTH_LONG).show();
        final Cursor res = db.getiddata(id);
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"no data found",Toast.LENGTH_LONG).show();
        }
        else
        {
            proid.setText(res.getString(0));
            String ppid = res.getString(0);
            proname.setText(res.getString(1));
            dealine.setText(res.getString(4));
            empid.setText(id);
            final Cursor tres= db.getidinfo(ppid);
            teamname.setText(tres.getString(1));
            status.setText(tres.getString(3));

            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String prid = proid.getText().toString();
                    String team = teamname.getText().toString();
                    String taskid = task.getSelectedItem().toString();
                    String st = stat.getText().toString();
                    String employee = id;
                    if(stat.getText().toString().isEmpty())
                    {
                        stat.setError("Edit Required fields!!");
                    }
                    else{
                        boolean isupdate = db.insertStatus(employee,prid,team,taskid,st);
                        if(isupdate==true) {
                            stat.setText("");
                            db.updatestatus(st,taskid,prid);
                            Toast.makeText(EmployeeActivity.this,"Updated!",Toast.LENGTH_LONG).show();
                            finish();
                            startActivity(getIntent());
                        }
                    }
                }
            });

        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

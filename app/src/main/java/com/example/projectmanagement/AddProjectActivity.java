package com.example.projectmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.projectmanagement.ui.projects.ProjectsFragment;

public class AddProjectActivity extends AppCompatActivity {
    DataBaseHelper mydb;
    EditText pid;
    EditText pname;
    EditText task1;
    EditText task2;
    EditText task3;
    Button addbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb =new DataBaseHelper(this);
        setContentView(R.layout.add_project);
        pid = (EditText)findViewById(R.id.proid);
        pname = (EditText)findViewById(R.id.pname);
        task1 = (EditText)findViewById(R.id.task1);
        task2 = (EditText)findViewById(R.id.task2);
        task3 = (EditText)findViewById(R.id.task3);
        //button
        addbtn = (Button)findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pid.getText().toString().isEmpty() || pname.getText().toString().isEmpty() || task1.getText().toString().isEmpty())
                {
                    pid.setError("Please Enter All fields!!");
                }
                else{
                    String id = pid.getText().toString();
                    String name = pname.getText().toString();
                    String t1 = task1.getText().toString();
                    String t2 = task2.getText().toString();
                    String t3 = task3.getText().toString();
                    mydb.insertProjects(id,name,t1,t2,t3);
                    pid.setText("");
                    pname.setText("");
                    task1.setText("");
                    task2.setText("");
                    task3.setText("");
                    Toast.makeText(getBaseContext(),"Added",Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(AddProjectActivity.this,AdminActivity.class);
                    startActivity(intent);
                    //FragmentManager fm = getSupportFragmentManager();
                    //EmployeesFragment fragment = new EmployeesFragment();
                    //fm.beginTransaction().replace(R.id.addid,fragment).commit();
                }

            }
        });
    }

}
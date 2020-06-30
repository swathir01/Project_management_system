package com.example.projectmanagement;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.List;

public class AssignActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataBaseHelper mydb;
    Spinner proid;
    DatePickerDialog picker;
    EditText eText;
    EditText teamname;
    ArrayAdapter<String> dataAdapter;
    Button addbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb =new DataBaseHelper(this);
        setContentView(R.layout.activity_assign);
        proid = findViewById(R.id.proid);
        final Calendar cldr = Calendar.getInstance();
        proid.setOnItemSelectedListener(this);
        List<String> labels = mydb.getAllLabels();
        dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        proid.setAdapter(dataAdapter);
        teamname = (EditText)findViewById(R.id.teamname);
        addbtn = (Button)findViewById(R.id.add);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamname.getText().toString().isEmpty() || proid.getSelectedItem().toString().isEmpty())
                {
                    addbtn.setError("Please Enter All fields!!");
                }
                else{
                    String id = proid.getSelectedItem().toString();
                    String name = teamname.getText().toString();
                    String lastdate = ""+ cldr.get(Calendar.DAY_OF_MONTH) + "/" + cldr.get(Calendar.MONTH)+"/"+cldr.get(Calendar.YEAR);
                    String completion = "0";
                    mydb.assignProject(id,name,lastdate,completion);
                    teamname.setText("");
                    cldr.setFirstDayOfWeek(Calendar.MONDAY);
                    proid.setSelection(dataAdapter.getPosition("Manager"));
                    //Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(AssignActivity.this,AdminActivity.class);
                    startActivity(intent);
                    //FragmentManager fm = getSupportFragmentManager();
                    //EmployeesFragment fragment = new EmployeesFragment();
                    //fm.beginTransaction().replace(R.id.addid,fragment).commit();
                }

            }
        });
        eText=(EditText) findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AssignActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String proid = parent.getItemAtPosition(position).toString();
        Toast.makeText(this,proid + " is assigned!", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


package com.example.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectmanagement.ui.employees.EmployeesFragment;

public class EditActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataBaseHelper mydb;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView empid;
    EditText teamname;
    Button editbtn;
    String dtext;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb =new DataBaseHelper(this);
        setContentView(R.layout.activity_edit);
        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.designation,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        empid = (TextView)findViewById(R.id.eid);
        final String id = getIntent().getStringExtra("empid");
        final String ename = getIntent().getStringExtra("name");
        empid.setText(id);
        teamname =  (EditText)findViewById(R.id.teamname);
        //RADIO GROUP
        radioGroup = findViewById(R.id.radioGroup);
        //button
        editbtn = (Button)findViewById(R.id.editbtn);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tname = teamname.getText().toString();
                String desg = dtext;
                String tlead = checkButton();
                boolean isupdate = mydb.updateData(id,ename,desg,tname,tlead);
                if(isupdate == true) {
                    if(teamname.getText().toString().isEmpty())
                    {
                        teamname.setError("Team name is required!!");
                    }
                    else{
                        teamname.setText("");
                        radioGroup.check(R.id.rd1);
                        spinner.setSelection(adapter.getPosition("Manager"));
                        //FragmentManager fm = getSupportFragmentManager();
                        //EmployeesFragment fragment = new EmployeesFragment();
                        //fm.beginTransaction().replace(R.id.editid, fragment).commit();
                        Intent intent =  new Intent(EditActivity.this,AdminActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
    public String checkButton()
    {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton =  findViewById(radioId);
        return radioButton.getText().toString();
        //Toast.makeText(this,radioButton.getText(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dtext = parent.getItemAtPosition(position).toString();
        //Toast.makeText(this,dtext, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
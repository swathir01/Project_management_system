package com.example.projectmanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DataBaseHelper mydb;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText bname,emailid;
    EditText teamname;
    Button addbtn;
    String dtext;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mydb =new DataBaseHelper(this);
        setContentView(R.layout.activity_add);
        spinner = findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.designation,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        bname = (EditText)findViewById(R.id.name);
        teamname =  (EditText)findViewById(R.id.teamname);
        //RADIO GROUP
        radioGroup = findViewById(R.id.radioGroup);
        emailid = (EditText) findViewById(R.id.empid);
        //button
        addbtn = (Button)findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teamname.getText().toString().isEmpty() ||  emailid.getText().toString().isEmpty() ||bname.getText().toString().isEmpty())
                {
                    emailid.setError("Please Enter All fields!!");
                }
                else{
                    String name = bname.getText().toString();
                    String eid = emailid.getText().toString();
                    String tname = teamname.getText().toString();
                    String desg = dtext;
                    String tlead = checkButton();
                    mydb.insertData(name,desg,tname,tlead);
                    String lastid = mydb.getlastid();
                    sendMessage(name,lastid,eid);
                    bname.setText("");
                    teamname.setText("");
                    radioGroup.check(R.id.rd1);
                    spinner.setSelection(adapter.getPosition("Manager"));
                    //Toast.makeText(this,"Added",Toast.LENGTH_LONG).show();
                    Intent intent =  new Intent(AddActivity.this,AdminActivity.class);
                    startActivity(intent);
                    //FragmentManager fm = getSupportFragmentManager();
                    //EmployeesFragment fragment = new EmployeesFragment();
                    //fm.beginTransaction().replace(R.id.addid,fragment).commit();
                }

            }
        });
    }
    private void sendMessage(final String nam, final String id, final String ed) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("lalithswathi01@gmail.com","lalith@01");
                    sender.sendMail("XYZ Company","Hi "+nam+"!\nYour Employee ID: "+ id+".","lalithswathi01@gmail.com",ed);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
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
        Toast.makeText(this,dtext, Toast.LENGTH_LONG).show();
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

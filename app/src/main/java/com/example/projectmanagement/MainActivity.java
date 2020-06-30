package com.example.projectmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private Button Login;
    private TextView Info;
    private int counter = 5;
    private TextView signup;
    private TextView adminlink;
    DataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHelper(this);

        Name = (EditText)findViewById(R.id.username);
        Password = (EditText)findViewById(R.id.logpassword);
        Login = (Button)findViewById(R.id.btnlogin);
        //Info = (TextView)findViewById(R.id.tvinfo);
        //Info.setText("No of remaining attempts: 5");
        adminlink = (TextView)findViewById(R.id.adminlink);
        signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);

                //Toast.makeText(MainActivity.this,"you clicked on text",Toast.LENGTH_LONG).show();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exists= loginvalidate(Name.getText().toString());
                if(exists==true)
                {
                String user = Name.getText().toString();
                String pwd = Password.getText().toString().trim();
                boolean res = db.checkUser(user, pwd);
                Cursor iddata = db.getlead(user);
                String lead = iddata.getString(4);
                String teamname = iddata.getString(3);
                if(res == true)
                {
                  if(lead.equals("Yes")) {
                      Intent HomePage = new Intent(MainActivity.this, HomeActivity.class);
                      HomePage.putExtra("eid",user);
                      HomePage.putExtra("eeid",user);
                      HomePage.putExtra("tname",teamname);
                      HomePage.putExtra("lpro",teamname);
                      startActivity(HomePage);
                      Name.setText("");
                      Password.setText("");
                  }
                  else
                  {
                      Intent HomePage = new Intent(MainActivity.this, EmployeeActivity.class);
                      HomePage.putExtra("eid",user);
                      HomePage.putExtra("eeid",user);
                      HomePage.putExtra("tname",teamname);
                      HomePage.putExtra("lpro",teamname);
                      startActivity(HomePage);
                      Name.setText("");
                      Password.setText("");
                  }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Invalid Password",Toast.LENGTH_SHORT).show();
                    Name.setText("");
                    Password.setText("");
                }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Invalid ID!",Toast.LENGTH_SHORT).show();
                    Name.setText("");
                    Password.setText("");
                }

            }
        });
        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(Name.getText().toString(),Password.getText().toString());

            }
        });

    }
    private boolean loginvalidate(String user)
    {
     return db.checkId(user);
    }
    private void validate(String user,String pass)
    {
        if((user.equals("swathi"))&&(pass.equals("sss")))
        {
            Intent intent = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(intent);
            Name.setText("");
            Password.setText("");
            Toast.makeText(MainActivity.this,"Hai Manager",Toast.LENGTH_LONG).show();
        }else{
            counter--;
            Toast.makeText(MainActivity.this,"You are not a Manager",Toast.LENGTH_LONG).show();
            //Info.setText("No of remaining attempts:"+String.valueOf(counter));
            if(counter==0)
            {
                Login.setEnabled(false);
            }
        }
    }

}

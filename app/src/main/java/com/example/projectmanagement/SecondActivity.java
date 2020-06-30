package com.example.projectmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText mTextUsername;
    EditText emailid;
    EditText phnno;
    EditText mTextPassword;
    EditText mTextCnfPassword;
    Button mButtonRegister;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        db = new DataBaseHelper(this);
        mTextUsername = (EditText)findViewById(R.id.edittext_username);
        mTextPassword = (EditText)findViewById(R.id.edittext_password);
        emailid = (EditText)findViewById(R.id.emailid);
        phnno = (EditText)findViewById(R.id.phnno);
        mTextCnfPassword = (EditText)findViewById(R.id.edittext_cnf_password);
        mButtonRegister = (Button)findViewById(R.id.button_register);

        mTextViewLogin = (TextView)findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(SecondActivity.this,MainActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = mTextUsername.getText().toString();
                String email = emailid.getText().toString();
                String phn = phnno.getText().toString();
                String pwd = mTextPassword.getText().toString();
                String cnf_pwd = mTextCnfPassword.getText().toString();
                boolean exists = loginvalidate(user);
                if (exists) {
                    if (pwd.equals(cnf_pwd)) {
                        boolean val = db.addUser(user, email, phn, pwd);
                        if (val) {
                            Toast.makeText(SecondActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                            Intent moveToLogin = new Intent(SecondActivity.this, MainActivity.class);
                            startActivity(moveToLogin);
                        } else {
                            Toast.makeText(SecondActivity.this, "Registeration Error", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(SecondActivity.this, "Password is not matching", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SecondActivity.this, "Invalid ID!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private boolean loginvalidate(String sid)
    {
        return db.checkId(sid);
    }

}

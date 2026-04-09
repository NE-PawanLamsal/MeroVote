package com.pec.project.merovote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pec.project.merovote.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText username,password;
    Button login;
    Switch active;
    TextView contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact= findViewById(R.id.tvContact);
        username= findViewById(R.id.etUsername);
        password= findViewById(R.id.etPassword);
        login=findViewById(R.id.btnLogin);
        active=findViewById(R.id.active);
        getSupportActionBar().hide();

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,AboutUs.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input1 = username.getText().toString();
                String hash = password.getText().toString();
                String real = "VoteCount";
                if (input1.equals("admin") & hash.equals(real)) {
                    if (active.isChecked()) {
                        preferences.setDataLogin(LoginActivity.this, true);
                        preferences.setDataAs(LoginActivity.this, "admin");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        preferences.setDataLogin(LoginActivity.this, false);
                        preferences.setDataAs(LoginActivity.this, "user");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                }

            }




        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preferences.getDataLogin(this)){
            if (preferences.getDataAs(this).equals("admin")){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }else if (preferences.getDataAs(this).equals("user")){
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }
        }
    }
}
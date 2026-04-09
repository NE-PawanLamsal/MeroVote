package com.pec.project.merovote;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {

    private Button logout,refresh,reset;
    private TextView VoteA;
    private TextView VoteB;
    private TextView VoteC;
    private static String ip ="172.20.144.1",port="1433";
    private static String Classes = "net.sourceforge.jtds.jdbc.Driver";
    private static String db = "test",user = "test", pass ="Pa19846029656";
    private static String url = "jdbc:jtds:sqlserver://"+ip+":"+port+"/"+db;
    private Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        getSupportActionBar().hide();
        logout = findViewById(R.id.btnLogOut);
        reset = findViewById(R.id.btnreset);
        refresh = findViewById(R.id.Refresh);
        VoteA= findViewById(R.id.VoteA);
        VoteB= findViewById(R.id.VoteB);
        VoteC= findViewById(R.id.VoteC);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName(Classes);
            connection = DriverManager.getConnection(url,user,pass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            VoteA.setText("Failure");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            VoteA.setText("Failure");
        }
        voteA();
        voteB();
        voteC();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogout(view);
            }
        });

    }

    private void voteA() {
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select Max(sensor1) from vote");
                while (resultSet.next()) {
                    VoteA.setText(resultSet.getString(1));

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            VoteA.setText("Connection error");
        }
    }

    private void voteB() {
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select Max(sensor2) from vote");
                while (resultSet.next()) {
                    VoteB.setText(resultSet.getString(1));

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            VoteA.setText("Connection error");
        }
    }

    private void voteC() {
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select Max(sensor3) from vote");
                while (resultSet.next()) {
                    VoteC.setText(resultSet.getString(1));

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            VoteA.setText("Connection error");
        }
    }



    public void userlogout(View view) {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        preferences.clearData(this);
        finish();
    }

    public void reset(){
        if (connection != null) {
            Statement statement = null;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("TRUNCATE TABLE vote");
                while (resultSet.next()) {
                    VoteB.setText(resultSet.getString(1));

                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            VoteA.setText("Connection error");
        }
    }

}
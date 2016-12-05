package com.nguyenthanhtu.doanmobile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by thanhtu on 9/30/16.
 */
public class RegisterActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword, txtPassword2;
    Button btnSignUp;
    TextView link_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        addControls();
        addActions();
    }

    private void addActions() {

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        txtUsername  = (EditText) findViewById(R.id.txtUsername);
        txtPassword  = (EditText) findViewById(R.id.txtPassword);
        txtPassword2 = (EditText) findViewById(R.id.txtPassword2);
        btnSignUp    = (Button)   findViewById(R.id.btnSignUp);
        link_login   = (TextView) findViewById(R.id.link_login);
    }
}

package com.nguyenthanhtu.doanmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

import java.util.ArrayList;

/**
 * Created by thanhtu on 9/30/16.
 */
public class LoginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    Button btnLogin;
    TextView link_signup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        addControls();
        addActions();

    }

    private void getData() {
        Category cate1 = new Category("Loai1","Lon");
        Category cate2 = new Category("Loai2","Chai");

        Data.listDataCategory = new ArrayList<Category>();
        Data.listDataCategory.add(cate1);
        Data.listDataCategory.add(cate2);

        Data.listDataProduct = new ArrayList<Product>();
        Data.listDataProduct.add(new Product("SP1","Coca cola",R.drawable.cocacola, cate1, 10.000, 330, "Pesico"));
        Data.listDataProduct.add(new Product("SP2","Coca cola",R.drawable.cocacola, cate1, 20.000, 330, "Pesico"));
        Data.listDataProduct.add(new Product("SP3","Coca cola",R.drawable.cocacola, cate1, 30.000, 330, "Pesico"));
        Data.listDataProduct.add(new Product("SP4","Coca cola",R.drawable.cocacola, cate1, 40.000, 330, "Pesico"));
        Data.listDataProduct.add(new Product("SP5","Coca cola",R.drawable.cocacola, cate1, 50.000, 330, "Pesico"));
    }

    private void addControls() {
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin    = (Button)   findViewById(R.id.btnLogin);
        link_signup = (TextView) findViewById(R.id.link_signup);
    }

    private void addActions() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this,"login",Toast.LENGTH_LONG).show();
                if (txtUsername.getText().toString().trim().equals("thanhtu") && txtPassword.getText().toString().trim().equals("123456")){
                    getData();

                    Intent intentLogin = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intentLogin);
                }
            }
        });

        link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

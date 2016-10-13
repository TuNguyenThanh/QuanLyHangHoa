package com.nguyenthanhtu.doanmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;


/**
 * Created by thanhtu on 10/12/16.
 */
public class AddProductActivity extends AppCompatActivity  {

    TextView txtId;
    EditText edtAddName, edtAddCost, edtAddVolumetric, edtAddProduction;
    Spinner spinnerCategory;
    ImageView imgAddProduct;
    Button btnAdd;
    private ArrayAdapter<Category> adapterCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
    }

    private void addProduct() {
        String idProduct = "SP9";//txtId.getText().toString();
        String name = edtAddName.getText().toString();
        String production = edtAddProduction.getText().toString();
        if (name.trim().length() > 0 && production.trim().length() > 0 && edtAddCost.getText().toString().trim().length() > 0 && edtAddVolumetric.getText().toString().trim().length() > 0 && spinnerCategory.getSelectedItem() != null){
            Double cost = Double.parseDouble(edtAddCost.getText().toString());
            Integer volumetric = Integer.parseInt(edtAddVolumetric.getText().toString());
            Category cate = (Category) spinnerCategory.getSelectedItem();
            //test
            Integer imgProduct = R.drawable.cocacola;

            Product product = new Product(idProduct,name,imgProduct,cate,cost,volumetric,production);

            Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
            intent.putExtra("ADD", product);
            setResult(100, intent);
            finish();
        }else{
            Toast.makeText(AddProductActivity.this, String.valueOf(AddProductActivity.this.getString(R.string.simple_messErrorSpace)), Toast.LENGTH_SHORT).show();
        }

    }

    private void addControls() {
        txtId = (TextView) findViewById(R.id.txtId);
        edtAddName = (EditText) findViewById(R.id.edtAddName);
        edtAddCost = (EditText) findViewById(R.id.edtAddCost);
        edtAddVolumetric = (EditText) findViewById(R.id.edtAddVolumetric);
        edtAddProduction = (EditText) findViewById(R.id.edtAddProduction);
        spinnerCategory  = (Spinner) findViewById(R.id.spinnerCategory);
        imgAddProduct = (ImageView) findViewById(R.id.imgProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        //set id product
        //String.valueOf(AddProductActivity.this.getString(R.string.simple_codeProduct))
        //txtId.setText(String.valueOf(AddProductActivity.this.getString(R.string.simple_codeProduct)));

        adapterCategory = new ArrayAdapter<Category>(AddProductActivity.this, android.R.layout.simple_list_item_1, Data.listDataCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCategory.setAdapter(adapterCategory);
    }
}

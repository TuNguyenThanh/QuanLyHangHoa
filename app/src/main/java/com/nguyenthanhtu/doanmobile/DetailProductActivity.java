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
public class DetailProductActivity extends AppCompatActivity {

    ImageView imgProduct;
    TextView txtIdProduct;
    EditText edtName, edtCost, edtVolumetric, edtProduction;
    Spinner spinnerEditCategory;
    Button btnEdit, btnDel, btnExitEdit;
    Product product;
    private ArrayAdapter<Category> adapterCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        addControls();
        addEvents();
        getData();
    }

    private void getData() {
        if (getIntent().getExtras() != null) {
            product = (Product) getIntent().getSerializableExtra("EDIT");

            //Day len giao dien
            imgProduct.setImageResource(product.getProductImage());
            txtIdProduct.setText(product.getProductId());
            edtName.setText(product.getProductName());
            edtCost.setText(String.valueOf(product.getProductCost()));
            edtVolumetric.setText(String.valueOf(product.getProductVolumetric()));
            edtProduction.setText(product.getProduction());
            spinnerEditCategory.setSelection(adapterCategory.getPosition(product.getProductCategory()));
        }
    }

    private void addEvents() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = product.getProductId();
                String name = edtName.getText().toString();
                Double cost = Double.parseDouble(edtCost.getText().toString());
                Integer volumetric = Integer.parseInt(edtVolumetric.getText().toString());
                String production = edtProduction.getText().toString();
                Category cate = (Category) spinnerEditCategory.getSelectedItem();
                Integer imgProduct = product.getProductImage();

                Product productEdit = new Product(id,name,imgProduct,cate,cost,volumetric,production);

                Intent intent = new Intent(DetailProductActivity.this, MainActivity.class);
                intent.putExtra("EDIT", productEdit);
                setResult(200, intent);
                finish();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailProductActivity.this, MainActivity.class);
                intent.putExtra("DEL", product.getProductId());
                setResult(300, intent);
                finish();
            }
        });

        btnExitEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addControls() {
        imgProduct   = (ImageView) findViewById(R.id.imgEditProduct);
        txtIdProduct = (TextView) findViewById(R.id.txtEditId);
        edtCost = (EditText) findViewById(R.id.edtEditCost);
        edtName = (EditText) findViewById(R.id.edtEditName);
        edtVolumetric = (EditText) findViewById(R.id.edtEditVolumetric);
        edtProduction = (EditText) findViewById(R.id.edtEditProduction);
        spinnerEditCategory = (Spinner) findViewById(R.id.spinnerEditCategory);

        btnDel = (Button) findViewById(R.id.btnDel);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        btnExitEdit = (Button) findViewById(R.id.btnExitEdit);

        adapterCategory = new ArrayAdapter<Category>(DetailProductActivity.this, android.R.layout.simple_list_item_1, Data.listDataCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerEditCategory.setAdapter(adapterCategory);
    }
}

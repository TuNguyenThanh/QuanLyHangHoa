package com.nguyenthanhtu.doanmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

import java.util.ArrayList;

/**
 * Created by thanhtu on 10/12/16.
 */
public class AddCategoryActivity extends AppCompatActivity {

    EditText txtId, txtName;
    Button btnAddCategory, btnDelCategory, btnEditCategory;
    ListView lvCategory;
    Category categorySelected;
    private ArrayAdapter<Category> adapterCategory;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        addControls();
        addEvents();
    }

    private void addEvents() {
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category ca = adapterCategory.getItem(position);
                categorySelected = ca;

                txtId.setText(ca.getCategoryId());
                txtName.setText(ca.getCategoryName());
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "Loai3";
                String nameCategory = txtName.getText().toString();

                if (nameCategory.trim().length() > 0) {
                    Category categoryNew = new Category(id, nameCategory);
                    adapterCategory.add(categoryNew);
                    adapterCategory.notifyDataSetChanged();
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messAddSuccess)), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorSpace)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
//                intent.putExtra("DELCATEGORY", categorySelected.getCategoryId());
//                setResult(400, intent);
//                finish();
                if (categorySelected != null){
                    int position = -1;
                    for (int i = 0 ; i< adapterCategory.getCount();i++){
                        if (adapterCategory.getItem(i).getCategoryId().equals(categorySelected.getCategoryId())) {
                            position = i;
                            break;
                        }
                    }
                    if (position < 0) {
                        Toast.makeText(AddCategoryActivity.this, "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
                    } else {
                        adapterCategory.remove(adapterCategory.getItem(position));
                        //remove data array
                        // Data.listDataCategory.remove(adapterCategory.getItem(position));

                        adapterCategory.notifyDataSetChanged();


                        txtId.setText("");
                        txtName.setText("");
                        txtName.requestFocus();

                        Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messDelSuccess)), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorChoose)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
//                intent.putExtra("EDITCATEGORY", categorySelected);
//                setResult(500, intent);
//                finish();

                if (categorySelected != null) {
                    for (int i = 0 ; i< adapterCategory.getCount();i++){
                        Category ca = adapterCategory.getItem(i);
                        if (ca.getCategoryId().equals(categorySelected.getCategoryId())){
                            ca.setCategoryId(categorySelected.getCategoryId());
                            ca.setCategoryName(txtName.getText().toString().trim());

                            Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messEditSuccess)), Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    adapterCategory.notifyDataSetChanged();
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorChoose)), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }



    private void addControls() {
        txtId = (EditText) findViewById(R.id.edtAddIdCategory);
        txtName = (EditText) findViewById(R.id.edtAddNameCategory);
        btnAddCategory = (Button) findViewById(R.id.btnAddCategory);
        btnEditCategory = (Button) findViewById(R.id.btnEditCategory);
        btnDelCategory = (Button) findViewById(R.id.btnDelCategory);
        lvCategory = (ListView) findViewById(R.id.lvAddCategory);

        adapterCategory = new ArrayAdapter<Category>(AddCategoryActivity.this, android.R.layout.simple_list_item_1, Data.listDataCategory);
        lvCategory.setAdapter(adapterCategory);
    }
}

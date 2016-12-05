package com.nguyenthanhtu.doanmobile;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenthanhtu.Database;
import com.nguyenthanhtu.model.Category;


/**
 * Created by thanhtu on 10/12/16.
 */
public class AddCategoryActivity extends AppCompatActivity {
    final String DATABASE_NAME = "qlhh.sqlite";
    SQLiteDatabase database;
    EditText txtName;
    TextView txtId;
    Button btnAddCategory, btnDelCategory, btnEditCategory;
    ListView lvCategory;
    Category categorySelected;
    private ArrayAdapter<Category> adapterCategory;
    private String idCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();
    }

    @Override
    public boolean onSupportNavigateUp(){
        Data.idCategory.moveBack();
        finish();
        return true;
    }

    private void addEvents() {
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category ca = adapterCategory.getItem(position);
                categorySelected = ca;

                txtId.setText(ca.getCategoryId());
                txtName.setText(ca.getCategoryName());
                txtName.requestFocus();
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idCategory;
                String nameCategory = txtName.getText().toString();

                if (nameCategory.trim().length() > 0) {
                    database = Database.initDatabase(AddCategoryActivity.this, DATABASE_NAME);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("idCategory",id);
                    contentValues.put("name", nameCategory);
                    database.insert("Category", null, contentValues);

//                    Integer idCa = Data.idCategory.getNextId();
//                    ContentValues contentValues2 = new ContentValues();
//                    contentValues2.put("idCategory", id);
//                    long returnValue = database.update("IDManager", contentValues2, null, null);
//                    if (returnValue < 0){
//                        Toast.makeText(AddCategoryActivity.this, "that bai", Toast.LENGTH_SHORT).show();
//                    }

                    Integer idCa = Data.idCategory.getNextId();
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("idCategory" , idCa );
                    long returnValue = database.update("IDManager", contentValues2, null, null);
                    if (returnValue < 0){
                        Toast.makeText(AddCategoryActivity.this, "that bai", Toast.LENGTH_SHORT).show();
                    }

                    Category categoryNew = new Category(id, nameCategory);
                    Data.listDataCategory.add(categoryNew);
                    adapterCategory.notifyDataSetChanged();

                    //idCategory = Data.idCategory.nextId();


                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messAddSuccess)), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorSpace)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categorySelected != null){
                    AlertDialog.Builder  alert = new AlertDialog.Builder(AddCategoryActivity.this);
                    alert.setIcon(android.R.drawable.ic_delete);
                    alert.setTitle(AddCategoryActivity.this.getText(R.string.simple_removeTitle));
                    alert.setMessage(AddCategoryActivity.this.getText(R.string.simple_removeMessage));
                    alert.setPositiveButton(AddCategoryActivity.this.getText(R.string.simple_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //yes xoa
                            removeCategory(categorySelected);
                        }
                    });
                    alert.setNegativeButton(AddCategoryActivity.this.getText(R.string.simple_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorChoose)), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categorySelected != null) {
                    AlertDialog.Builder  alert = new AlertDialog.Builder(AddCategoryActivity.this);
                    alert.setIcon(android.R.drawable.ic_menu_edit);
                    alert.setTitle(AddCategoryActivity.this.getText(R.string.simple_editTitle));
                    alert.setMessage(AddCategoryActivity.this.getText(R.string.simple_editMessage));
                    alert.setPositiveButton(AddCategoryActivity.this.getText(R.string.simple_yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(Category item : Data.listDataCategory){
                                if (item.getCategoryId().equals(categorySelected.getCategoryId())){
                                    item.setCategoryName(txtName.getText().toString().trim());

                                    ContentValues contentValues = new ContentValues();
                                    contentValues.put("idCategory",item.getCategoryId());
                                    contentValues.put("name",txtName.getText().toString().trim());
                                    database = Database.initDatabase(AddCategoryActivity.this, DATABASE_NAME);
                                    database.update("Category", contentValues, "idCategory = ?", new String[] {item.getCategoryId() + ""} );

                                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messEditSuccess)), Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            adapterCategory.notifyDataSetChanged();

                            Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
                            intent.putExtra("EDITCATEGORY", categorySelected);
                            setResult(500, intent);
                            finish();
                        }
                    });
                    alert.setNegativeButton(AddCategoryActivity.this.getText(R.string.simple_no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //cancel clicked
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.show();
                }else{
                    Toast.makeText(AddCategoryActivity.this, String.valueOf(AddCategoryActivity.this.getString(R.string.simple_messErrorChoose)), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void removeCategory(Category category){
        int position = -1;
        for (int i = 0 ; i< Data.listDataCategory.size();i++){
            if (Data.listDataCategory.get(i).getCategoryId().equals(category.getCategoryId())) {
                position = i;
                break;
            }
        }
        if (position < 0) {
            Toast.makeText(AddCategoryActivity.this, "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
        } else {
            Data.idCategory.moveBack();

            //remove in array
            Data.listDataCategory.remove(Data.listDataCategory.get(position));
            adapterCategory.notifyDataSetChanged();

            //remove in sqlite
            database = Database.initDatabase(AddCategoryActivity.this,DATABASE_NAME);
            database.delete("Category","idCategory = ?", new String[] {category.getCategoryId() + ""});

            //reomve product idCategory equal
            database.delete("Product","idCategory = ?", new String[] {category.getCategoryId() + ""});

            Intent intent = new Intent(AddCategoryActivity.this, MainActivity.class);
            intent.putExtra("DELCATEGORY", category);
            setResult(400, intent);
            finish();
        }
    }


    private void addControls() {
        txtId = (TextView) findViewById(R.id.txtAddIdCategory);
        txtName = (EditText) findViewById(R.id.edtAddNameCategory);
        btnAddCategory = (Button) findViewById(R.id.btnAddCategory);
        btnEditCategory = (Button) findViewById(R.id.btnEditCategory);
        btnDelCategory = (Button) findViewById(R.id.btnDelCategory);
        lvCategory = (ListView) findViewById(R.id.lvAddCategory);

        adapterCategory = new ArrayAdapter<Category>(AddCategoryActivity.this, android.R.layout.simple_list_item_1, Data.listDataCategory);
        lvCategory.setAdapter(adapterCategory);


        this.idCategory = Data.idCategory.nextId();
        txtId.setText(this.idCategory);
    }
}

package com.nguyenthanhtu.doanmobile;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenthanhtu.Database;
import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/**
 * Created by thanhtu on 10/12/16.
 */
public class AddProductActivity extends AppCompatActivity  {
    final String DATABASE_NAME = "qlhh.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;
    TextView txtId;
    EditText edtAddName, edtAddPrice, edtAddVolumetric, edtAddProduction;
    Spinner spinnerCategory;
    ImageView imgAddProduct;
    Button btnAdd;
    private ArrayAdapter<Category> adapterCategory;
    private String idProduct;
    private Boolean checkChooseImage = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        imgAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence image[] = new CharSequence[] {AddProductActivity.this.getText(R.string.simple_photo),
                        AddProductActivity.this.getText(R.string.simple_camera)};

                AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                builder.setTitle(AddProductActivity.this.getText(R.string.simple_chooseImage));
                builder.setItems(image, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            choosePhoto();
                        }else if (which == 1){
                            takePicture();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        Data.idProduct.moveBack();
        finish();
        return true;
    }

    private void addProduct() {
        String idProduct = this.idProduct;
        String name = edtAddName.getText().toString();
        String production = edtAddProduction.getText().toString();

        if (name.trim().length() > 0 && production.trim().length() > 0 && edtAddPrice.getText().toString().trim().length() > 0
                && edtAddVolumetric.getText().toString().trim().length() > 0 && spinnerCategory.getSelectedItem() != null) {

            if (this.checkChooseImage == false){
                Toast.makeText(AddProductActivity.this,AddProductActivity.this.getText(R.string.simple_alertChooseImage), Toast.LENGTH_SHORT).show();
            }else{
                Double price = Double.parseDouble(edtAddPrice.getText().toString());
                Integer volumetric = Integer.parseInt(edtAddVolumetric.getText().toString());
                Category cate = (Category) spinnerCategory.getSelectedItem();
                byte[] image = convertImageViewToByte(imgAddProduct);

                ContentValues contentValues = new ContentValues();
                contentValues.put("idProduct",idProduct);
                contentValues.put("name",name);
                contentValues.put("image",image);
                contentValues.put("price",price);
                contentValues.put("volumetric",volumetric);
                contentValues.put("production",production);
                contentValues.put("idCategory",cate.getCategoryId());

                SQLiteDatabase database = Database.initDatabase(AddProductActivity.this, DATABASE_NAME);
                database.insert("Product",null, contentValues);

                Integer id = Data.idProduct.getNextId();
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("idProduct" , id );
                long returnValue = database.update("IDManager", contentValues2, null, null);
                if (returnValue < 0){
                    Toast.makeText(AddProductActivity.this, "that bai", Toast.LENGTH_SHORT).show();
                }

                Product product = new Product(idProduct,name,image,cate,price,volumetric,production);
                Intent intent = new Intent(AddProductActivity.this, MainActivity.class);
                intent.putExtra("ADD", product);
                setResult(100, intent);
                finish();
            }
        }else{
            Toast.makeText(AddProductActivity.this, String.valueOf(AddProductActivity.this.getString(R.string.simple_messErrorSpace)), Toast.LENGTH_SHORT).show();
        }

    }

    private void addControls() {
        txtId = (TextView) findViewById(R.id.txtAddId);
        edtAddName = (EditText) findViewById(R.id.edtAddName);
        edtAddPrice = (EditText) findViewById(R.id.edtAddPrice);
        edtAddVolumetric = (EditText) findViewById(R.id.edtAddVolumetric);
        edtAddProduction = (EditText) findViewById(R.id.edtAddProduction);
        spinnerCategory  = (Spinner) findViewById(R.id.spinnerCategory);
        imgAddProduct = (ImageView) findViewById(R.id.imgAddProduct);
        btnAdd = (Button) findViewById(R.id.btnAdd);

        //create id product
        this.idProduct = Data.idProduct.nextId();
        txtId.setText(this.idProduct);

        adapterCategory = new ArrayAdapter<Category>(AddProductActivity.this, android.R.layout.simple_list_item_1, Data.listDataCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerCategory.setAdapter(adapterCategory);
    }

    //-------
    private byte[] convertImageViewToByte(ImageView img){
        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    private void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, RESQUEST_TAKE_PHOTO);
    }

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == RESQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgAddProduct.setImageBitmap(bitmap);
                    checkChooseImage = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgAddProduct.setImageBitmap(bitmap);
                checkChooseImage = true;
            }
        }
    }
}

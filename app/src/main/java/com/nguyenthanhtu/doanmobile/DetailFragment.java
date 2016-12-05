package com.nguyenthanhtu.doanmobile;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by thanhtu on 11/2/16.
 */
public class DetailFragment extends Fragment {
    final String DATABASE_NAME = "qlhh.sqlite";
    final int RESQUEST_TAKE_PHOTO = 123;
    final int RESQUEST_CHOOSE_PHOTO = 321;

    ImageView imgProduct;
    TextView txtIdProduct;
    EditText edtName, edtPrice, edtVolumetric, edtProduction;
    Spinner spinnerEditCategory;
    Button btnEdit, btnDel;
    Product product;
    View fragment;
    private ArrayAdapter<Category> adapterCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_detail, container, true);
        addControls();
        addEvents();
        if (Data.listDataProduct.size() != 0){
            getData((Product)Data.listDataProduct.get(0));
        }else{
            imgProduct.setImageResource(R.drawable.hinh);
            txtIdProduct.setText("");
            edtPrice.setText("");
            edtName.setText("");
            edtVolumetric.setText("");
            edtProduction.setText("");
        }
        return fragment;
    }

    public void getData(Product pro) {
        if (pro != null) {
            product = pro;

            SQLiteDatabase database = Database.initDatabase(getActivity(),DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM Product Where idProduct = ?",new String[] {product.getProductId() + ""});
            cursor.moveToFirst();
            String id   = cursor.getString(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);
            Double price = cursor.getDouble(3);
            Integer volumetric = cursor.getInt(4);
            String production = cursor.getString(5);
            String idCategory = cursor.getString(6);
            cursor.close();

            Cursor cursor2 = database.rawQuery("SELECT * FROM Category Where idCategory = ?", new String[] {idCategory + ""});
            cursor2.moveToFirst();
            String idCategory2   = cursor2.getString(0);
            String nameCategory  = cursor2.getString(1);
            Category category = new Category(idCategory2, nameCategory);
            cursor2.close();

            //Bitmap image
            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);

            //Day len giao dien
            imgProduct.setImageBitmap(bitmap);
            txtIdProduct.setText(id);
            edtName.setText(name);
            edtPrice.setText(String.valueOf(price));
            edtVolumetric.setText(String.valueOf(volumetric));
            edtProduction.setText(production);
            spinnerEditCategory.setSelection(adapterCategory.getPosition(category));
        }
    }

    private void editProduct(){
        String id = product.getProductId();
        String name = edtName.getText().toString();
        Double price = Double.parseDouble(edtPrice.getText().toString());
        Integer volumetric = Integer.parseInt(edtVolumetric.getText().toString());
        String production = edtProduction.getText().toString();
        Category category = (Category) spinnerEditCategory.getSelectedItem();
        byte[] image = convertImageViewToByte(imgProduct);

        ContentValues contentValues = new ContentValues();
        contentValues.put("idProduct",id);
        contentValues.put("name",name);
        contentValues.put("image",image);
        contentValues.put("price",price);
        contentValues.put("volumetric",volumetric);
        contentValues.put("production",production);
        contentValues.put("idCategory",category.getCategoryId());

        SQLiteDatabase database = Database.initDatabase(getActivity(), DATABASE_NAME);
        database.update("Product",contentValues,"idProduct = ?",new String[] {id + ""} );

        Product productEdit = new Product(id,name,image,category,price,volumetric,production);

        ListviewFragment listviewFragment = (ListviewFragment) getFragmentManager().findFragmentById(R.id.frgListView);
        listviewFragment.editProduct(productEdit);
    }

    private void addEvents() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder  alert = new AlertDialog.Builder(getActivity());
                alert.setIcon(android.R.drawable.ic_menu_edit);
                alert.setTitle(getActivity().getText(R.string.simple_editTitle));
                alert.setMessage(getActivity().getText(R.string.simple_editMessage));
                alert.setPositiveButton(getActivity().getText(R.string.simple_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editProduct();
                    }
                });
                alert.setNegativeButton(getActivity().getText(R.string.simple_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder  alert = new AlertDialog.Builder(getActivity());
                alert.setIcon(android.R.drawable.ic_delete);
                alert.setTitle(getActivity().getText(R.string.simple_removeTitle));
                alert.setMessage(getActivity().getText(R.string.simple_removeMessage));
                alert.setPositiveButton(getActivity().getText(R.string.simple_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProduct(product.getProductId());
                    }
                });
                alert.setNegativeButton(getActivity().getText(R.string.simple_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = alert.create();
                dialog.show();
            }
        });

        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence image[] = new CharSequence[] {getActivity().getText(R.string.simple_photo),
                        getActivity().getText(R.string.simple_camera)};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getActivity().getText(R.string.simple_chooseImage));
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

    private void addControls() {
        imgProduct   = (ImageView) fragment.findViewById(R.id.imgEditProductFM);
        txtIdProduct = (TextView) fragment.findViewById(R.id.txtEditIdFM);
        edtPrice = (EditText) fragment.findViewById(R.id.edtEditPriceFM);
        edtName = (EditText) fragment.findViewById(R.id.edtEditNameFM);
        edtVolumetric = (EditText) fragment.findViewById(R.id.edtEditVolumetricFM);
        edtProduction = (EditText) fragment.findViewById(R.id.edtEditProductionFM);
        spinnerEditCategory = (Spinner) fragment.findViewById(R.id.spinnerEditCategoryFM);

        btnDel = (Button) fragment.findViewById(R.id.btnDelFM);
        btnEdit = (Button) fragment.findViewById(R.id.btnEditFM);

        adapterCategory = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_list_item_1, Data.listDataCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerEditCategory.setAdapter(adapterCategory);
    }

    private void deleteProduct(String idProduct) {
        SQLiteDatabase database = Database.initDatabase(getActivity(),DATABASE_NAME);
        database.delete("Product","idProduct = ?", new String[] {idProduct + ""});

        ListviewFragment listviewFragment = (ListviewFragment) getFragmentManager().findFragmentById(R.id.frgListView);
        listviewFragment.delProduct(idProduct);

        imgProduct.setImageResource(R.drawable.hinh);
        txtIdProduct.setText("");
        edtPrice.setText("");
        edtName.setText("");
        edtVolumetric.setText("");
        edtProduction.setText("");
    }

    public byte[] convertImageViewToByte(ImageView img){
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK){
            if (requestCode == RESQUEST_CHOOSE_PHOTO){
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgProduct.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if(requestCode == RESQUEST_TAKE_PHOTO) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgProduct.setImageBitmap(bitmap);
            }
        }
    }


}

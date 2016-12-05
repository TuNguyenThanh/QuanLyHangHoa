package com.nguyenthanhtu.doanmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nguyenthanhtu.Database;
import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

/**
 * Created by thanhtu on 11/2/16.
 */
public class ListviewFragment extends Fragment {
    final String DATABASE_NAME = "qlhh.sqlite";
    SQLiteDatabase database;
    ProductAdapter adapterProduct;
    ListView lvProduct;
    View fragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_listview,container, true);
        addControls();
        addEvents();
        return fragment;
    }

    private void addEvents() {
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DetailFragment detailFragment = (DetailFragment) getFragmentManager().findFragmentById(R.id.frgDetail);
                if (detailFragment == null || !detailFragment.isInLayout()){
                    //ko co
                    Intent intentDetail = new Intent(getActivity(), DetailProductActivity.class);
                    intentDetail.putExtra("EDIT", (Product)adapterProduct.getItem(position));
                    startActivityForResult(intentDetail, 20);
                }else{  //tim thay
                    detailFragment.getData((Product)adapterProduct.getItem(position));
                }
            }
        });
    }

    private void addControls() {
        lvProduct = (ListView) fragment.findViewById(R.id.lvProductFM);
        adapterProduct = new ProductAdapter(Data.listDataProduct, getActivity());
        lvProduct.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
    }

    private void getProduct() {
        //copy database
        database = Database.initDatabase(getActivity(),DATABASE_NAME);

        //get product
        Cursor cursor2 = database.rawQuery("SELECT * FROM Product", null);
        while (cursor2.moveToNext()) {
            String id   = cursor2.getString(0);
            String name = cursor2.getString(1);
            byte[] image = cursor2.getBlob(2);
            Double price = cursor2.getDouble(3);
            Integer volumetric = cursor2.getInt(4);
            String production = cursor2.getString(5);
            String idCategory = cursor2.getString(6);

            Cursor cursor = database.rawQuery("SELECT * FROM Category Where idCategory = '" + idCategory + "'" , null);
            cursor.moveToFirst();
            String idCategory2   = cursor.getString(0);
            String nameCategory = cursor.getString(1);
            Category category = new Category(idCategory2, nameCategory);
            cursor.close();

            Product product = new Product(id,name,image,category,price,volumetric,production);
            Data.listDataProduct.add(product);
        }
        cursor2.close();
    }

    public void editProduct(Product pro){
        for (Product item : Data.listDataProduct) {
            if (item.getProductId().equals(pro.getProductId())) {
                item.setProductName(pro.getProductName());
                item.setProductPrice(pro.getProductPrice());
                item.setProductVolumetric(pro.getProductVolumetric());
                item.setProduction(pro.getProduction());
                item.setProductImage(pro.getProductImage());
                item.setProductCategory(pro.getProductCategory());

                Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messEditSuccess)),
                        Toast.LENGTH_SHORT).show();
                break;
            }
        }
        adapterProduct.notifyDataSetChanged();
    }

    public void delProduct(String idProduct){
        int position = -1;
        for(int i = 0 ; i < Data.listDataProduct.size(); i++){
            if (((Product)(Data.listDataProduct.get(i))).getProductId().equals(idProduct)) {
                position = i; break;
            }
        }
        if (position < 0) {
            Toast.makeText(getActivity(), "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
        } else {
            Data.listDataProduct.remove(Data.listDataProduct.get(position));
            adapterProduct.notifyDataSetChanged();
            Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messDelSuccess)), Toast.LENGTH_SHORT).show();
        }
    }

    //nhan du lieu ve
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 10:
                if (data != null) {
                    Product product = (Product) data.getSerializableExtra("ADD");
                    if (resultCode == 100) {
                        adapterProduct.getListData().add(adapterProduct.getCount(),product);
                        adapterProduct.notifyDataSetChanged();
                        Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messAddSuccess)), Toast.LENGTH_SHORT).show();
                    }
                } //else
                //  Toast.makeText(MainActivity.this, "Có lỗi sảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                break;

            case 20:
                if (data != null) {
                    // Lấy ra dữ liệu được truyền về từ EditActivity
                    Product product = (Product) data.getSerializableExtra("EDIT");
                    if (resultCode == 200) {
                        for (Product item : Data.listDataProduct) {
                            if (item.getProductId().equals(product.getProductId())) {
                                item.setProductName(product.getProductName());
                                item.setProductPrice(product.getProductPrice());
                                item.setProductVolumetric(product.getProductVolumetric());
                                item.setProduction(product.getProduction());
                                item.setProductImage(product.getProductImage());
                                item.setProductCategory(product.getProductCategory());

                                Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messEditSuccess)), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        adapterProduct.notifyDataSetChanged();
                        break;

                    }else if (resultCode == 300) {
                        String ID = data.getExtras().getString("DEL");
                        int position = -1;
                        for(int i = 0 ; i < Data.listDataProduct.size(); i++){
                            if (((Product)(Data.listDataProduct.get(i))).getProductId().equals(ID)) {
                                position = i;
                                break;
                            }
                        }
                        if (position < 0) {
                            Toast.makeText(getActivity(), "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
                        } else {
                            Data.listDataProduct.remove(Data.listDataProduct.get(position));
                            adapterProduct.notifyDataSetChanged();
                            Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messDelSuccess)), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case 30:
                if (resultCode == 400){
                    Category category = (Category)data.getSerializableExtra("DELCATEGORY");
                    int position = -1;
                    for(int i = 0 ; i < Data.listDataProduct.size(); i++){
                        if (((Product)(Data.listDataProduct.get(i))).getProductCategory().equals(category)) {
                            position = i;
                            Data.listDataProduct.remove(Data.listDataProduct.get(i));
                            adapterProduct.notifyDataSetChanged();
                        }
                    }

                    if (position < 0) {
                        //Toast.makeText(MainActivity.this, "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messDelSuccess)), Toast.LENGTH_SHORT).show();
                    }
                }else if (resultCode == 500){
                    Category category = (Category) data.getSerializableExtra("EDITCATEGORY");

                    for (Product item : Data.listDataProduct) {
                        if (item.getProductCategory().getCategoryId().equals(category.getCategoryId())) {
                            item.setProductCategory(category);
                            Toast.makeText(getActivity(), String.valueOf(getActivity().getString(R.string.simple_messEditSuccess)), Toast.LENGTH_SHORT).show();
                            adapterProduct.notifyDataSetChanged();
                        }
                    }
                    break;
                }
                break;
        }
    }


    //option menu
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.option_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.catagory_menu:
                Intent intentCategory = new Intent(getActivity(), AddCategoryActivity.class);
                startActivityForResult(intentCategory, 30);
                break;
            case R.id.add_menu:
                Intent intent = new Intent(getActivity(), AddProductActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.logout_menu:
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(getActivity().getString(R.string.simple_logout));
                alert.setMessage(getActivity().getString(R.string.simple_logoutMessage));

                alert.setPositiveButton(getActivity().getString(R.string.simple_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                });

                alert.setNegativeButton(getActivity().getString(R.string.simple_no),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert.create().show();
                break;
            case R.id.about_menu:
                Intent intentAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

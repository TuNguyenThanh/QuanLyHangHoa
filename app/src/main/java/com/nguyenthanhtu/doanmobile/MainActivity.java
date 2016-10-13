package com.nguyenthanhtu.doanmobile;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ProductAdapter adapterProduct;
    ListView lvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getData();
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvProduct.setOnItemClickListener(this);
    }

    private void addControls() {
        lvProduct = (ListView) findViewById(R.id.lvProduct);

        adapterProduct = new ProductAdapter(Data.listDataProduct, MainActivity.this);
        lvProduct.setAdapter(adapterProduct);
        adapterProduct.notifyDataSetChanged();
    }

//    private void getData() {
//        Category cate1 = new Category("Loai1","Lon");
//        Category cate2 = new Category("Loai2","Chai");
//
//        Data.listDataCategory = new ArrayList<Category>();
//        Data.listDataCategory.add(cate1);
//        Data.listDataCategory.add(cate2);
//
//        Data.listDataProduct = new ArrayList<Product>();
//        Data.listDataProduct.add(new Product("SP1","Coca cola",R.drawable.cocacola, cate1, 10000, 330, "Pesico"));
//        Data.listDataProduct.add(new Product("SP2","Coca cola",R.drawable.cocacola, cate1, 20000, 330, "Pesico"));
//        Data.listDataProduct.add(new Product("SP3","Coca cola",R.drawable.cocacola, cate1, 30000, 330, "Pesico"));
//        Data.listDataProduct.add(new Product("SP4","Coca cola",R.drawable.cocacola, cate1, 40000, 330, "Pesico"));
//        Data.listDataProduct.add(new Product("SP5","Coca cola",R.drawable.cocacola, cate1, 50000, 330, "Pesico"));
//    }


    //nhan du lieu ve
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // Kiểm tra nếu requestCode = 10
            case 10:
                if (data != null) {
                    // Lấy ra giá trị truyền về từ AddProductActivity và gán vào đối tượng product
                    Product product = (Product) data.getSerializableExtra("ADD");
                    if (resultCode == 100) {
                        // gán thuộc tính code trong Product bằng số lượng của adapter + 1
                        adapterProduct.getListData().add(0,product);
                        adapterProduct.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, String.valueOf(MainActivity.this.getString(R.string.simple_messAddSuccess)), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, "Có lỗi sảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                break;

            // EditActivity với giá trị requestcode = 20
            case 20:
                if (data != null) {
                    // Lấy ra dữ liệu được truyền về từ EditActivity
                    Product select = (Product) data.getSerializableExtra("EDIT");
                    if (resultCode == 200) {
                        for (Product item : adapterProduct.getListData()) {
                            if (item.getProductId().equals(select.getProductId())) {
                                item.setProductName(select.getProductName());
                                item.setProductCost(select.getProductCost());
                                item.setProductVolumetric(select.getProductVolumetric());
                                item.setProduction(select.getProduction());
                                item.setProductImage(select.getProductImage());
                                item.setProductCategory(select.getProductCategory());

                                Toast.makeText(MainActivity.this, String.valueOf(MainActivity.this.getString(R.string.simple_messEditSuccess)), Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        adapterProduct.notifyDataSetChanged();
                        break;
                    }
                    else if (resultCode == 300) {
                        String ID = data.getExtras().getString("DEL");
                        int position = -1;
                        for (int i = 0; i < adapterProduct.getCount(); i++) {
                            if (((Product)(adapterProduct.getItem(i))).getProductId().equals(ID)) {
                                position = i;
                                break;
                            }
                        }
                        if (position < 0) {
                            Toast.makeText(MainActivity.this, "Không xác định được vị trí", Toast.LENGTH_SHORT).show();
                        } else {
                            adapterProduct.getListData().remove(position);
                            adapterProduct.notifyDataSetChanged();
                            Toast.makeText(MainActivity.this, String.valueOf(MainActivity.this.getString(R.string.simple_messDelSuccess)), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    //option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.catagory_menu:
                //Toast.makeText(this, "category tap",Toast.LENGTH_LONG).show();
                Intent intentCategory = new Intent(MainActivity.this, AddCategoryActivity.class);
                startActivityForResult(intentCategory, 30);
                break;
            case R.id.add_menu:
                //Toast.makeText(this, "add tap",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AddProductActivity.class);
                startActivityForResult(intent, 10);
                break;
            case R.id.logout_menu:
                //Toast.makeText(this, "logout",Toast.LENGTH_LONG).show();
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle(MainActivity.this.getString(R.string.simple_logout));
                alert.setMessage(MainActivity.this.getString(R.string.simple_logoutMessage));

                alert.setPositiveButton(MainActivity.this.getString(R.string.simple_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                alert.setNegativeButton(MainActivity.this.getString(R.string.simple_no),new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                alert.create().show();
                break;
            case R.id.about_menu:
                Intent intentAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Click item on listview
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "oke",Toast.LENGTH_LONG).show();
        Intent intentDetail = new Intent(MainActivity.this, DetailProductActivity.class);
        intentDetail.putExtra("EDIT", (Product)adapterProduct.getItem(position));
        startActivityForResult(intentDetail, 20);
    }
}

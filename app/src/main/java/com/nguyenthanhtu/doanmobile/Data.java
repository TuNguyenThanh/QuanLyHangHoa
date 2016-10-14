package com.nguyenthanhtu.doanmobile;

import android.widget.ArrayAdapter;

import com.nguyenthanhtu.model.Category;
import com.nguyenthanhtu.model.Product;

import java.util.ArrayList;

import util.IdGenerator;

/**
 * Created by thanhtu on 10/12/16.
 */
public class Data {
    public static ArrayList<Product>  listDataProduct;
    public static ArrayList<Category> listDataCategory;
    public static IdGenerator generator = new IdGenerator();
}

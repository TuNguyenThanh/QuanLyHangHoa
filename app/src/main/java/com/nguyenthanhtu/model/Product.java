package com.nguyenthanhtu.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by thanhtu on 10/12/16.
 */
public class Product implements Serializable {
    private String productId;
    private String productName;
    private byte[] productImage;
    private Category productCategory;
    private Double productPrice;
    private int productVolumetric;
    private String production;

    public Product() {
    }

    public Product(String productId, String productName, byte[] productImage, Category productCategory, Double productPrice, int productVolumetric, String production) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productVolumetric = productVolumetric;
        this.production = production;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductVolumetric() {
        return productVolumetric;
    }

    public void setProductVolumetric(int productVolumetric) {
        this.productVolumetric = productVolumetric;
    }

    public String getProduction() {
        return production;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (productVolumetric != product.productVolumetric) return false;
        if (productId != null ? !productId.equals(product.productId) : product.productId != null)
            return false;
        if (productName != null ? !productName.equals(product.productName) : product.productName != null)
            return false;
        if (!Arrays.equals(productImage, product.productImage)) return false;
        if (productCategory != null ? !productCategory.equals(product.productCategory) : product.productCategory != null)
            return false;
        if (productPrice != null ? !productPrice.equals(product.productPrice) : product.productPrice != null)
            return false;
        return production != null ? production.equals(product.production) : product.production == null;

    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(productImage);
        result = 31 * result + (productCategory != null ? productCategory.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + productVolumetric;
        result = 31 * result + (production != null ? production.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImage=" + productImage +
                ", productCategory=" + productCategory +
                ", productCost=" + productPrice +
                ", productVolumetric=" + productVolumetric +
                ", production='" + production + '\'' +
                '}';
    }
}

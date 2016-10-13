package com.nguyenthanhtu.model;

import java.io.Serializable;

/**
 * Created by thanhtu on 10/12/16.
 */
public class Product implements Serializable {
    private String productId;
    private String productName;
    private int productImage;
    private Category productCategory;

    private Double productCost;
    private int productVolumetric;
    private String production;

    public Product() {
    }

    public Product(String productId, String productName, int productImage, Category productCategory, Double productCost, int productVolumetric, String production) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productCategory = productCategory;
        this.productCost = productCost;
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

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Double getProductCost() {
        return productCost;
    }

    public void setProductCost(Double productCost) {
        this.productCost = productCost;
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
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productImage=" + productImage +
                ", productCategory=" + productCategory +
                ", productCost=" + productCost +
                ", productVolumetric=" + productVolumetric +
                ", production='" + production + '\'' +
                '}';
    }
}

package com.nguyenthanhtu.model;

import java.io.Serializable;

/**
 * Created by thanhtu on 10/12/16.
 */
public class Category implements Serializable {
    private String categoryId;
    private String categoryName;

    public Category(String categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (!categoryId.equals(category.categoryId)) return false;
        return categoryName.equals(category.categoryName);

    }

    @Override
    public int hashCode() {
        int result = categoryId.hashCode();
        result = 31 * result + categoryName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return  categoryId + " - " + categoryName;
    }
}

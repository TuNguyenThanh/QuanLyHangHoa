package com.nguyenthanhtu.doanmobile;

/**
 * Created by thanhtu on 10/23/16.
 */
public class IdAuto {
    private int nextId;
    private String key;

    public IdAuto() {
        this.nextId = 0;
        this.key = "";
    }

    public IdAuto(int nextId, String key) {
        this.nextId = nextId;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public String nextId() {
        String id = "" + this.nextId++;

        for (int i = 0; i <= 6 - id.length(); ++i) {
            id = "0" + id;
        }

        return this.key + id;
    }

    public boolean moveBack() {
        if (this.nextId == 0) {
            return false;
        } else {
            --this.nextId;
            return true;
        }
    }

}

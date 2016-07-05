package com.pictureworks.www.retrofitwithokhttp.bean;

import java.util.List;

/**
 * Created by pengwu on 16/7/4.
 */
public class CartInfo {
    List<CartItem> items;
    int preferentialPrice;
    int totalPrice;
    int totalCount;

    public CartInfo(List<CartItem> items, int preferentialPrice, int totalPrice, int totalCount) {
        this.items = items;
        this.preferentialPrice = preferentialPrice;
        this.totalPrice = totalPrice;
        this.totalCount = totalCount;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public int getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(int preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}

package com.pictureworks.www.retrofitwithokhttp.bean;

import java.util.List;

/**
 * Created by pengwu on 16/7/4.
 */
public class CartItem {
    String goodsKey;
    int qty;
    int price;
    String _id;
    List<EmbedPhoto> embedPhotos;

    public CartItem(String goodsKey, int qty, int price, String _id, List<EmbedPhoto> embedPhotos) {
        this.goodsKey = goodsKey;
        this.qty = qty;
        this.price = price;
        this._id = _id;
        this.embedPhotos = embedPhotos;
    }

    public String getGoodsKey() {
        return goodsKey;
    }

    public void setGoodsKey(String goodsKey) {
        this.goodsKey = goodsKey;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<EmbedPhoto> getEmbedPhotos() {
        return embedPhotos;
    }

    public void setEmbedPhotos(List<EmbedPhoto> embedPhotos) {
        this.embedPhotos = embedPhotos;
    }
}

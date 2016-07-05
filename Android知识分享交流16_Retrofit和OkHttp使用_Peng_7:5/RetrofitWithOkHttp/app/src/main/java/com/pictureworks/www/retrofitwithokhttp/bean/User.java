package com.pictureworks.www.retrofitwithokhttp.bean;

import java.util.List;

/**
 * Created by pengwu on 16/7/4.
 */
public class User {

    String _id;
    String userName;
    String email;
    String mobile;
    String name;
    String gender;
    String birthday;
    CartInfo cart;
    List<Address> addresses;
    List<FavouriteLocation> favoriteLocationIds;
    String userPP;
    List<CustomerId> customerIds;
    List<HidenPP> hiddenPPList;
    List<FavoritePhotos> favoritePhotos;
    List<LikePhoto> likePhotos;

    public User(String _id, String userName, String email, String mobile, String name, String gender, String birthday, CartInfo cart, List<Address> addresses, List<FavouriteLocation> favoriteLocationIds, String userPP, List<CustomerId> customerIds, List<HidenPP> hiddenPPList, List<FavoritePhotos> favoritePhotos, List<LikePhoto> likePhotos) {
        this._id = _id;
        this.userName = userName;
        this.email = email;
        this.mobile = mobile;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.cart = cart;
        this.addresses = addresses;
        this.favoriteLocationIds = favoriteLocationIds;
        this.userPP = userPP;
        this.customerIds = customerIds;
        this.hiddenPPList = hiddenPPList;
        this.favoritePhotos = favoritePhotos;
        this.likePhotos = likePhotos;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public CartInfo getCart() {
        return cart;
    }

    public void setCart(CartInfo cart) {
        this.cart = cart;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<FavouriteLocation> getFavoriteLocationIds() {
        return favoriteLocationIds;
    }

    public void setFavoriteLocationIds(List<FavouriteLocation> favoriteLocationIds) {
        this.favoriteLocationIds = favoriteLocationIds;
    }

    public String getUserPP() {
        return userPP;
    }

    public void setUserPP(String userPP) {
        this.userPP = userPP;
    }

    public List<CustomerId> getCustomerIds() {
        return customerIds;
    }

    public void setCustomerIds(List<CustomerId> customerIds) {
        this.customerIds = customerIds;
    }

    public List<HidenPP> getHiddenPPList() {
        return hiddenPPList;
    }

    public void setHiddenPPList(List<HidenPP> hiddenPPList) {
        this.hiddenPPList = hiddenPPList;
    }

    public List<FavoritePhotos> getFavoritePhotos() {
        return favoritePhotos;
    }

    public void setFavoritePhotos(List<FavoritePhotos> favoritePhotos) {
        this.favoritePhotos = favoritePhotos;
    }

    public List<LikePhoto> getLikePhotos() {
        return likePhotos;
    }

    public void setLikePhotos(List<LikePhoto> likePhotos) {
        this.likePhotos = likePhotos;
    }
}

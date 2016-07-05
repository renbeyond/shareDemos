package com.pictureworks.www.retrofitwithokhttp.bean;

import java.util.List;

/**
 * Created by pengwu on 16/7/4.
 */
public class EmbedPhoto {

    String photoIdS;
    String photoIp;
    String _id;
    List<PPCode> ppCodes;

    public EmbedPhoto(String photoIdS, String photoIp, String _id, List<PPCode> ppCodes) {
        this.photoIdS = photoIdS;
        this.photoIp = photoIp;
        this._id = _id;
        this.ppCodes = ppCodes;
    }

    public String getPhotoIdS() {
        return photoIdS;
    }

    public void setPhotoIdS(String photoIdS) {
        this.photoIdS = photoIdS;
    }

    public String getPhotoIp() {
        return photoIp;
    }

    public void setPhotoIp(String photoIp) {
        this.photoIp = photoIp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<PPCode> getPpCodes() {
        return ppCodes;
    }

    public void setPpCodes(List<PPCode> ppCodes) {
        this.ppCodes = ppCodes;
    }
}

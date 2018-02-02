package com.example.ye.chameleo.Model;

import com.orm.SugarRecord;

/**
 * Created by Yesaya on 1/24/2018.
 */

public class Stickers extends SugarRecord<Stickers> {

    public int stickerID;
    public String stickerName, stickerPhoto, stickerDetails;

    public Stickers(int stickerID, String stickerName, String stickerPhoto, String stickerDetails) {
        this.stickerID = stickerID;
        this.stickerName = stickerName;
        this.stickerPhoto = stickerPhoto;
        this.stickerDetails = stickerDetails;
    }

    public Stickers() {
    }

    public String getStickerDetails() {
        return stickerDetails;
    }

    public void setStickerDetails(String stickerDetails) {
        this.stickerDetails = stickerDetails;
    }

    public int getStickerID() {
        return stickerID;
    }

    public void setStickerID(int stickerID) {
        this.stickerID = stickerID;
    }

    public String getStickerName() {
        return stickerName;
    }

    public void setStickerName(String stickerName) {
        this.stickerName = stickerName;
    }

    public String getStickerPhoto() {
        return stickerPhoto;
    }

    public void setStickerPhoto(String stickerPhoto) {
        this.stickerPhoto = stickerPhoto;
    }

}

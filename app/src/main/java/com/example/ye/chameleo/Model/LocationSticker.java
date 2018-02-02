package com.example.ye.chameleo.Model;

import com.orm.SugarRecord;

/**
 * Created by Yesaya on 1/26/2018.
 */

public class LocationSticker  extends SugarRecord<LocationSticker>{

    int locId;
    String locName, locSticker, locType;

    public LocationSticker(int locId, String locName, String locSticker, String locType) {
        this.locId = locId;
        this.locName = locName;
        this.locSticker = locSticker;
        this.locType = locType;
    }

    public LocationSticker() {
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getLocSticker() {
        return locSticker;
    }

    public void setLocSticker(String locSticker) {
        this.locSticker = locSticker;
    }
}

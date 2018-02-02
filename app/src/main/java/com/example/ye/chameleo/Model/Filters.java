package com.example.ye.chameleo.Model;

import com.orm.SugarRecord;

/**
 * Created by YE on 16/01/2018.
 */

public class Filters extends SugarRecord<Filters> {

    int filterID;
    String filtername;
    String filterPhoto;

    public Filters(int filterID, String filtername, String filterPhoto) {
        this.filterID = filterID;
        this.filtername = filtername;
        this.filterPhoto = filterPhoto;
    }

    public Filters() {
    }

    public String getFiltername() {
        return filtername;
    }

    public void setFiltername(String filtername) {
        this.filtername = filtername;
    }

    public int getFilterID() {
        return filterID;
    }

    public void setFilterID(int filterID) {
        this.filterID = filterID;
    }

    public String getFilterPhoto() {
        return filterPhoto;
    }

    public void setFilterPhoto(String filterPhoto) {
        this.filterPhoto = filterPhoto;
    }


}



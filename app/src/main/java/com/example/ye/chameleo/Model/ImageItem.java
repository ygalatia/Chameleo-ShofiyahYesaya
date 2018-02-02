package com.example.ye.chameleo.Model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by YE on 02/01/2018.
 */

public class ImageItem implements Comparable<ImageItem> {

    String DATA;
    String DISPLAY_NAME;
    String CREATED;

    public ImageItem(String DATA, String DISPLAY_NAME,String CREATED){
        this.DATA=DATA;
        this.DISPLAY_NAME=DISPLAY_NAME;
        this.CREATED=CREATED;
    };
    public ImageItem(){

    }


    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getDISPLAY_NAME() {
        return DISPLAY_NAME;
    }

    public void setDISPLAY_NAME(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;
    }

    public String getCREATED() {
        return CREATED;
    }

    public void setCREATED(String CREATED) {
        this.CREATED = CREATED;
    }

    @Override
    public int compareTo(@NonNull ImageItem imageItem) {
        Date newDate = new Date();
        Date inputDate = new Date();
        try {
            newDate = formatDateTime(imageItem.getCREATED(), "HH:mm yyyy-MM-DD", "yyyy-MM-DD HH:mm");
            inputDate = formatDateTime(getCREATED(), "HH:mm yyyy-MM-DD", "yyyy-MM-DD HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return inputDate.compareTo(newDate);
    }

    public Date formatDateTime(String date, String fromFormat, String toFormat) throws ParseException {
        Date d = null;
        try {
            d = new SimpleDateFormat(fromFormat, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(toFormat, Locale.US).parse(String.valueOf(d));
    }

}

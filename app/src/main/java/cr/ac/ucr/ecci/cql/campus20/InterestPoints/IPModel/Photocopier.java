package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Photocopier extends Place {


    public Photocopier() {
        super();
        super.setType(TYPE_PHOTOCOPIER);
    }

    protected Photocopier(Parcel in) {
        super(in);
        super.setType(TYPE_PHOTOCOPIER);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_PHOTOCOPIER, comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_PHOTOCOPIER);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public String getPhotocopierName() {
        return super.getName();
    }

    public void setPhotocopierName(String name) { super.setName(name); }



}
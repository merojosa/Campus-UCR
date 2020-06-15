package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Library extends Place {


    public Library() {
        super();
        super.setType(TYPE_LIBRARY);
    }


    protected Library(Parcel in) {
        super(in);
        super.setType(TYPE_LIBRARY);
    }

    public Library(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_LIBRARY, comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Library(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_LIBRARY);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }


    public String getLibraryName() {
        return super.getName();
    }

    public void setLibraryName(String name) {
        super.setName(name);
    }


}
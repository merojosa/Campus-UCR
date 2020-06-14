package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Library extends Place {


    public Library() {
        super();
        super.setType("library");
    }


    protected Library(Parcel in) {
        super(in);
        super.setType("library");
    }

    public Library(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, "library");
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
package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Office extends Place implements Parcelable {



    public Office() {
        super();
        super.setType(TYPE_OFFICE);
    }


    protected Office(Parcel in) {
        super(in);
        super.setType(TYPE_OFFICE);
    }

    public Office(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_OFFICE);
        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Office(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_OFFICE);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Office> CREATOR = new Creator<Office>() {
        @Override
        public Office createFromParcel(Parcel in) {
            return new Office(in);
        }

        @Override
        public Office[] newArray(int size) {
            return new Office[size];
        }
    };

    public String getOfficeName() {
        return super.getName();
    }

    public void setOfficeName(String name) {
        super.setName(name);
    }


}

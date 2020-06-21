package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Photocopier extends Place implements Parcelable {


    public Photocopier() {
        super();
        super.setType(TYPE_PHOTOCOPIER);
    }

    protected Photocopier(Parcel in) {
        super(in);
        super.setType(TYPE_PHOTOCOPIER);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_PHOTOCOPIER);
        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_PHOTOCOPIER);
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

    public static final Creator<Photocopier> CREATOR = new Creator<Photocopier>() {
        @Override
        public Photocopier createFromParcel(Parcel in) {
            return new Photocopier(in);
        }

        @Override
        public Photocopier[] newArray(int size) {
            return new Photocopier[size];
        }
    };

    public String getPhotocopierName() {
        return super.getName();
    }

    public void setPhotocopierName(String name) { super.setName(name); }



}
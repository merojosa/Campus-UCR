package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Library extends Place implements Parcelable {

    public String horario;

    public Library() {
        super();
        super.setType(TYPE_LIBRARY);
    }


    protected Library(Parcel in) {
        super(in);
        horario =  in.readString();
        super.setType(TYPE_LIBRARY);
    }

/*    public Library(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_LIBRARY);
        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }*/

    public Library(int id, String name, String description, int image, double latitude, double longitude, String horary, boolean wifi) {
        super(id, name, description, image, TYPE_LIBRARY);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        this.horario = horary;
    }


    public static final Creator<Library> CREATOR = new Creator<Library>() {
        public Library createFromParcel(Parcel in) {
            return new Library(in);
        }

        public Library[] newArray(int size) {
            return new Library[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(horario);
    }
}
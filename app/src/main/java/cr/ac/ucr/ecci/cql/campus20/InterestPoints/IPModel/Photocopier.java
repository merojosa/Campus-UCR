package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Photocopier extends Place implements Parcelable {

    public String horario;
    public String precioHoja;

    public Photocopier() {
        super();
        super.setType(TYPE_PHOTOCOPIER);
    }

    protected Photocopier(Parcel in) {
        super(in);
        horario = in.readString();
        precioHoja = in.readString();
        super.setType(TYPE_PHOTOCOPIER);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_PHOTOCOPIER);
        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Photocopier(int id, String name, String description, int image, double latitude, double longitude, boolean wifi, String horary, String pricePaper) {
        super(id, name, description, image, TYPE_PHOTOCOPIER);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        horario = horary;
        precioHoja = pricePaper;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(horario);
        dest.writeString(precioHoja);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photocopier> CREATOR = new Creator<Photocopier>() {
        public Photocopier createFromParcel(Parcel in) {
            return new Photocopier(in);
        }

        public Photocopier[] newArray(int size) {
            return new Photocopier[size];
        }
    };

}
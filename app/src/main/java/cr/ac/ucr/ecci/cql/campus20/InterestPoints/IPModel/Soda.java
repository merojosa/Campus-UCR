package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Soda extends Place implements Parcelable {

    private String horario;
    private String oferta;
    private Boolean express;

    public Soda() {
        super();
        super.setType(TYPE_SODA);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_SODA);

        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_SODA);

        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude, Boolean wifi, String horario, String oferta, boolean express) {
        super(id, name, description, image, TYPE_SODA);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        this.horario = horario;
        this.oferta = oferta;
        this.express = express;
    }


    public static final Creator<Soda> CREATOR = new Creator<Soda>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public Soda createFromParcel(Parcel in) {
            return new Soda(in);
        }

        public Soda[] newArray(int size) {
            return new Soda[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Soda(Parcel in) {
        super(in);
        horario = in.readString();
        oferta = in.readString();
        express = in.readBoolean();
        super.setType(TYPE_SODA);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(horario);
        dest.writeString(oferta);
        dest.writeBoolean(express);
    }
}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Coffee extends Place implements Parcelable {

    public String horario;
    public String oferta;

    public Coffee() {
        super();
        super.setType(TYPE_COFFEE);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Coffee(Parcel in) {
        super(in);
        super.setType(TYPE_COFFEE);
    }

    public Coffee(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_COFFEE, comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Coffee(int id, String name, String description, int image, double latitude, double longitude, String horario, boolean wifi, String oferta) {
        super(id, name, description, image, TYPE_COFFEE);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        setHorario(horario);
        setOferta(oferta);
    }

    public String getCoffeName() {
        return super.getName();
    }

    public void setCoffeName(String name) {
        super.setName(name);
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public static final Creator<Soda> CREATOR = new Creator<Soda>() {
        @Override
        public Soda createFromParcel(Parcel in) {
            return new Soda(in);
        }

        @Override
        public Soda[] newArray(int size) {
            return new Soda[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horario);
        dest.writeByte((byte) (wifi == null ? 0 : wifi ? 1 : 2));
        dest.writeString(oferta);
    }
}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Coffee extends Place {

    public String horario;
    public String oferta;

    public Coffee() {
        super();
        super.setType(TYPE_COFFEE);
    }

    public Coffee(Parcel in) {
        super(in);
        horario = in.readString();
        oferta = in.readString();
        super.setType(TYPE_COFFEE);
    }

    public Coffee(int id, String name, String description, int image, double latitude, double longitude, String horario, Boolean wifi, String oferta) {
        super(id, name , description, image, TYPE_COFFEE);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        this.horario = horario;
        this.oferta = oferta;
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

    public static final Parcelable.Creator<Coffee> CREATOR = new Parcelable.Creator<Coffee>() {
        public Coffee createFromParcel(Parcel in) {
            return new Coffee(in);
        }

        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(horario);
        out.writeString(oferta);
    }
}

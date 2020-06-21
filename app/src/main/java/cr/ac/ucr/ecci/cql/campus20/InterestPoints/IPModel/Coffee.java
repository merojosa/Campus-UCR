package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class Coffee extends Place implements Parcelable{

    public String horario;
    public String oferta;

    public Coffee() { }

    public Coffee(int id, String name, String description, int image, double latitude, double longitude, String horario, Boolean wifi, String oferta) {
        super(id, name, description, image, TYPE_COFFEE);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        setHorario(horario);
        setOferta(oferta);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Coffee(Parcel in) {
        super.id = in.readInt();
        super.wifi = in.readBoolean();
    }

    public static final Creator<Coffee> CREATOR = new Creator<Coffee>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public Coffee createFromParcel(Parcel in) {
            return new Coffee(in);
        }

        @Override
        public Coffee[] newArray(int size) {
            return new Coffee[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horario);
        dest.writeString(oferta);
    }
}

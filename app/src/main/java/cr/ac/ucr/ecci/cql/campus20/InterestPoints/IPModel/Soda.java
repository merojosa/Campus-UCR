package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

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
        super(id, name, description, image, TYPE_SODA, comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_SODA);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude, String horario, boolean wifi, String oferta, boolean express) {
        super(id, name, description, image, TYPE_SODA);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        setHorario(horario);
        setWifi(wifi);
        setOferta(oferta);
        setExpress(express);
    }

    public String getSodaName() {
        return super.getName();
    }

    public void setSodaName(String name) {
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

    public Boolean getExpress() {
        return express;
    }

    public void setExpress(Boolean express) {
        this.express = express;
    }

    // --------------- Parcelable methods ---------------

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

    public Soda(Parcel in) {
        super(in);
        super.setType(TYPE_SODA);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horario);
        dest.writeByte((byte) (wifi == null ? 0 : wifi ? 1 : 2));
        dest.writeString(oferta);
        dest.writeByte((byte) (express == null ? 0 : express ? 1 : 2));
    }
}

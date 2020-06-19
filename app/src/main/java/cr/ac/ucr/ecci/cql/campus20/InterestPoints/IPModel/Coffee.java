package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

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
}

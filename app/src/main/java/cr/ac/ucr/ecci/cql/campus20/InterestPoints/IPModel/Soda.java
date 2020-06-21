package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

import java.util.ArrayList;

public class Soda extends Place{

    private String horario;
    private String ofertas;
    private boolean express;

    public Soda() {
        super();
        super.setType(TYPE_SODA);
    }

    public Soda(Parcel in) {
        super(in);
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

    public Soda(int id, String name, String description, int image, double latitude, double longitude, String horario, boolean wifi, String ofertas, boolean express) {
        super(id, name, description, image, TYPE_SODA, wifi);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        this.setHorario(horario);
        this.setOfertas(ofertas);
        this.setExpress(express);
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

    public String getOfertas() {
        return ofertas;
    }

    public void setOfertas(String ofertas) {
        this.ofertas = ofertas;
    }

    public boolean isExpress() {
        return express;
    }

    public void setExpress(boolean express) {
        this.express = express;
    }
}

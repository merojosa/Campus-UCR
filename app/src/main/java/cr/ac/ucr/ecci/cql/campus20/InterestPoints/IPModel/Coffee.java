package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

import java.util.ArrayList;

public class Coffee extends Place {

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
        super(id, name, description, image, TYPE_COFFEE, horario, wifi, oferta);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public String getCoffeName() {
        return super.getName();
    }

    public void setCoffeName(String name) {
        super.setName(name);
    }
}

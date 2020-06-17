package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

import java.util.ArrayList;

public class Soda extends Place{

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

    public String getSodaName() {
        return super.getName();
    }

    public void setSodaName(String name) {
        super.setName(name);
    }

}

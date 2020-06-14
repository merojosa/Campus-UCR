package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

public class Coffe extends Place {

    public Coffe() {
        super();
        super.setType("coffe");
    }

    public Coffe(Parcel in) {
        super(in);
        super.setType("coffe");
    }

    public Coffe(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, "coffe");
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

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

public class Soda extends Place{

    public Soda() {
        super();
        super.setType("coffe");
    }

    public Soda(Parcel in) {
        super(in);
        super.setType("coffe");
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, "soda");
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

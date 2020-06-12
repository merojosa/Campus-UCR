package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

public class Coffe extends Place {

    public Coffe() {
        super();
        super.setType("coffe");
    }

    public Coffe(int id, String name, String description, int rating, int floor, int image) {
        super(id, name, description, rating, floor, image);
        super.setType("coffe");
    }

    public Coffe(Parcel in) {
        super(in);
        super.setType("coffe");
    }

    public Coffe(int id, String name, String description, int image) {
        super(id, name, description, image);
        super.setType("coffe");
    }
}

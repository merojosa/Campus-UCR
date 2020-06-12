package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;

public class Faculty extends Place {

    public Faculty() {
        super();
        super.setType("faculty");
    }

    public Faculty(int id, String name, String description, int image) {
        super(id, name, description, image);
        super.setType("faculty");
    }

    public Faculty(int id, String name, String description, int rating, int floor, int image) {
        super(id, name, description, rating, floor, image);
        super.setType("faculty");
    }

    public Faculty(Parcel in) {
        super(in);
        super.setType("faculty");
    }
}

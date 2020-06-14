package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Faculty extends Place {

    public Faculty() {
        super();
        super.setType("faculty");
    }

    // Constructor used in the Deployment Script
    public Faculty(int id, String name, String description, int image, String type) {
        super(id, name, description, image, type);
    }
}

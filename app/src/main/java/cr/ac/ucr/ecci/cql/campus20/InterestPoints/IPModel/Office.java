package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Office extends Place {



    public Office() {
        super();
        super.setType("office");
    }


    protected Office(Parcel in) {
        super(in);
        super.setType("office");
    }

    public Office(int id, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, "office");
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public String getOfficeName() {
        return super.getName();
    }

    public void setOfficeName(String name) {
        super.setName(name);
    }


}

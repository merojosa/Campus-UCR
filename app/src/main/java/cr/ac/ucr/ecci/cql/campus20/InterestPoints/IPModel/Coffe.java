package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Coffe extends Place implements Parcelable {

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

    public static final Creator<Coffe> CREATOR = new Creator<Coffe>() {
        @Override
        public Coffe createFromParcel(Parcel in) {
            return new Coffe(in);
        }

        @Override
        public Coffe[] newArray(int size) {
            return new Coffe[size];
        }
    };

    public String getCoffeName() {
        return super.getName();
    }

    public void setCoffeName(String name) {
        super.setName(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

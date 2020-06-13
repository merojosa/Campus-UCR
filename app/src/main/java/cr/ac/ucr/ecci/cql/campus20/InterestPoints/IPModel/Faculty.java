package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Faculty extends Place implements Parcelable {

    public Faculty() {
        super();
        super.setType("faculty");
    }

    // Constructor used in the Deployment Script
    public Faculty(int id, String name, String description, int image, String type) {
        super(id, name, description, image, type);
    }

    protected Faculty(Parcel in) {
    }

    public static final Creator<Faculty> CREATOR = new Creator<Faculty>() {
        @Override
        public Faculty createFromParcel(Parcel in) {
            return new Faculty(in);
        }

        @Override
        public Faculty[] newArray(int size) {
            return new Faculty[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Faculty extends Place implements Parcelable {

    public Faculty() {
        super();
        super.setType(TYPE_FACULTY);
    }

    // Constructor used in the Deployment Script
    public Faculty(int id, String name, String description, int image, String type, ArrayList<Comment> comments) {
        super(id, name, description, image, type);
        super.setComments(comments);
    }

    public Faculty(int id, String name, String description, int image, String type) {
        super(id, name, description, image, type);
    }

    protected Faculty(Parcel in) {
        super(in);
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
}

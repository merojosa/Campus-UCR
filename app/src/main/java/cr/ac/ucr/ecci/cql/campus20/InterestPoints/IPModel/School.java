package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class School extends Place {

    private int id_faculty_fk;
    private int id_place_fk;

    public School() {
        super();
        super.setType(TYPE_SCHOOL);
    }

    public School(int id, int id_faculty_fk, int id_place_fk, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_SCHOOL);

        super.setComments(comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        this.id_faculty_fk = id_faculty_fk;
        this.id_place_fk = id_place_fk;
    }

    public School(int id, int id_faculty_fk, int id_place_fk, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, TYPE_SCHOOL);

        super.setLatitude(latitude);
        super.setLongitude(longitude);
        this.id_faculty_fk = id_faculty_fk;
        this.id_place_fk = id_place_fk;
    }

    protected School(Parcel in) {
        super(in);
        //super.id = in.readInt();
        id_faculty_fk = in.readInt();
        id_place_fk = in.readInt();
        super.setType(TYPE_SCHOOL);

        //super.name = in.readString();
        //super.description = in.readString();
        //super.image = in.readInt();
        //super.latitude = in.readDouble();
        //super.longitude = in.readDouble();
        //super.comments = in.readArrayList(Comment.class.getClassLoader());
    }

    public static final Parcelable.Creator<School> CREATOR = new Parcelable.Creator<School>() {
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        public School[] newArray(int size) {
            return new School[size];
        }
    };

    public int getId_faculty_fk() {
        return id_faculty_fk;
    }

    public void setId_faculty_fk(int id_faculty_fk) {
        this.id_faculty_fk = id_faculty_fk;
    }

    public int getId_place_fk() {
        return id_place_fk;
    }

    public void setId_place_fk(int id_place_fk) {
        this.id_place_fk = id_place_fk;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(id_faculty_fk);
        dest.writeInt(id_place_fk);
    }
}

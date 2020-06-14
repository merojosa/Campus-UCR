package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class School extends Place {

    private int id_faculty_fk;
    private int id_place_fk;

    public School() { }

    public School(int id, int id_faculty_fk, int id_place_fk, String name, String description, int image, double latitude, double longitude) {
        super(id, name, description, image, "school");
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        this.id_faculty_fk = id_faculty_fk;
        this.id_place_fk = id_place_fk;
    }

    protected School(Parcel in) {
        super.id = in.readInt();
        id_faculty_fk = in.readInt();
        id_place_fk = in.readInt();
        super.name = in.readString();
        super.description = in.readString();
        super.image = in.readInt();
        super.latitude = in.readDouble();
        super.longitude = in.readDouble();
    }

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
}

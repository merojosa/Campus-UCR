package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class School extends Place implements Parcelable {

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

    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_faculty_fk);
        dest.writeInt(id_place_fk);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(image);
        dest.writeString(type);
        dest.writeInt(rating);
        dest.writeInt(floor);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}

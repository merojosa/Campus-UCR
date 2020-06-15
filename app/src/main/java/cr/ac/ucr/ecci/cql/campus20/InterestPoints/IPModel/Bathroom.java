package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.RequiresApi;

public class Bathroom extends Place {

    private int id_school_fk;
    private int id_place_fk;
    private boolean wifi;

    public Bathroom() { }

    public Bathroom(int id, int id_school_fk, int id_place_fk, String name, String description, int image, int rating, int floor, boolean wifi) {
        super(id, name, description, "bathroom", rating, floor);
        this.id_school_fk = id_school_fk;
        this.id_place_fk = id_place_fk;
        this.wifi = wifi;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Bathroom(Parcel in) {
        super.id = in.readInt();
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
        super.name = in.readString();
        super.description = in.readString();
        super.rating = in.readInt();
        super.floor = in.readInt();
        wifi = in.readBoolean();
    }

    public int getId_faculty_fk() {
        return id_school_fk;
    }

    public void setId_faculty_fk(int id_faculty_fk) {
        this.id_school_fk = id_faculty_fk;
    }

    public int getId_place_fk() {
        return id_place_fk;
    }

    public void setId_place_fk(int id_place_fk) {
        this.id_place_fk = id_place_fk;
    }

    public boolean get_wifi(){
        return this.wifi;
    }

    public void set_wifi(boolean wifi){this.wifi = wifi;}
}

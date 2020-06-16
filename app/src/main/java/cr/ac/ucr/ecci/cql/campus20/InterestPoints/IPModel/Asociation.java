package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.RequiresApi;

public class Asociation extends Place {
    private int id_school_fk;
    private int id_place_fk;
    private int capacity;
    private boolean wifi;
    private boolean computers;
    private boolean extintor;

    public Asociation() { }

//    public Asociation(int id, int id_school_fk, int id_place_fk, String name, String description,
//                      int floor, int capacity, boolean wifi, boolean computers, boolean extintor) {
//        super(id, name, description, TYPE_ASOCIATION, floor);
//        this.id_school_fk = id_school_fk;
//        this.id_place_fk = id_place_fk;
//        this.capacity = capacity;
//        this.wifi = wifi;
//        this.computers = computers;
//        this.extintor = extintor;
//    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Asociation(Parcel in) {
        super.id = in.readInt();
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
        super.name = in.readString();
        super.setType(TYPE_ASOCIATION);
        super.description = in.readString();
        super.rating = in.readInt();
        super.floor = in.readInt();
        capacity = in.readInt();
        wifi = in.readBoolean();
        computers = in.readBoolean();
        extintor = in.readBoolean();
    }

    public int getId_school_fk() {
        return id_school_fk;
    }

    public void setId_school_fk(int id_faculty_fk) {
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean getComputers() {
        return computers;
    }

    public void setComputers(boolean computers) {
        this.computers = computers;
    }

    public boolean getExtintor() {
        return extintor;
    }

    public void setExtintor(boolean extintor) {
        this.extintor = extintor;
    }
}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;

import androidx.annotation.RequiresApi;

public class Laboratory extends Place {
    private int id_school_fk;
    private int id_place_fk;
    private int capacity;
    private boolean wifi;
    private boolean computers;
    private boolean projector;
    private boolean extintor;

    public Laboratory() { }

    public Laboratory(int id, int id_school_fk, int id_place_fk, String name, String description,
                      int floor, int capacity, boolean wifi, boolean computers, boolean projector, boolean extintor) {
        super(id, name, description, TYPE_LABORATORY, floor);
        this.id_school_fk = id_school_fk;
        this.id_place_fk = id_place_fk;
        this.setCapacity(capacity);
        this.wifi = wifi;
        this.setComputers(computers);
        this.setProjector(projector);
        this.setExtintor(extintor);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Laboratory(Parcel in) {
        super.id = in.readInt();
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
        super.name = in.readString();
        super.setType(TYPE_BATHROOM);
        super.description = in.readString();
        super.rating = in.readInt();
        super.floor = in.readInt();
        capacity = in.readInt();
        wifi = in.readBoolean();
        computers = in.readBoolean();
        projector = in.readBoolean();
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

    public boolean getProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public boolean getExtintor() {
        return extintor;
    }

    public void setExtintor(boolean extintor) {
        this.extintor = extintor;
    }
}

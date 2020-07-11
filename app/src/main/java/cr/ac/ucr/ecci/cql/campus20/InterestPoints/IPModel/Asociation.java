package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Asociation extends Place implements Parcelable {
    private int id_school_fk;
    private int id_place_fk;

    public Asociation() { }

    public Asociation(int id, int id_school_fk, int id_place_fk, String name, String description,
                      int floor, int capacity, boolean wifi, boolean computers) {
        super(id, name, description, -1, TYPE_ASOCIATION);
        this.id_school_fk = id_school_fk;
        this.id_place_fk = id_place_fk;
        super.setFloor(floor);
        super.setCapacity(capacity);
        super.setWifi(wifi);
        super.setHaveComputers(computers);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Asociation(Parcel in) {
        super.id = in.readInt();
        super.name = in.readString();
        super.setType(TYPE_ASOCIATION);
        super.description = in.readString();
        super.rating = in.readInt();
        super.floor = in.readInt();
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
        super.floor = in.readInt();
        super.capacity = in.readInt();
        super.setWifi(in.readBoolean());
        super.setHaveComputers(in.readBoolean());
    }

    public static final Creator<Asociation> CREATOR = new Creator<Asociation>() {
        @Override
        public Asociation createFromParcel(Parcel in) {
            return new Asociation(in);
        }

        @Override
        public Asociation[] newArray(int size) {
            return new Asociation[size];
        }
    };

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



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_school_fk);
        dest.writeInt(id_place_fk);

    }
}

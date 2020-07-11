package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Laboratory extends Place implements Parcelable {
    private int id_school_fk;
    private int id_place_fk;

    public Laboratory() {
        super();
        super.setType(TYPE_LABORATORY);
    }

    public Laboratory(int id, int id_school_fk, int id_place_fk, String name, String description, int floor, int capacity, Boolean wifi, Boolean computers, Boolean projector, Boolean extintor) {
        super(id, name, description, -1, TYPE_LABORATORY);
        super.setFloor(floor);
        super.setCapacity(capacity);
        super.setWifi(wifi);
        super.setHaveComputers(computers);
        super.setHaveExtintors(extintor);
        super.setHaveProjectors(projector);
        setId_place_fk(id_place_fk);
        setId_school_fk(id_school_fk);
    }

    protected Laboratory(Parcel in) {
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_school_fk);
        dest.writeInt(id_place_fk);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Laboratory> CREATOR = new Creator<Laboratory>() {
        @Override
        public Laboratory createFromParcel(Parcel in) {
            return new Laboratory(in);
        }

        @Override
        public Laboratory[] newArray(int size) {
            return new Laboratory[size];
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}

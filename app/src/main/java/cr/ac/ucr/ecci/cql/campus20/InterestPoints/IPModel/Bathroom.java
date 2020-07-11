package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class Bathroom extends Place implements Parcelable {

    private int id_school_fk;
    private int id_place_fk;
    private boolean wifi;

    public Bathroom() { }

    public Bathroom(int id, int id_school_fk, int id_place_fk, String name, String description, int floor, int capacity, boolean wifi) {
        super(id, name, description, -1, TYPE_BATHROOM);
        super.setFloor(floor);
        super.setCapacity(capacity);
        super.setWifi(wifi);
        this.id_school_fk = id_school_fk;
        this.id_place_fk = id_place_fk;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected Bathroom(Parcel in) {
        super.id = in.readInt();
        id_school_fk = in.readInt();
        id_place_fk = in.readInt();
        super.name = in.readString();
        super.setType(TYPE_BATHROOM);
        super.description = in.readString();
//        super.rating = in.readInt();
        super.floor = in.readInt();
        super.setCapacity(in.readInt());
        super.setWifi(in.readBoolean());
    }

    public static final Creator<Bathroom> CREATOR = new Creator<Bathroom>() {
        @Override
        public Bathroom createFromParcel(Parcel in) {
            return new Bathroom(in);
        }

        @Override
        public Bathroom[] newArray(int size) {
            return new Bathroom[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_school_fk);
        dest.writeInt(id_place_fk);
        dest.writeByte((byte) (wifi ? 1 : 0));
    }
}

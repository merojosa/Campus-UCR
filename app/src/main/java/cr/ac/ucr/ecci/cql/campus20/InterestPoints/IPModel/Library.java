package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Library extends Place implements Parcelable {

    private String horario;

    public Library() {
        super();
        super.setType(TYPE_LIBRARY);
    }

    public Library(int id, String name, String description, int image, double latitude, double longitude, ArrayList<Comment> comments) {
        super(id, name, description, image, TYPE_LIBRARY, comments);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
    }

    public Library(int id, String name, String description, int image, double latitude, double longitude, String horario, boolean wifi) {
        super(id, name, description, image, TYPE_LIBRARY, wifi);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        setHorario(horario);

    }


    public String getLibraryName() {
        return super.getName();
    }

    public void setLibraryName(String name) {
        super.setName(name);
    }


    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    // --------------- Parcelable methods ---------------

    public static final Creator<Soda> CREATOR = new Creator<Soda>() {
        @Override
        public Soda createFromParcel(Parcel in) {
            return new Soda(in);
        }

        @Override
        public Soda[] newArray(int size) {
            return new Soda[size];
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public Library(Parcel in) {
        super(in);
        super.setType(TYPE_LIBRARY);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horario);
        dest.writeByte((byte) (wifi == null ? 0 : wifi ? 1 : 2));
        dest.writeString(horario);
    }
}
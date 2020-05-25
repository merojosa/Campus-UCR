package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

/**
 * Class that represents a School database entity.
 * */
public class School extends GeneralData implements Parcelable {

    /*Columns*/
    private int id;
    private int id_faculty_fk;
    private int id_place_fk;
    private String name;
    private String description;
    private int image;

    public School() { }

    public School(int id, int id_faculty_fk, int id_place_fk, String name, String description, int image) {
        this.id = id;
        this.id_faculty_fk = id_faculty_fk;
        this.id_place_fk = id_place_fk;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    /**
     * Constructor.
     * */
    public School(int id, int id_faculty_fk, int id_place_fk, String name, String description) {
        this.id = id;
        this.id_faculty_fk = id_faculty_fk;
        this.id_place_fk = id_place_fk;
        this.name = name;
        this.description = description;
    }

    protected School(Parcel in) {
        id = in.readInt();
        id_faculty_fk = in.readInt();
        id_place_fk = in.readInt();
        name = in.readString();
        description = in.readString();
        image = in.readInt();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getTitle() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_faculty_fk);
        dest.writeInt(id_place_fk);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(image);
    }
}

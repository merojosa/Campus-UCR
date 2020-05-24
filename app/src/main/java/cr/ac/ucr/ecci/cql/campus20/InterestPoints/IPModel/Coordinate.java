package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

/**
 * Class that represents a Coordinate database entity.
 * */
public class Coordinate extends GeneralData {

    private int id;
    private int id_place_fk;
    private double latitude;
    private double longitude;

    /**
     * Constructor.
     * */
    public Coordinate(int id, int id_place_fk, double latitude, double longitude) {
        this.id = id;
        this.id_place_fk = id_place_fk;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinate() { }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_place_fk() {
        return id_place_fk;
    }

    public void setId_place_fk(int id_place_fk) {
        this.id_place_fk = id_place_fk;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

}

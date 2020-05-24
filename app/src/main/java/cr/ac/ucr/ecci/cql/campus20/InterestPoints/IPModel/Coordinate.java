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

    /**
     * Inserts a new row in Coordinate table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_ID_PLACE_FK, getId_place_fk());
        values.put(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_LATITUDE, getLatitude());
        values.put(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_LONGITUDE, getLongitude());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.CoordinateTable.TABLE_NAME, values);
        dataAccess.close();
        return result;
    }

    /**
     * Retrieves the Coordinate object related to a given place.
     * @param context Current app context.
     * @param id_place_fk Foreign key to the place where the coordinate belongs.
     * */
    public static Coordinate read(Context context, int id_place_fk){
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();

        Cursor cursor = dataAccess.select(
                null,
                DatabaseContract.InterestPoints.CoordinateTable.TABLE_NAME,
                DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_ID_PLACE_FK + " = " + Integer.toString(id_place_fk)
        );
        cursor.moveToFirst();
        Coordinate coordinate = new Coordinate();
        /*The place must have at most one coordinate.*/
        if (!cursor.isAfterLast()) {
            coordinate.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_ID)));
            coordinate.setId_place_fk(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_ID_PLACE_FK)));
            coordinate.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_LATITUDE)));
            coordinate.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoordinateTable.TABLE_COLUMN_LONGITUDE)));
        }
        cursor.close();
        dataAccess.close();
        Log.d("coordinateRead", "The coordinate from place id: " + Integer.toString(id_place_fk) + " had been read from database.");
        return coordinate;
    }
}

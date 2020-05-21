package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

/**
 * Class that represents a School database entity.
 * */
public class School extends GeneralData {

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

    /**
     * Inserts a new row in School table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_FACULTY_FK, getId_faculty_fk());
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_PLACE_FK, getId_place_fk());
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_NAME, getName());
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_DESCRIPTION, getDescription());
        values.put(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_IMAGE, getImage());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.SchoolTable.TABLE_NAME, values);
        dataAccess.close();
        return result;
    }

    /**
     * Retrieves all the Schools related to a given faculty.
     * @param context Current app context.
     * @param faculty Foreign key to the faculty where the schools belong.
     * */
    public static List<School> read(Context context, String faculty){
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();

        Cursor fCursor = dataAccess.select("Id",
                DatabaseContract.InterestPoints.FacultyTable.TABLE_NAME,
                DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_NAME + " = " + "'"+faculty+ "'");
        fCursor.moveToFirst();
        int  id_faculty_fk = fCursor.getInt(fCursor.getColumnIndexOrThrow(
                DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_ID));
        fCursor.close();

        Cursor cursor = dataAccess.select(
                null,
                DatabaseContract.InterestPoints.SchoolTable.TABLE_NAME,
                DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_FACULTY_FK + " = " + Integer.toString(id_faculty_fk)
        );
        cursor.moveToFirst();
        List<School> schools = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            schools.add( new School(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_FACULTY_FK)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_PLACE_FK)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_IMAGE))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        dataAccess.close();
        Log.d("schoolRead", "The schools from faculty id: " + Integer.toString(id_faculty_fk) + " had been read from database.");
        return schools;
    }

    /**
     * Retrieves all the Schools related to a given faculty.
     * @param context Current app context.
     * @param schoolName Name of the school to be selected.
     * */
    public static School select(Context context, String schoolName){
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();

        Cursor cursor = dataAccess.select("*",
                DatabaseContract.InterestPoints.SchoolTable.TABLE_NAME,
                DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_NAME + " = " + "'"+schoolName+ "'");
        cursor.moveToFirst();

        if (cursor.isAfterLast()) {
            return null;
        }
        School school = new School(
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_FACULTY_FK)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_ID_PLACE_FK)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_DESCRIPTION)),
            cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.SchoolTable.TABLE_COLUMN_IMAGE))
        );

        cursor.close();
        dataAccess.close();
        Log.d("schoolRead", "A single school name " + schoolName + " has been read from database.");
        return school;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

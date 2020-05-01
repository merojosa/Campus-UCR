package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.GeneralData;

public class Faculty extends GeneralData {// implements Comparable<Faculty>{

    int id;
    String facultyName;
    String description;

    public Faculty(){}

    public Faculty(String facultyName, String description){
        this.facultyName = facultyName;
        this.description = description;
    }

    public Faculty(int id, String facultyName, String description){
        this.id = id;
        this.facultyName = facultyName;
        this.description = description;
    }

    // Hay que implementar getter y setters de los atributos que tenga Faculty en la BD


    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String getTitle() {
        return facultyName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Inserts a new row in Faculty table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_NAME, getFacultyName());
        values.put(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_DESCRIPTION, getDescription());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.FacultyTable.TABLE_NAME, values);
        return result;
    }

    /**
     * @param context Current app context.
     * @return List containing all the rows in the table.
     * */
    public List<Faculty> getFacultiesList(Context context) {
        List<Faculty> list = new ArrayList<>();
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        Cursor cursor = dataAccess.selectAll("Faculty");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Faculty(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.FacultyTable.TABLE_COLUMN_DESCRIPTION))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        dataAccess.close();
        return list;
    }
}

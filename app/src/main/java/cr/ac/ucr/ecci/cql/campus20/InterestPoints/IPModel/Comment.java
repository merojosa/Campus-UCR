package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Comment extends GeneralData {

    private int id;
    private int id_place_fk;
    private String description;
    private String date;

    public Comment() {
    }

    public Comment(int id, int id_place_fk, String description, String date) {
        this.id = id;
        this.id_place_fk = id_place_fk;
        this.description = description;
        this.date = date;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Inserts a new row in Comment table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_ID_PLACE_FK, getId_place_fk());
        values.put(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_DESCRIPTION, getDescription());
        values.put(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_DATE, getDate());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.CommentTable.TABLE_NAME, values);
        dataAccess.close();
        return result;
    }

    /**
     * Retrieves all the Comments related to a given place.
     * @param context Current app context.
     * @param id_place_fk Foreign key to the place where the comments belong.
     * */
    public static List<Comment> read(Context context, int id_place_fk){
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();

        Cursor cursor = dataAccess.select(
                null,
                DatabaseContract.InterestPoints.CommentTable.TABLE_NAME,
                DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_ID_PLACE_FK + " = " + Integer.toString(id_place_fk)
        );
        cursor.moveToFirst();
        List<Comment> comments = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            comments.add( new Comment(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_ID_PLACE_FK)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CommentTable.TABLE_COLUMN_DATE))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        dataAccess.close();
        Log.d("commentRead", "The comments from place id: " + Integer.toString(id_place_fk) + " had been read from database.");
        return comments;
    }

}

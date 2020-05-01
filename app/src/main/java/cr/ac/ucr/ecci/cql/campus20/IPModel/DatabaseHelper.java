package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 4;

    private static final String DB_NAME = "CampusDB.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.InterestPoints.PlaceTable.SQL_CREATE_PLACE);
        db.execSQL(DatabaseContract.InterestPoints.FacultyTable.SQL_CREATE_FACULTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseContract.InterestPoints.PlaceTable.SQL_DELETE_PLACE);
        db.execSQL(DatabaseContract.InterestPoints.FacultyTable.SQL_DELETE_FACULTY);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}

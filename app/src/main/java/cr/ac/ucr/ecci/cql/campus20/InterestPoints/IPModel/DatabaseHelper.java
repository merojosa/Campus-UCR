package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**Increment this counter by one whenever the database model is modified.*/
    private static final int VERSION = 12;

    private static final String DB_NAME = "CampusDB.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(String statement : DatabaseContract.DeploymentScript){
            db.execSQL(statement.replaceAll("\\s+", " "));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}

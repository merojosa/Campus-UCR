package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//TODO: Implement all the CRUD operations and their variants using WHERE, ORDER BY, GROUP BY and other clauses.
/**
 * @class DataAccess Wrapper object around underlying databases.
 * @brief Provides an abstraction level for CRUD operations.
 * All database entities objects should use this class CRUD methods to perform their database operations
 * instead of using DatabaseHelper directly.
 * */
public class DataAccess {
    private SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;
    private static DataAccess instance;

    /**
     * Gets an instance of this object.
     * @param context Current app context.
     * @return A static DataAccess object.
     * */
    public static DataAccess getInstance(Context context){
        if(instance == null)
            instance = new DataAccess(context);
        return instance;
    }

    /**
     * Constructor.
     * */
    public DataAccess(Context context){
        this.openHelper = new DatabaseHelper(context);
    }

    /**
     * Opens a R/W connection to the database.
     * */
    public void open(){
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Closes the connection if it was open.
     * */
    public void close(){
        if(database != null)
            database.close();
    }

    /**
     * Performs a SELECT * FROM table.
     * @param fromTable The table whose rows are to be retrieved.
     * @return A Cursor object containing the results of the SELECT operation.
     * */
    public Cursor selectAll(String fromTable){
        String query = "SELECT * FROM " + fromTable + ";";
        return database.rawQuery(query, null);
    }

    /**
     * Performs an INSERT operation without WHERE clause.
     * @param tableName The table where the data is going to be inserted.
     * @param values The key-value map containing all the data.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(String tableName, ContentValues values){
        long result = 0;
        try {
            result = database.insert(tableName, null, values);
        }catch(Exception e){
            Log.d("exception", e.toString());
        }
        Log.d("insert", Long.toString(result));
        return result;
    }

}

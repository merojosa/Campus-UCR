package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.net.wifi.p2p.WifiP2pManager;
import android.provider.BaseColumns;

/* @class DatabaseContract
*  This class defines the string constants and statements to define the database model in local storage
*  through SQLite, following the inner-class hierarchy Module > Tables > Columns and Statements.
* */
public class DatabaseContract {

    private DatabaseContract () {}

    private static final String COMMA = ",";
    private static final String TEXT_TYPE = " TEXT ";
    private static final String INTEGER_TYPE = " INTEGER ";
    private static final String REAL_TYPE = " REAL ";
    private static final String PK = " PRIMARY KEY ";
    private static final String FK = " FOREIGN KEY ";
    private static final String NULLABLE = " null ";

    /*Database model for Interest Points module. Repeat with other modules.*/
    public static class InterestPoints {
        /*Place table.*/
        public static final class PlaceTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Place";

            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_NAME = "Name";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            public static final String TABLE_COLUMN_TYPE = "Type";
            public static final String TABLE_COLUMN_RATING = "Rating";
            public static final String TABLE_COLUMN_FLOOR = "Floor";

            /*SQL Statements*/
            public static final String SQL_CREATE_PLACE =
                    "CREATE TABLE " + PlaceTable.TABLE_NAME + " (" +
                            PlaceTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            PlaceTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            PlaceTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA +
                            PlaceTable.TABLE_COLUMN_TYPE + TEXT_TYPE + COMMA +
                            PlaceTable.TABLE_COLUMN_RATING + INTEGER_TYPE + NULLABLE + COMMA +
                            PlaceTable.TABLE_COLUMN_FLOOR + INTEGER_TYPE + NULLABLE + " )";

            public static final String SQL_DELETE_PLACE =
                    "DROP TABLE IF EXISTS " + PlaceTable.TABLE_NAME;
        }

        /*TO-DO: Fill table attributes and statements.*/
        /*Comment table.*/
        public static final class Comment implements BaseColumns{
            public static final String TABLE_COMMENT = "Comment";
        }

        /*Coordinate table.*/
        public static final class Coordinate implements BaseColumns{
            public static final String TABLE_COORDINATE = "Coordinate";
        }

        /*Faculty table.*/
        public static final class Faculty implements BaseColumns{
            public static final String TABLE_FACULTY = "Faculty";
        }

        /*School table.*/
        public static final class School implements BaseColumns {
            public static final String TABLE_SCHOOL = "School";
        }
    }
}

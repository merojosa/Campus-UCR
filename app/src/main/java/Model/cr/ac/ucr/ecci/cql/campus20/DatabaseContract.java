package cr.ac.ucr.ecci.cql.campus20;

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
    private static final String PK = "PRIMARY KEY";
    private static final String FK = "FOREIGN KEY";

    /*Database model for Interest Points module. Repeat with other modules.*/
    public static class InterestPoints {
        /*Place table.*/
        public static final class Place implements BaseColumns{
            /*Table name*/
            public static final String TABLE_PLACE = "Location";

            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_ID_PLACE_FK = "Id_Place_FK";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            public static final String TABLE_COLUMN_DATE = "Date";

            /*SQL Statements*/
            public static final String SQL_CREATE_LOCATION =
                    "CREATE TABLE " + Place.TABLE_PLACE + " (" +
                            Place.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            Place.TABLE_COLUMN_ID_PLACE_FK + INTEGER_TYPE + FK + COMMA +
                            Place.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA +
                            Place.TABLE_COLUMN_DATE + TEXT_TYPE + " )";

            public static final String SQL_DELETE_LOCATION =
                    "DROP TABLE IF EXISTS " + Place.TABLE_PLACE;
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

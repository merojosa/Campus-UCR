package cr.ac.ucr.ecci.cql.campus20.IPModel;

import android.provider.BaseColumns;

/** @class DatabaseContract
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
    private static final String REF = " REFERENCES ";
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

        /*TODO: Fill table attributes and statements.*/
        /*Comment table.*/
        public static final class Comment implements BaseColumns{
            public static final String TABLE_NAME = "Comment";
        }

        /*Coordinate table.*/
        public static final class Coordinate implements BaseColumns{
            public static final String TABLE_NAME = "Coordinate";
        }

        /*Faculty table.*/
        public static final class FacultyTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Faculty";
            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_NAME = "Name";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            /*Statements*/
            public static final String SQL_CREATE_FACULTY =
                    "CREATE TABLE " + FacultyTable.TABLE_NAME + " (" +
                            FacultyTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            FacultyTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            FacultyTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + NULLABLE + " )";

            public static final String SQL_DELETE_FACULTY =
                    "DROP TABLE IF EXISTS " + FacultyTable.TABLE_NAME;
        }

        /*School table.*/
        public static final class SchoolTable implements BaseColumns {
            /*Table name*/
            public static final String TABLE_NAME = "School";

            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_ID_FACULTY_FK = "Id_faculty_FK";
            public static final String TABLE_COLUMN_ID_PLACE_FK = "Id_place_FK";
            public static final String TABLE_COLUMN_NAME = "Name";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";

            /*Statements*/
            public static final String SQL_CREATE_SCHOOL =
                    "CREATE TABLE " + SchoolTable.TABLE_NAME + " (" +
                            SchoolTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            SchoolTable.TABLE_COLUMN_ID_FACULTY_FK + INTEGER_TYPE + FK + REF + FacultyTable.TABLE_NAME + " (" + FacultyTable.TABLE_COLUMN_ID + ") " + COMMA +
                            SchoolTable.TABLE_COLUMN_ID_PLACE_FK + INTEGER_TYPE + FK + REF + PlaceTable.TABLE_NAME + " (" + PlaceTable.TABLE_COLUMN_ID + ") " + COMMA +
                            SchoolTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            SchoolTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + NULLABLE + " )";

            public static final String SQL_DELETE_SCHOOL =
                    "DROP TABLE IF EXISTS " + SchoolTable.TABLE_NAME;
        }
    }

    public static String[] DeploymentScript = {
            InterestPoints.PlaceTable.SQL_DELETE_PLACE,
            InterestPoints.FacultyTable.SQL_DELETE_FACULTY,
            InterestPoints.PlaceTable.SQL_CREATE_PLACE,
            InterestPoints.FacultyTable.SQL_CREATE_FACULTY,
    };
}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.provider.BaseColumns;

/** @class DatabaseContract
*  This class defines the string constants and statements to define the database model in local storage
*  through SQLite, following the inner-class hierarchy Module > Tables > Columns and Statements.
* */
public class DatabaseContract {

    private DatabaseContract () {}

    private static final String COMMA = ", ";
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
            public static final String TABLE_COLUMN_IMAGE = "Image";

            /*SQL Statements*/
            public static final String SQL_CREATE_PLACE =
                    "CREATE TABLE " + PlaceTable.TABLE_NAME + " (" +
                            PlaceTable.TABLE_COLUMN_ID          + INTEGER_TYPE  + PK        + COMMA +
                            PlaceTable.TABLE_COLUMN_NAME        + TEXT_TYPE     + COMMA     +
                            PlaceTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE     + COMMA     +
                            PlaceTable.TABLE_COLUMN_TYPE        + TEXT_TYPE     + COMMA     +
                            PlaceTable.TABLE_COLUMN_RATING      + INTEGER_TYPE  + NULLABLE  + COMMA +
                            PlaceTable.TABLE_COLUMN_FLOOR       + INTEGER_TYPE  + NULLABLE  + COMMA +
                            PlaceTable.TABLE_COLUMN_IMAGE       + INTEGER_TYPE  + NULLABLE  +
                            " )";

            public static final String SQL_DELETE_PLACE =
                    "DROP TABLE IF EXISTS " + PlaceTable.TABLE_NAME;
        }

        /*TODO: Fill table attributes and statements.*/
        /*Comment table.*/
        public static final class CommentTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Comment";

            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_ID_PLACE_FK = "Id_place_FK";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            public static final String TABLE_COLUMN_DATE = "Date";

            /*Statements*/
            public static final String SQL_CREATE_COMMENT =
                    "CREATE TABLE " + CommentTable.TABLE_NAME + " (" +
                            CommentTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            CommentTable.TABLE_COLUMN_ID_PLACE_FK+ INTEGER_TYPE + COMMA +
                            CommentTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + COMMA +
                            CommentTable.TABLE_COLUMN_DATE + TEXT_TYPE + COMMA +
                            FK + "(" + CommentTable.TABLE_COLUMN_ID_PLACE_FK + ")" + REF + PlaceTable.TABLE_NAME + " (" + PlaceTable.TABLE_COLUMN_ID + ") " +
                            " )";

            public static final String SQL_DELETE_COMMENT =
                    "DROP TABLE IF EXISTS " + CommentTable.TABLE_NAME;
        }

        /*Coordinate table.*/
        public static final class CoordinateTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Coordinate";

            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_ID_PLACE_FK = "Id_place_FK";
            public static final String TABLE_COLUMN_LATITUDE = "Latitude";
            public static final String TABLE_COLUMN_LONGITUDE = "Longitude";

            /*Statements*/
            public static final String SQL_CREATE_COORDINATE =
                    "CREATE TABLE " + CoordinateTable.TABLE_NAME + " (" +
                            CoordinateTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            CoordinateTable.TABLE_COLUMN_ID_PLACE_FK + INTEGER_TYPE + COMMA +
                            CoordinateTable.TABLE_COLUMN_LATITUDE + REAL_TYPE + COMMA +
                            CoordinateTable.TABLE_COLUMN_LONGITUDE + REAL_TYPE + COMMA +
                            FK + "(" + CoordinateTable.TABLE_COLUMN_ID_PLACE_FK + ")" + REF + PlaceTable.TABLE_NAME + " (" + PlaceTable.TABLE_COLUMN_ID + ") " +
                            " )";

            public static final String SQL_DELETE_COORDINATE =
                    "DROP TABLE IF EXISTS " + CoordinateTable.TABLE_NAME;
        }

        /*Faculty table.*/
        public static final class FacultyTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Faculty";
            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_NAME = "Name";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            public static final String TABLE_COLUMN_IMAGE = "Image";
            /*Statements*/
            public static final String SQL_CREATE_FACULTY =
                    "CREATE TABLE " + FacultyTable.TABLE_NAME + " (" +
                            FacultyTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            FacultyTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            FacultyTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + NULLABLE + COMMA +
                            FacultyTable.TABLE_COLUMN_IMAGE + INTEGER_TYPE  + NULLABLE  +
                            " )";

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
            public static final String TABLE_COLUMN_IMAGE = "Image";
            public static final String TABLE_COLUMN_PHONE = "Phone";
            public static final String TABLE_COLUMN_SCHEDULE = "Schedule";

            /*Statements*/
            public static final String SQL_CREATE_SCHOOL =
                    "CREATE TABLE " + SchoolTable.TABLE_NAME + " (" +
                            SchoolTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            SchoolTable.TABLE_COLUMN_ID_FACULTY_FK + INTEGER_TYPE + COMMA +
                            SchoolTable.TABLE_COLUMN_ID_PLACE_FK + INTEGER_TYPE + COMMA +
                            SchoolTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            SchoolTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + NULLABLE + COMMA +
                            SchoolTable.TABLE_COLUMN_IMAGE + INTEGER_TYPE  + NULLABLE  + COMMA +
                            SchoolTable.TABLE_COLUMN_PHONE + TEXT_TYPE + NULLABLE + COMMA +
                            SchoolTable.TABLE_COLUMN_SCHEDULE + TEXT_TYPE + NULLABLE + COMMA +
                            FK + "(" + SchoolTable.TABLE_COLUMN_ID_FACULTY_FK + ")" + REF + FacultyTable.TABLE_NAME + " (" + FacultyTable.TABLE_COLUMN_ID + ") " + COMMA +
                            FK + "(" + SchoolTable.TABLE_COLUMN_ID_PLACE_FK + ")" + REF + PlaceTable.TABLE_NAME + " (" + PlaceTable.TABLE_COLUMN_ID + ") " +
                            " )";

            public static final String SQL_DELETE_SCHOOL =
                    "DROP TABLE IF EXISTS " + SchoolTable.TABLE_NAME;
        }


        /*Faculty table.*/
        public static final class CoffeShopTable implements BaseColumns{
            /*Table name*/
            public static final String TABLE_NAME = "Coffe";
            /*Columns*/
            public static final String TABLE_COLUMN_ID = "Id";
            public static final String TABLE_COLUMN_NAME = "Name";
            public static final String TABLE_COLUMN_DESCRIPTION = "Description";
            public static final String TABLE_COLUMN_IMAGE = "Image";
            /*Statements*/
            public static final String SQL_CREATE_COFFE =
                    "CREATE TABLE " + InterestPoints.CoffeShopTable.TABLE_NAME + " (" +
                            InterestPoints.CoffeShopTable.TABLE_COLUMN_ID + INTEGER_TYPE + PK + COMMA +
                            InterestPoints.CoffeShopTable.TABLE_COLUMN_NAME + TEXT_TYPE + COMMA +
                            InterestPoints.CoffeShopTable.TABLE_COLUMN_DESCRIPTION + TEXT_TYPE + NULLABLE + COMMA +
                            InterestPoints.CoffeShopTable.TABLE_COLUMN_IMAGE + INTEGER_TYPE  + NULLABLE  +
                            " )";

            public static final String SQL_DELETE_COFFE =
                    "DROP TABLE IF EXISTS " + InterestPoints.CoffeShopTable.TABLE_NAME;
        }

    }


    /*All statements here are executed whenever the database is created or updated.*/
    public static String[] DeploymentScript = {
            InterestPoints.PlaceTable.SQL_DELETE_PLACE,
            InterestPoints.FacultyTable.SQL_DELETE_FACULTY,
            InterestPoints.SchoolTable.SQL_DELETE_SCHOOL,
            InterestPoints.CoffeShopTable.SQL_DELETE_COFFE,
            InterestPoints.CoordinateTable.SQL_DELETE_COORDINATE,
            InterestPoints.CommentTable.SQL_DELETE_COMMENT,
            InterestPoints.PlaceTable.SQL_CREATE_PLACE,
            InterestPoints.FacultyTable.SQL_CREATE_FACULTY,
            InterestPoints.SchoolTable.SQL_CREATE_SCHOOL,
            InterestPoints.CoffeShopTable.SQL_CREATE_COFFE,
            InterestPoints.CoordinateTable.SQL_CREATE_COORDINATE,
            InterestPoints.CommentTable.SQL_CREATE_COMMENT
    };
}

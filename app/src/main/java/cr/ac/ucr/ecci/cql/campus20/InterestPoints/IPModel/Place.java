package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
//TODO: Implement CRUD operations using DataAccess helper class.
/**
 * @brief Class that represents a Place database entity.
 * */

public class Place extends GeneralData implements Parcelable {

    /*Columns*/
    private int id;
    private String name;
    private String description;
    private String type;
    private int rating;
    private int floor;
    private int image;

    public Place() {
    }

    public Place(int id, String name, String description, String type, int rating, int floor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating = rating;
        this.floor = floor;
    }

    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        type = in.readString();
        rating = in.readInt();
        floor = in.readInt();
    }

    public static final Creator<Place> CREATOR = new Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel in) {
            return new Place(in);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };

    public Place(int id, String name, String description, String type, int rating, int floor, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating = rating;
        this.floor = floor;
        this.image = image;
    }

    @Override
    public String getTitle(){
        return getName();
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeInt(rating);
        dest.writeInt(floor);
    }

    /**
     * @param context Current app context.
     * @return List containing all the rows in the table.
     * */
    public static List<Place> getPlacesList(Context context) {
        List<Place> list = new ArrayList<>();
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        Cursor cursor = dataAccess.selectAll(DatabaseContract.InterestPoints.PlaceTable.TABLE_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Place(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_TYPE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_RATING)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_FLOOR)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_IMAGE))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        dataAccess.close();
        return list;
    }

    /**
     * Inserts a new row in Place table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_NAME, getName());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_DESCRIPTION, getDescription());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_TYPE, getType());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_RATING, getRating());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_FLOOR, getFloor());
        values.put(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_IMAGE, getImage());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.PlaceTable.TABLE_NAME, values);
        dataAccess.close();
        return result;
    }

    /**
     * Retrieves the Place object identified by its id.
     * @param context Current app context.
     * @param id Foreign key to the place where the coordinate belongs.
     * */
    public static Place read(Context context, int id){
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();

        Cursor cursor = dataAccess.select(
                null,
                DatabaseContract.InterestPoints.PlaceTable.TABLE_NAME,
                DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_ID + " = " + Integer.toString(id)
        );
        cursor.moveToFirst();
        Place place = new Place();

        if (!cursor.isAfterLast()) {
            place.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_ID)));
            place.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_NAME)));
            place.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_DESCRIPTION)));
            place.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_TYPE)));
            place.setRating(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_RATING)));
            place.setFloor(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_FLOOR)));
            place.setImage(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.PlaceTable.TABLE_COLUMN_IMAGE)));
        }
        cursor.close();
        dataAccess.close();
        Log.d("placeRead", "The place with id: " + Integer.toString(id) + " had been read from database.");
        return place;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

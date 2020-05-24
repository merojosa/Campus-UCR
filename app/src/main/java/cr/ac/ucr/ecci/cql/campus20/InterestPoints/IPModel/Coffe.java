package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Coffe extends GeneralData {

    int id;
    String coffeShopName;
    String description;
    int image;

    public Coffe() {}

    public Coffe(String coffeShopName, String description){
        this.coffeShopName = coffeShopName;
        this.description = description;
    }

    public Coffe(int id, String coffeShopName, String description){
        this.id = id;
        this.coffeShopName = coffeShopName;
        this.description = description;
    }

    public Coffe(int id, String coffeShopName, String description, int image) {
        this.id = id;
        this.coffeShopName = coffeShopName;
        this.description = description;
        this.image = image;
    }


    // Hay que implementar getter y setters de los atributos que tenga Coffe en la BD

    public String getCoffeName() {
        return coffeShopName;
    }

    public void setCoffeName(String coffeShopName) {
        this.coffeShopName = coffeShopName;
    }

    @Override
    public String getTitle() {
        return coffeShopName;
    }

    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    //--------------------------------------------------------------------------------------------//

    /**
     * Inserts a new row in Faculty table.
     * @param context Current app context.
     * @return The row ID of the newly inserted row, or -1 if an error occurred.
     * */
    public long insert(Context context){
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_ID, getId());
        values.put(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_NAME, getCoffeName());
        values.put(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_DESCRIPTION, getDescription());
        values.put(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_IMAGE, getImage());

        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        long result = dataAccess.insert(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_NAME, values);
        dataAccess.close();
        return result;
    }

    /**
     * @param context Current app context.
     * @return List containing all the rows in the table.
     * */
    public static List<Coffe> getCoffeShopList(Context context) {
        List<Coffe> list = new ArrayList<>();
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        Cursor cursor = dataAccess.selectAll("Coffe");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(new Coffe(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_IMAGE))
            ));
            cursor.moveToNext();
        }
        cursor.close();
        dataAccess.close();
        return list;
    }

    /**
     * @param context Current app context.
     * @return the selected faculty instance.
     * */
    public static Coffe getCoffeShop(Context context, int id) {
        DataAccess dataAccess = DataAccess.getInstance(context);
        dataAccess.open();
        Cursor cursor = dataAccess.select("*", "Coffe", DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_ID + " = " + Integer.toString(id));
        if(cursor.isAfterLast()){
            return null;
        }
        cursor.moveToFirst();

        Coffe returned = new Coffe(
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_NAME)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.InterestPoints.CoffeShopTable.TABLE_COLUMN_IMAGE))
        );
        cursor.close();
        dataAccess.close();
        return returned;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}

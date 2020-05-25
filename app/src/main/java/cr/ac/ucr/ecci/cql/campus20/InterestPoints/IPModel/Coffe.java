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

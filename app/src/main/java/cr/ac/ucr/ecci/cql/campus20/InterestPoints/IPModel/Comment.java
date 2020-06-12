package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;


/*
https://www.geeksforgeeks.org/android-creating-a-ratingbar/
https://abhiandroid.com/ui/ratingbar
https://www.youtube.com/watch?v=LpNJhJF3gW8

hacer el rating float
 */

public class Comment extends GeneralData {

    private int id;
    private int id_place_fk;
    private String description;
    private String date;
    private float cRating;
    private String photo_path;

    public Comment() {}

    public Comment(int id, int id_place_fk, String description, String date, float cRating, String photo_path) {
        this.id = id;
        this.id_place_fk = id_place_fk;
        this.description = description;
        this.date = date;
        this.cRating = cRating;
        this.photo_path = photo_path;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_place_fk() {
        return id_place_fk;
    }

    public void setId_place_fk(int id_place_fk) {
        this.id_place_fk = id_place_fk;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getcRting() {
        return cRating;
    }

    public void setcRating(String cRrating) {
        this.cRating = cRating;
    }

    public String getPhotoPath() { return photo_path; }

    public void setPhotoPath(String photo_path) {
        this.photo_path = photo_path;
    }

}

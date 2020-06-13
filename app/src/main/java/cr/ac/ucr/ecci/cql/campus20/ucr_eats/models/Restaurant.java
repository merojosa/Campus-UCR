package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.UcrEatsFirebaseDatabase;

@Entity(tableName = "Restaurant",
        indices = {@Index(value={"name"}, unique = true)}) // Unique Restaurant name
public class Restaurant
{
    @PrimaryKey
    private int id;

    @Ignore
    private String firebaseId;

    @ColumnInfo(name = "name")
    public String name = "";

    @ColumnInfo(name = "photo")
    public String photo = "";

    @ColumnInfo(name = "latitude")
    public double latitude = 0;

    @ColumnInfo(name = "longitude")
    public double longitude = 0;

    @ColumnInfo(name = "days_open")
    public String daysOpen = "";

    @ColumnInfo(name = "opening_time")
    public int openingTime = 0;

    @ColumnInfo(name = "closing_time")
    public int closingTime = 0;

    public double rating = 0;

    public int closing_hour = 0;

    public int capacity;

    public int capacity_max;

    @Ignore
    private int totalServings;

    @Ignore
    private int availableServings;

    public Restaurant(){}

    // Firebase data constructor
    @Ignore
    public Restaurant(@NonNull DataSnapshot data)
    {
        this.setName(data.child("name").getValue(String.class));
        this.setPhoto(data.child("photo").getValue(String.class));
        this.setDays_open(data.child("days_open").getValue(String.class));
        this.setCapacity(Objects.requireNonNull(data.child("capacity").getValue(Integer.class)));
        this.setLatitude(Objects.requireNonNull(data.child("latitud").getValue(Integer.class)));
        this.setLongitude(Objects.requireNonNull(data.child("longitud").getValue(Integer.class)));
        this.setCapacity_max(Objects.requireNonNull(data.child("capacity_max").getValue(Integer.class)));
        this.setOpening_hour(Objects.requireNonNull(data.child("opening_hour").getValue(Integer.class)));
        this.setClosing_hour(Objects.requireNonNull(data.child("closing_hour").getValue(Integer.class)));
        this.setRating(Objects.requireNonNull(data.child("rating").getValue(Double.class)));
    }

    @Ignore
    public Restaurant(String name, String photo, double latitude, double longitude,
                      String daysOpen, int openingTime, int closingTime)
    {
        this.name = name;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.daysOpen = daysOpen;
        this.openingTime = openingTime;
        this.closing_hour = closingTime;
    }

    public void setServings(DataSnapshot data)
    {
        DataSnapshot mealsRoot = data.child("meals");

        Iterable<DataSnapshot> meals = mealsRoot.getChildren();

        this.totalServings = 0;
        this.availableServings = 0;

        for(DataSnapshot meal : meals)
        {
            this.totalServings += Objects.requireNonNull(meal.child("max").getValue(Integer.class));
            this.availableServings += Objects.requireNonNull(meal.child("available").getValue(Integer.class));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getDays_open() {
        return daysOpen;
    }

    public void setDays_open(String days_open) {
        this.daysOpen = days_open;
    }

    public int getOpening_hour() {
        return openingTime;
    }

    public void setOpening_hour(int opening_hour) {
        this.openingTime = opening_hour;
    }

    public int getClosing_hour() {
        return closing_hour;
    }

    public void setClosing_hour(int closing_hour) {
        this.closing_hour = closing_hour;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity_max() {
        return capacity_max;
    }

    public void setCapacity_max(int capacity_max) {
        this.capacity_max = capacity_max;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }

    public int getTotalServings() {
        return totalServings;
    }

    public void setTotalServings(int totalServings) {
        this.totalServings = totalServings;
    }

    public int getAvailableServings() {
        return availableServings;
    }

    public void setAvailableServings(int availableServings) {
        this.availableServings = availableServings;
    }
}

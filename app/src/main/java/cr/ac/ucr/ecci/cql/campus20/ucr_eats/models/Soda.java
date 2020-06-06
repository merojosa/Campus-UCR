package cr.ac.ucr.ecci.cql.campus20.ucr_eats.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class Soda {

    public String name = "";

    public String photo = "";

    public double latitude = 0;

    public double longitude = 0;

    public String days_open = "";

    public int opening_hour = 0;

    public int closing_hour = 0;

    public double rating = 0;

    public int capacity;

    public int capacity_max;

    public Soda(){}

    // Firebase data constructor
    public Soda(@NonNull DataSnapshot data)
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
        return days_open;
    }

    public void setDays_open(String days_open) {
        this.days_open = days_open;
    }

    public int getOpening_hour() {
        return opening_hour;
    }

    public void setOpening_hour(int opening_hour) {
        this.opening_hour = opening_hour;
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

}

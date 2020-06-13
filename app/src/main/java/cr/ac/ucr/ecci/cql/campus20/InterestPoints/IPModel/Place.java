package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Place extends GeneralData {

    public int id;
    public String name;
    public String description;
    public String type;
    public int rating;
    public int floor;
    public int image;
    public String title;

    Place() {}

    public Place(int id, String name, String description, int rating, int floor, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = "";
        this.rating = rating;
        this.floor = floor;
        this.image = image;
    }

    public Place(int id, String name, String description, int image, String title) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.title = title;
    }

    public Place(int id, String name, String description, int image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = "";
    }

    public Place(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = "";
    }

    public Place(int id, String name, String description, String type, int rating, int something) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating = rating;
        this.type = "";
        // Somthing
    }

    protected Place(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        type = in.readString();
        rating = in.readInt();
        floor = in.readInt();
        image = in.readInt();
        title = in.readString();
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

    @Override
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
    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

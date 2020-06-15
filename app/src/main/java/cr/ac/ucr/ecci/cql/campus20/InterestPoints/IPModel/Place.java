package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Array;
import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Place extends GeneralData implements Parcelable {

    public int id;
    public String name;
    public String description;
    public String type;
    public int rating;
    public int floor;
    public int image;
    public double latitude;
    public double longitude;

    public static final String TYPE_COFFEE = "Coffee";
    public static final String TYPE_FACULTY = "Faculty";
    public static final String TYPE_OFFICE = "Office";
    public static final String TYPE_PHOTOCOPIER = "Photocopier";
    public static final String TYPE_LIBRARY = "Library";
    public static final String TYPE_SCHOOL = "School";
    public static final String TYPE_SODA = "Soda";
    public static final String TYPE_PLACE = "Place";

    public ArrayList<Comment> comments;

    Place() {}

    // Used in the Faculty/DeploymentScript
    // Used in the Coffe/DeploymentScript
    // Used in the School
    public Place(int id, String name, String description, int image, String type, ArrayList<Comment> comments) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
        this.comments = comments;
    }

    public Place(int id, String name, String description, int image, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
        this.comments = comments;
    }

    // Used in the Deployment Script/Fincas/Places
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
        image = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        this.comments = new ArrayList<>();
        in.readList(this.comments, Comment.class.getClassLoader());
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
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
        dest.writeInt(image);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeList(comments);
    }
}

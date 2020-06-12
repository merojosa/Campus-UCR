package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Place extends GeneralData implements Parcelable {

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
    }

    public Place(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Place(int id, String name, String description, String type, int rating, int something) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating = rating;
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
        dest.writeString(title);
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

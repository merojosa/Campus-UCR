package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public abstract class Place extends GeneralData implements Parcelable {

    public static final String TYPE_COFFEE = "Coffee";
    public static final String TYPE_FACULTY = "Faculty";
    public static final String TYPE_OFFICE = "Office";
    public static final String TYPE_PHOTOCOPIER = "Photocopier";
    public static final String TYPE_LIBRARY = "Library";
    public static final String TYPE_SCHOOL = "School";
    public static final String TYPE_SODA = "Soda";
    public static final String TYPE_PLACE = "Place";
    public static final String TYPE_BATHROOM = "Bathroom";
    public static final String TYPE_ASOCIATION = "Asociation";
    public static final String TYPE_LABORATORY = "Laboratory";

    public int id;
    public int rating;
    public int floor;
    public int image;
    public Boolean wifi;
    public Boolean haveComputers;
    public Boolean haveProjectors;
    public Boolean haveExtintors;
    public int capacity;
    public double latitude;
    public double longitude;
    public String name;
    public String description;
    public String type;
    public ArrayList<Comment> comments;

    Place() {
    }

    // Constructor de Coffee con Deployment Script
    protected Place(int id, String name, String description, int image, String type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(rating);
        dest.writeInt(floor);
        dest.writeInt(image);
        dest.writeByte((byte) (wifi == null ? 0 : wifi ? 1 : 2));
        dest.writeByte((byte) (haveComputers == null ? 0 : haveComputers ? 1 : 2));
        dest.writeByte((byte) (haveProjectors == null ? 0 : haveProjectors ? 1 : 2));
        dest.writeByte((byte) (haveExtintors == null ? 0 : haveExtintors ? 1 : 2));
        dest.writeInt(capacity);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(type);
        dest.writeTypedList(comments);
    }

    protected Place(Parcel in) {
        id = in.readInt();
        rating = in.readInt();
        floor = in.readInt();
        image = in.readInt();
        byte tmpWifi = in.readByte();
        wifi = tmpWifi == 0 ? null : tmpWifi == 1;
        byte tmpHaveComputers = in.readByte();
        haveComputers = tmpHaveComputers == 0 ? null : tmpHaveComputers == 1;
        byte tmpHaveProjectors = in.readByte();
        haveProjectors = tmpHaveProjectors == 0 ? null : tmpHaveProjectors == 1;
        byte tmpHaveExtintors = in.readByte();
        haveExtintors = tmpHaveExtintors == 0 ? null : tmpHaveExtintors == 1;
        capacity = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        description = in.readString();
        type = in.readString();
        comments = in.createTypedArrayList(Comment.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Boolean getWifi() {
        return wifi;
    }

    public void setWifi(Boolean wifi) {
        this.wifi = wifi;
    }

    public Boolean getHaveComputers() {
        return haveComputers;
    }

    public void setHaveComputers(Boolean haveComputers) {
        this.haveComputers = haveComputers;
    }

    public Boolean getHaveProjectors() {
        return haveProjectors;
    }

    public void setHaveProjectors(Boolean haveProjectors) {
        this.haveProjectors = haveProjectors;
    }

    public Boolean getHaveExtintors() {
        return haveExtintors;
    }

    public void setHaveExtintors(Boolean haveExtintors) {
        this.haveExtintors = haveExtintors;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Place) {
            Place other = (Place) obj;
            if (other != null)
                return this.id == other.id && this.name.equals(other.name) && this.description.equals(other.description)
                        && this.image == other.image && this.type.equals(other.type) && this.rating == other.rating &&
                        this.floor == other.floor && this.latitude == other.latitude && this.longitude == other.longitude &&
                        this.areEqualComments(other.comments);
            else
                return false;
        } else {
            return false;
        }
    }

    private boolean areEqualComments(ArrayList<Comment> otherComments) {
        if (this.comments == null && otherComments == null)
            return true;
        else {
            if (this.comments.size() != otherComments.size())
                return false;
            for (int i = 0; i < this.comments.size(); ++i) {
                for (int j = 0; j < otherComments.size(); ++j)
                    if (!this.comments.get(i).equals(otherComments.get(j)))
                        return false;
            }
        }
        return true;
    }
}

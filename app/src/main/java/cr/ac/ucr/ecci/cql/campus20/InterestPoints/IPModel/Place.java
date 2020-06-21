package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

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

    private boolean wifi;
    private int capacity;
    private boolean computers;
    private boolean projector;
    private boolean extintor;

    public static final String TYPE_COFFEE = "Coffee";
    public static final String TYPE_FACULTY = "Faculty";
    public static final String TYPE_OFFICE = "Office";
    public static final String TYPE_PHOTOCOPIER = "Photocopier";
    public static final String TYPE_LIBRARY = "Library";
    public static final String TYPE_SCHOOL = "School";
    public static final String TYPE_SODA = "Soda";
    public static final String TYPE_PLACE = "Place";
    public static final String TYPE_BATHROOM = "Bathroom";
    public static final String TYPE_ASOCIATION= "Asociation";
    public static final String TYPE_LABORATORY = "Laboratory";

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
    }

    //Para los laboratorios
    public Place(int id, String name, String description, String type, int floor, int capacity,
                 boolean wifi, boolean computers, boolean projector, boolean extintor) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.floor = floor;
        this.setCapacity(capacity);
        this.setWifi(wifi);
        this.setComputers(computers);
        this.setProjector(projector);
        this.setExtintor(extintor);

    }

    //Para los Cafés
    public Place(int id, String name, String description, int image, String type, boolean wifi) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.type = type;
        this.setWifi(wifi);
    }

    //Para los baños
    public Place(int id, String name, String description, String type, int floor, int capacity, boolean wifi) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.floor = floor;
        this.capacity = capacity;
        this.setWifi(wifi);
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
        this.comments = in.readArrayList(Comment.class.getClassLoader());
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

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isComputers() {
        return computers;
    }

    public void setComputers(boolean computers) {
        this.computers = computers;
    }

    public boolean isProjector() {
        return projector;
    }

    public void setProjector(boolean projector) {
        this.projector = projector;
    }

    public boolean isExtintor() {
        return extintor;
    }

    public void setExtintor(boolean extintor) {
        this.extintor = extintor;
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

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Place) {
            Place other = (Place) obj;
            if (other != null)
                return this.id == other.id && this.name.equals(other.name) && this.description.equals(other.description)
                        && this.image == other.image && this.type.equals(other.type) && this.rating == other.rating &&
                        this.floor == other.floor && this.latitude == other.latitude && this.longitude == other.longitude &&
                        this.areEqualComments(other.comments);
            else
                return false;
        }else {
            return false;
        }
    }

    private boolean areEqualComments(ArrayList<Comment> otherComments){
        if(this.comments == null && otherComments == null)
            return true;
        else{
            if(this.comments.size() != otherComments.size())
                return false;
            for(int i = 0; i < this.comments.size(); ++i){
                for(int j = 0; j < otherComments.size(); ++j)
                    if(!this.comments.get(i).equals(otherComments.get(j)))
                        return false;
            }
        }
        return true;
    }

}

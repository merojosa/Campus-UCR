package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;

public class Comment extends GeneralData implements Parcelable {

    private int id;
    private int id_place_fk;
    private String type;
    private String description;
    private String date;
    private float cRating;
    private String photo_path;
    private int like;
    private int dislike;

    public Comment() {}

    public Comment(int id, int id_place_fk, String type, String description, String date, float cRating, String photo_path, int like, int dislike) {
        this.id = id;
        this.id_place_fk = id_place_fk;
        this.type = type;
        this.description = description;
        this.date = date;
        this.cRating = cRating;
        this.photo_path = photo_path;
        this.like = like;
        this.dislike = dislike;
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        id_place_fk = in.readInt();
        type = in.readString();
        description = in.readString();
        date = in.readString();
        cRating = in.readFloat();
        photo_path = in.readString();
        like = in.readInt();
        dislike = in.readInt();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setcRating(float cRrating) {
        this.cRating = cRating;
    }

    public String getPhotoPath() { return photo_path; }

    public void setPhotoPath(String photo_path) {
        this.photo_path = photo_path;
    }

    public int getLike(){return like;}

    public void setLike(int like){ this.like =like;}

    public int getDislike(){return dislike;}

    public void setDislike(int dislike) {this.dislike = dislike;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(id_place_fk);
        dest.writeString(type);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeFloat(cRating);
        dest.writeString(photo_path);
        dest.writeInt(like);
        dest.writeInt(dislike);
    }
}

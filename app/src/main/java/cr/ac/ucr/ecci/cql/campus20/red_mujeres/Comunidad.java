package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Parcel;
import android.os.Parcelable;

public class Comunidad implements Parcelable
{
    private int communityImgRes;
    private String communityName;
    private String communityNoMemebers;
    private String communityDescription;

    public Comunidad(int communityImgRes, String communityName, String communityNoMemebers, String communityDescription) {
        this.communityImgRes = communityImgRes;
        this.communityName = communityName;
        this.communityNoMemebers = communityNoMemebers;
        this.communityDescription = communityDescription;
    }

    protected Comunidad(Parcel in) {
        communityImgRes = in.readInt();
        communityName = in.readString();
        communityNoMemebers = in.readString();
        communityDescription = in.readString();
    }

    public static final Creator<Comunidad> CREATOR = new Creator<Comunidad>() {
        @Override
        public Comunidad createFromParcel(Parcel in) {
            return new Comunidad(in);
        }

        @Override
        public Comunidad[] newArray(int size) {
            return new Comunidad[size];
        }
    };

    public int getCommunityImgRes() {
        return communityImgRes;
    }

    public void setCommunityImgRes(int communityImgRes) {
        this.communityImgRes = communityImgRes;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityNoMemebers() {
        return communityNoMemebers;
    }

    public void setCommunityNoMemebers(String communityNoMemebers) {
        this.communityNoMemebers = communityNoMemebers;
    }

    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.communityImgRes);
        dest.writeString(this.communityName);
        dest.writeString(this.communityNoMemebers);
        dest.writeString(this.communityDescription);
    }
}

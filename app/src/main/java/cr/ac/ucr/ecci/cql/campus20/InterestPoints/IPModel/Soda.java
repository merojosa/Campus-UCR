package cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Soda extends Place implements Parcelable {

    private String horario;
    private String oferta;
    private int express;

    public Soda() {
        super();
        super.setType(TYPE_SODA);
    }

    public Soda(int id, String name, String description, int image, double latitude, double longitude, Boolean wifi, String horario, String oferta, int express) {
        super(id, name, description, image, TYPE_SODA);
        super.setLatitude(latitude);
        super.setLongitude(longitude);
        super.setWifi(wifi);
        this.horario = horario;
        this.oferta = oferta;
        this.express = express;
    }


    public static final Creator<Soda> CREATOR = new Creator<Soda>() {
        public Soda createFromParcel(Parcel in) {
            return new Soda(in);
        }

        public Soda[] newArray(int size) {
            return new Soda[size];
        }
    };

    public Soda(Parcel in) {
        super(in);
        horario = in.readString();
        oferta = in.readString();
        express = in.readInt();
        super.setType(TYPE_SODA);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(horario);
        dest.writeString(oferta);
        dest.writeInt(express);
    }
}

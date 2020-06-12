package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class SodaCard implements Parcelable
{
    private int id;
    private String firebaseId;
    private String nombre;
    private String horario;
    private String foto;
    private double rating;
    private double latitud;
    private double longitud;

    private int maxCapacity;
    private int availableCapacity;

    public SodaCard(int id, String firebaseId, String nombre, String foto, String horario, double rating, double latitud, double longitud, int capacity, int capacity_max)
    {
        this.id = id;
        this.firebaseId = firebaseId;
        this.nombre = nombre;
        this.foto = foto;
        this.horario = horario;
        this.rating = rating;
        this.latitud = latitud;
        this.longitud = longitud;
        this.availableCapacity = capacity;
        this.maxCapacity = capacity_max;
    }

    protected SodaCard(Parcel in) {
        id = in.readInt();
        firebaseId = in.readString();
        nombre = in.readString();
        horario = in.readString();
        foto = in.readString();
        rating = in.readDouble();
        latitud = in.readDouble();
        longitud = in.readDouble();
        maxCapacity = in.readInt();
        availableCapacity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(firebaseId);
        dest.writeString(nombre);
        dest.writeString(horario);
        dest.writeString(foto);
        dest.writeDouble(rating);
        dest.writeDouble(latitud);
        dest.writeDouble(longitud);
        dest.writeInt(maxCapacity);
        dest.writeInt(availableCapacity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SodaCard> CREATOR = new Creator<SodaCard>() {
        @Override
        public SodaCard createFromParcel(Parcel in) {
            return new SodaCard(in);
        }

        @Override
        public SodaCard[] newArray(int size) {
            return new SodaCard[size];
        }
    };

    public int getId() { return id; }

    public String getHorario() {
        return this.horario;
    }

    public void setHorario(String id) {
        this.horario = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() { return foto; }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public double getRating() { return rating; }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getLatitud() { return latitud; }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }

    public void setLongitud(double longitud) { this.longitud = longitud; }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(String firebaseId) {
        this.firebaseId = firebaseId;
    }
}

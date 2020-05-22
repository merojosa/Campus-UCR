package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.os.Parcel;
import android.os.Parcelable;

public class SodaCard implements Parcelable
{
    int id;
    private String nombre;
    private String horario;
    private String foto;
    private double rating;

    public SodaCard(int id, String nombre, String foto, String horario, double rating)
    {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.horario = horario;
        this.rating = rating;
    }

    protected SodaCard(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        foto = in.readString();
        horario = in.readString();
        rating = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(foto);
        dest.writeString(horario);
        dest.writeDouble(rating);
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
}

package cr.ac.ucr.ecci.cql.campus20.foro_general;

import android.os.Parcel;
import android.os.Parcelable;

public class PreguntaCard implements Parcelable
{
    private int id;
    private int temaID;
    private String nombreUsuario;
    private String texto;
    private int contadorLikes;
    private int contadorDislikes;
    private int resuelta;

    public PreguntaCard(int temaID, int id, String nombreUsuario, String texto, int contadorLikes, int contadorDislikes, int resuelta)
    {
        this.temaID = temaID;
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.texto = texto;
        this.contadorLikes = contadorLikes;
        this.contadorDislikes = contadorDislikes;
        this.resuelta = resuelta;
    }

    protected PreguntaCard(Parcel in) {
        id = in.readInt();
        temaID = in.readInt();
        nombreUsuario = in.readString();
        texto = in.readString();
        contadorLikes = in.readInt();
        contadorDislikes = in.readInt();
        resuelta = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(temaID);
        dest.writeString(nombreUsuario);
        dest.writeString(texto);
        dest.writeInt(contadorLikes);
        dest.writeInt(contadorDislikes);
        dest.writeInt(resuelta);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreguntaCard> CREATOR = new Creator<PreguntaCard>() {
        @Override
        public PreguntaCard createFromParcel(Parcel in) {
            return new PreguntaCard(in);
        }

        @Override
        public PreguntaCard[] newArray(int size) {
            return new PreguntaCard[size];
        }
    };

    public int getId() {
        return id;
    }

    public int getTemaID() {
        return temaID;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public int getContadorLikes() {
        return contadorLikes;
    }

    public int getContadorDislikes() {
        return contadorDislikes;
    }

    public int getResuelta() {
        return resuelta;
    }
}

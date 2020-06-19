package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

@Entity(tableName = "Pregunta",
        foreignKeys = @ForeignKey(entity = Tema.class,
        parentColumns = "id",
        childColumns = "temaID",
        onDelete = ForeignKey.RESTRICT))
public class Pregunta {

     // Incrementa solo el id
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id = 0;

    @NonNull
    @ColumnInfo(name="nombreUsuario")
    public String nombreUsuario;

    @ColumnInfo(name = "temaID")
    public int temaID = 0;

    @ColumnInfo(name = "texto")
    public String texto = "";

    @ColumnInfo(name = "contadorLikes")
    public int contadorLikes = 0;

    @ColumnInfo(name = "contadorDisLikes")
    public int contadorDisLikes = 0;

    public Pregunta(int id, String nombreUsuario, int temaID, String texto, int contadorLikes, int contadorDisLikes) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.temaID = temaID;
        this.texto = texto;
        this.contadorLikes = contadorLikes;
        this.contadorDisLikes = contadorDisLikes;
    }

    // Getters y Setters de la clase

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getTemaID() {
        return temaID;
    }

    public void setTemaID(int temaID) {
        this.temaID = temaID;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getContadorLikes() {
        return contadorLikes;
    }

    public void setContadorLikes(int contadorLikes) {
        this.contadorLikes = contadorLikes;
    }

    public int getContadorDisLikes() {
        return contadorDisLikes;
    }

    public void setContadorDisLikes(int contadorDisLikes) {
        this.contadorDisLikes = contadorDisLikes;
    }

    public int getRanking(){
        return this.contadorLikes - this.contadorDisLikes;
    }
}

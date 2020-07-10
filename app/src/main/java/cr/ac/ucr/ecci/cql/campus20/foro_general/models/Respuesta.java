package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Respuesta",
        foreignKeys = {
                @ForeignKey(entity = Pregunta.class,
                        parentColumns = "id",
                        childColumns = "preguntaID",
                        onDelete = ForeignKey.RESTRICT
                ),
                @ForeignKey(entity = Tema.class,
                        parentColumns = "id",
                        childColumns = "temaID",
                        onDelete = ForeignKey.RESTRICT
                )
        })

public class Respuesta {
    @PrimaryKey(autoGenerate = true)//Auto incremental
    @ColumnInfo(name = "id")
    public int id;

    @NonNull
    @ColumnInfo(name="nombreUsuario")
    public String nombreUsuario;

    @ColumnInfo(name = "texto")
    public String texto;

    @ColumnInfo(name = "preguntaID")
    public int preguntaID;

    @ColumnInfo(name = "temaID")
    public int temaID;

    @ColumnInfo(name = "cantidad_likes")
    public int contadorLikes = 0;

    @ColumnInfo(name = "cantidad_dislikes")
    public int contadorDislikes = 0;

    public int notificada = 0;

    public Respuesta(int id, String nombreUsuario, String texto, int preguntaID, int temaID, int contadorLikes, int contadorDislikes) {
        //public Respuesta(int id, String texto, int preguntaID, int contadorLikes, int contadorDislikes) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.texto = texto;
        this.preguntaID = preguntaID;
        this.temaID = temaID;
        this.contadorLikes = contadorLikes;
        this.contadorDislikes = contadorDislikes;
        this.notificada = 0;
    }

    //getters y setters

    public int getId() {
        return id;
    }

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getPreguntaID() {
        return preguntaID;
    }

    public int getTemaID() {
        return temaID;
    }

    public int getContadorLikes() {
        return contadorLikes;
    }

    public void setContadorLikes(int contadorLikes) {
        this.contadorLikes = contadorLikes;
    }

    public int getContadorDislikes() {
        return contadorDislikes;
    }

    public void setContadorDislikes(int contadorDislikes) {
        this.contadorDislikes = contadorDislikes;
    }

    public int getRanking(){
        return this.contadorLikes - this.contadorDislikes;
    }
}

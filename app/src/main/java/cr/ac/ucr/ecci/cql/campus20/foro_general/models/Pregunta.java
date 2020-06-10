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

    @ColumnInfo(name = "contadorDisikes")
    public int contadorDisikes = 0;

    public Pregunta(int id, String nombreUsuario, int temaID, String texto, int contadorLikes, int contadorDisikes) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.temaID = temaID;
        this.texto = texto;
        this.contadorLikes = contadorLikes;
        this.contadorDisikes = contadorDisikes;
    }
}

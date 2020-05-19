package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.ForeignKey;

import java.util.ArrayList;

@Entity(tableName = "Pregunta", foreignKeys = @ForeignKey(entity = Tema.class,
        parentColumns = "id",
        childColumns = "temaID",
        onDelete = ForeignKey.RESTRICT))
public class Pregunta {

    @PrimaryKey(autoGenerate = true) // Incrementa solo el id
    @ColumnInfo(name = "id")
    public int id = 0;

    @ColumnInfo(name = "temaID")
    public int temaID = 0;

    @ColumnInfo(name = "texto")
    public String texto = "";

    @ColumnInfo(name = "contadorLikes")
    public int contadorLikes = 0;

    @ColumnInfo(name = "contadorDisikes")
    public int contadorDisikes = 0;

    public Pregunta(int id, int temaID, String texto, int contadorLikes, int contadorDisikes) {
        this.id = id;
        this.temaID = temaID;
        this.texto = texto;
        this.contadorLikes = contadorLikes;
        this.contadorDisikes = contadorDisikes;
    }
}

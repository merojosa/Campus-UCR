package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favoritos_table",
foreignKeys = @ForeignKey(entity = Tema.class,
parentColumns = "id",
childColumns = "IdTema",
onDelete = ForeignKey.CASCADE))
public class Favorito {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name="IdTema")
    public int idTema;

    // Constructor
    public Favorito(@NonNull int idTema)
    {
        this.idTema = idTema;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }
}

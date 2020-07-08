package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Favoritos_table",
        primaryKeys = {"IdTema", "nombreUsuario"},
foreignKeys = @ForeignKey(entity = Tema.class,
parentColumns = "id",
childColumns = "IdTema",
onDelete = ForeignKey.CASCADE))
public class Favorito {

    @NonNull
    @ColumnInfo(name="IdTema")
    public int idTema;


    @NonNull
    @ColumnInfo(name="nombreUsuario")
    public String nombreUsuario;

    // Constructor
    public Favorito(@NonNull int idTema, @NonNull String nombreUsuario)
    {
        this.idTema = idTema;
        this.nombreUsuario = nombreUsuario;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    @NonNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NonNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

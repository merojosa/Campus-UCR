package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Rank_preguntas", primaryKeys = {"IdPreg","NombreUsuario"})
public class RankPregunta {

    @NonNull
    @ColumnInfo(name="IdPreg")
    public int idPreg;

    @NonNull
    @ColumnInfo(name="NombreUsuario")
    public String nombreUsuario;

    //2 disliked, 1 liked
    @NonNull
    @ColumnInfo(name="IsLiked")
    public int isLiked;

    // Constructor
    public RankPregunta(@NonNull int idPreg, @NonNull String nombreUsuario, @NonNull int isLiked)
    {
        this.idPreg = idPreg;
        this.nombreUsuario = nombreUsuario;
        this.isLiked = isLiked;
    }

    public int getIdPreg() {
        return idPreg;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIdPreg(int idPreg) {
        this.idPreg = idPreg;
    }

    public void setIsLiked(int isLiked) {
        this.isLiked = isLiked;
    }

    @NotNull
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(@NotNull String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}

package cr.ac.ucr.ecci.cql.campus20.foro_general.models;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Rank_respuestas", primaryKeys = {"IdResp","NombreUsuario"})
public class RankRespuesta {

    @NonNull
    @ColumnInfo(name="IdResp")
    public int idResp;

    @NonNull
    @ColumnInfo(name="NombreUsuario")
    public String nombreUsuario;

    //2 disliked, 1 liked
    @NonNull
    @ColumnInfo(name="IsLiked")
    public int isLiked;

    // Constructor
    public RankRespuesta(@NonNull int idResp, @NonNull String nombreUsuario, @NonNull int isLiked)
    {
        this.idResp = idResp;
        this.nombreUsuario = nombreUsuario;
        this.isLiked = isLiked;
    }

    public int getIdResp() {
        return idResp;
    }

    public int getIsLiked() {
        return isLiked;
    }

    public void setIdResp(int idResp) {
        this.idResp = idResp;
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
package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
/**
 * Clase para representar una comunidad o grupo
 * */
import android.content.Context;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class Comunidad implements Parcelable
{
    //Atributos que conforman una comunidad
    private int communityImgRes;                //Almacena la imagen asociada a una comunidad
    private String communityName;               //Almacena el nombre una comunidad
    private String communityNoMembers;          //Contiene el número de miembros de la comunidad
    private ArrayList<String> communityMembers; //Contiene una lista con los usuarios que pertenecen a una comunidad
    private String communityDescription;        //Almacena la descripción de una comunidad

    //Método constructor
    public Comunidad(int communityImgRes, String communityName, String communityNoMembers, ArrayList<String> communityMembers, String communityDescription) {
        this.communityImgRes = communityImgRes;
        this.communityName = communityName;
        this.communityNoMembers = communityNoMembers;
        this.communityMembers = communityMembers;
        this.communityDescription = communityDescription;
    }

    //Constructor para la parcelización del objeto
    protected Comunidad(Parcel in) {
        communityImgRes = in.readInt();
        communityName = in.readString();
        communityNoMembers = in.readString();
        communityMembers = in.readArrayList(null);
        communityDescription = in.readString();
    }

    public static final Creator<Comunidad> CREATOR = new Creator<Comunidad>() {
        @Override
        public Comunidad createFromParcel(Parcel in) {
            return new Comunidad(in);
        }

        @Override
        public Comunidad[] newArray(int size) {
            return new Comunidad[size];
        }
    };

    //Getters y setters de la clase
    public int getCommunityImgRes() {
        return communityImgRes;
    }

    public void setCommunityImgRes(int communityImgRes) {
        this.communityImgRes = communityImgRes;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityNoMembers() {
        return communityNoMembers;
    }

    public void setCommunityNoMembers(String communityNoMembers) {
        this.communityNoMembers = communityNoMembers;
    }

    public ArrayList<String> getCommunityMembers() {
        return communityMembers;
    }

    public void setCommunityMembers(ArrayList<String> communityMembers) {
        this.communityMembers = communityMembers;
    }

    public String getCommunityDescription() {
        return communityDescription;
    }

    public void setCommunityDescription(String communityDescription) {
        this.communityDescription = communityDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.communityImgRes);
        dest.writeString(this.communityName);
        dest.writeString(this.communityNoMembers);
        dest.writeList(this.communityMembers);
        dest.writeString(this.communityDescription);
    }

    //Método para registrar la unión del usuario a una comunidad
    public void Unirse(Context context, String usuarioID, String usuarioNombre, String unionMotivo)
    {
        Toast.makeText(context, "Solicitud de unión a " + this.getCommunityName() + " enviada", Toast.LENGTH_SHORT).show();
    }

}

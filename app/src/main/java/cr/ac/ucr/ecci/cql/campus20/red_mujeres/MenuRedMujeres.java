package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
import cr.ac.ucr.ecci.cql.campus20.FirebaseBD;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.LoginBD;
import cr.ac.ucr.ecci.cql.campus20.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.*;

public class MenuRedMujeres extends AppCompatActivity {
    private LoginBD usuario = new FirebaseBD();
    private String correo = usuario.obtenerCorreoActual();
    public final ArrayList<Map<String,Object>> arr;
    public final ArrayList<Map<String,Object>> userArr;
    public DatabaseReference grupo;
    public DatabaseReference usuarios;
    private FirebaseDatabase mDatabase;

    public MenuRedMujeres() {
        this.arr = new ArrayList<>();
        this.userArr = new ArrayList<>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_red_mujeres);

        mDatabase = FirebaseDatabase.getInstance();
        fetchGroupAsync("GrupoEj");
        startActivity(new Intent(MenuRedMujeres.this, MainRedMujeres.class));


    }

    private void popupRegistro() {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Oh-uh!");
        builder.setMessage("Parece que no has enviado la solicitud de registro para unirte a la Red de Mujeres.");

        String positiveText = "Enviar Solicitud";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion();
                    }
                });

        String negativeText = "Cancelar";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // En este momento al cancelar el popup se "devuelve" a la pagina principal de Puntos de Interes
                        // porque no hay pagina principal, no pude volver al login, y es la mas bonita.
                        startActivity(new Intent(MenuRedMujeres.this, InterestPointsActivity.class));
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private boolean validarUsuario() {
        boolean validado;

        // revisar si solicitud fue aceptada

        // si si
            // validado = true;

        // si no
            // proceso de validacion...

            // si fue aceptado
            // enviarConfirmacion();
            // validado = true;
            // guardar que el usuario fue aceptado

            // sino
                // guardar que el usuario fue rechazado
            validado = false;

        return validado;
    }


    private void enviarConfirmacion() {
        Context context = getApplicationContext();
        CharSequence text = correo;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        List<String> toEmailList = Arrays.asList(correo); //Lista de remitentes en caso de que se ocupe enviar a un grupo de correos
        try {
            new SendMailTask(MenuRedMujeres.this).execute("campus.virtual.ucr@gmail.com", //remitente
                    "Campus2020", //contraseña remitente
                     toEmailList, //lista de destinatarios
                    "Confirmacion Red Mujeres", //asunto
                    "Su solicitud para unirse a la red de mujeres ha sido aceptada. Ahora puede acceder a grupos de confianza desde la aplicacion CampusUCR."); //mensaje en el cuerpo
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }
    }

    public void  fetchGroupUsersAsync(String id) {
        usuarios = mDatabase.getReference("usuarios_red_mujeres").child(id);
        readGroupUsersData(new FireBaseRedMujeres.FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                System.out.println(list);
            }
        });
    }
    public void  fetchGroupAsync(String nombreGrupo) {
        grupo = mDatabase.getReference(nombreGrupo);
        readGroupData(new FireBaseRedMujeres.FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                Map<String, Object> map = list.get(0);
                ArrayList<Integer> users = (ArrayList<Integer>)map.get("IDusuarios");

                for(int i = 0 ; i< users.size();++i){
                    fetchGroupUsersAsync(""+users.get(i));
                }
            }
        });
    }

    public  void readGroupData(FireBaseRedMujeres.FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                arr.add(list);
                firebaseCallBack.onCallBack(arr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        grupo.addListenerForSingleValueEvent(valueEventListener);

    }

    public  void readGroupUsersData( FireBaseRedMujeres.FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                userArr.add(list);
                firebaseCallBack.onCallBack(userArr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usuarios.addListenerForSingleValueEvent(valueEventListener);
    }

}

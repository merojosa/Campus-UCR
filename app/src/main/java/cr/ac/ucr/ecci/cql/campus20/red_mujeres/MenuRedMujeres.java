package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
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

    // 1 Diana validada
    // 2 Denisse validada
    // 3 Berta no validada
    private static final String currentUser = "3";
    //para pruebas a futuro se debe ligar con el usuario actual de la pplicacion

    private FirebaseDatabase mDatabase;
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();
    private String correo = bd.obtenerCorreoActual();

    //Estructuras de datos necesarias
    List<String> comunidadesUsuario = new ArrayList<String>();
    List<String> comunidadesTotales = new ArrayList<String>();

    public final ArrayList<Map<String,Object>> groupArr;
    public final ArrayList<Map<String,Object>> userArr;
    public DatabaseReference grupo;
    public DatabaseReference usuarios;


    public MenuRedMujeres() {
        this.groupArr = new ArrayList<>();
        this.userArr = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_red_mujeres);

        mDatabase = FirebaseDatabase.getInstance();
        recuperarDatos(currentUser);

    }

    private void recuperarDatos(String currentUserID) {

        DatabaseReference root = mDatabase.getReference();

        bd.autCallback(root, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {

                // recupera atributo de validacion del usuario actual.
                boolean val = (boolean) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Validado").getValue();

                // revisa si el usuario ya ha sido validado
                if (val) {
                    // si si continue

                    //recupera grupos a los que pertenece el usuario actual.
                    for (DataSnapshot snapshot : dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Grupos").getChildren()) {
                        comunidadesUsuario.add( (String) snapshot.getValue());
                    }
                    comunidadesUsuario(comunidadesUsuario);


                    //recupera todas las comunidades
                    for (DataSnapshot snapshot : dataSnapshot.child("Comunidades").getChildren()) {
                        comunidadesTotales.add( (String) snapshot.child("Nombre").getValue());
                    }
                    comunidadesTotales(comunidadesTotales);


                    //Manipulacion de datos del snapshot y llamados a metodos.
                    // ...







                    // Despues de recuperar todos los datos necesarios, se llama a la actividad de comunidades
                    startActivity(new Intent(MenuRedMujeres.this, ComunidadesRedMujeres.class));


                    // usuario no ha sido validado
                } else {
                    // si no muestre popup
                    String nombre = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Nombre").getValue();
                    popupRegistro(nombre);

                }
            }
            @Override
            public void fallo(DatabaseError databaseError) {}
        });

    }

    private void procesarValidacion() {

        // proceso de validacion temporal
        // pregunta al admin si quiere validar al usuario

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Psss Admin");
        builder.setMessage("Quiere validar a este mae?");

        String positiveText = "Sure why not";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion(true);
                        // write 1 en la bd

                        //continua a actividad
                        startActivity(new Intent(MenuRedMujeres.this, ComunidadesRedMujeres.class));
                    }
                });

        String negativeText = "Safo safo";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarConfirmacion(false);
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void popupRegistro(String nombre) {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuRedMujeres.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Oh-uh " + nombre + "!");
        builder.setMessage("Parece que no has enviado la solicitud de registro para unirte a la Red de Mujeres.");

        String positiveText = "Enviar Solicitud";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // proceso de validacion
                        procesarValidacion();
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

    private void enviarConfirmacion(boolean aceptado) {
        Context context = getApplicationContext();
        CharSequence text = correo;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        List<String> toEmailList = Arrays.asList(correo); //Lista de remitentes en caso de que se ocupe enviar a un grupo de correos

        String asunto = "";
        String cuerpo = "";

        if(aceptado) {
            asunto = "Confirmacion Red Mujeres";
            cuerpo = "Su solicitud para unirse a la red de mujeres ha sido aceptada. Ahora puede acceder a grupos de confianza desde la aplicacion CampusUCR.";
        } else {
            asunto = "Respuesta Solicitud Red Mujeres";
            cuerpo = "Lo sentimos, su solicitud para unirse a la red de mujeres ha sido rechazada.";
        }

        try {
            new SendMailTask(MenuRedMujeres.this).execute("campus.virtual.ucr@gmail.com", //remitente
                    "Campus2020", //contraseña remitente
                    toEmailList, //lista de destinatarios
                    asunto, //asunto
                    cuerpo); //mensaje en el cuerpo
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }
    }

    public void comunidadesUsuario (List<String> list) {
        for(String s:list){
            System.out.println(s);
        }
    }

    public void comunidadesTotales (List<String> list) {
        for(String s:list){
            System.out.println(s);
        }
    }
    //recupera toda la informacion relacionada a los grupos

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
        grupo = mDatabase.getReference("Comunidades").child(nombreGrupo);
        readGroupData(new FireBaseRedMujeres.FirebaseCallBack() {
            @Override
            public void onCallBack(ArrayList<Map<String, Object>> list) {
                Map<String, Object> map = list.get(0);
                //Sacamamos del mapa los usarios pertenecientes al grupo
                ArrayList<Integer> users = (ArrayList<Integer>)map.get("IDusuarios");

                for(int i = 0 ; i< users.size();++i){
                    //Recuperamos la informacion de los integrantes del grupo
                    fetchGroupUsersAsync(""+users.get(i));
                }
            }
        });
    }

    //Obtiene el json especifico para la referencia a la base de datos en el nodo del grupo especifcado
    public  void readGroupData(FireBaseRedMujeres.FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
                Map<String,Object> list = (HashMap<String, Object>) dataSnapshot.getValue();
                groupArr.add(list);
                firebaseCallBack.onCallBack(groupArr);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        grupo.addListenerForSingleValueEvent(valueEventListener);

    }
    //Obtiene el json especifico para la referencia a la base de datos en el nodo del usuario especifcado
    public  void readGroupUsersData( FireBaseRedMujeres.FirebaseCallBack firebaseCallBack){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Json se comporta como un mapa KEY = nombre atributo, Value = valor
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


package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class MenuRedMujeres extends AppCompatActivity {

    // 1 Diana validada
    // 2 Denisse validada
    // 3 Berta  validada
    // 4 Julio no validado
    // 5 Claudia no validada
    // 6 Juan no validado
    private static String currentUser;
    //para pruebas a futuro se debe ligar con el usuario actual de la aplicacion

    private FirebaseDatabase mDatabase;
    private FireBaseRedMujeres bd = new FireBaseRedMujeres();
    private String correo = bd.obtenerCorreoActual();

    //Estructuras de datos necesarias
    public final ArrayList<String> comunidadesUsuario;
    public final ArrayList<String> comunidadesTotales;
    public final ArrayList<Map<String,Object>> groupArr;
    public final ArrayList<Map<String,Object>> userArr;

    //Referencias a BD
    public DatabaseReference grupo;
    public DatabaseReference usuarios;

    public MenuRedMujeres() {
        this.groupArr = new ArrayList<>();
        this.userArr = new ArrayList<>();
        this.comunidadesUsuario = new ArrayList<String>();
        this.comunidadesTotales = new ArrayList<String>();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDatabase = FirebaseDatabase.getInstance();
        recuperarId();
    }



    public String getCurrentUserID(){
        return currentUser;
    }

    void recuperarId() {
        DatabaseReference root = mDatabase.getReference();

        bd.autCallback(root, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {
                int i = 1; //Id del current user
                String correoMujeres;
                for (DataSnapshot snapshot : dataSnapshot.child("usuarios_red_mujeres").getChildren()) {
                    correoMujeres = (String) snapshot.child("CorreoUCR").getValue(); //Correo bd red mujeres
                    if (correoMujeres.equals(correo)) { //Correo de logueo
                        currentUser = Integer.toString(i);
                    }
                    i += 1;
                }
                recuperarDatos(currentUser); //Validacion
            }
            @Override
            public void fallo(DatabaseError databaseError) {}
        });
    }



    private void recuperarDatos(String currentUserID) {

        DatabaseReference root = mDatabase.getReference();

        bd.autCallback(root, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {


            // Manipulacion de datos del snapshot y llamados a metodos sin importar si el usuario no esta validado.
                //recupera todas las comunidades
                for (DataSnapshot snapshot : dataSnapshot.child("Comunidades").getChildren()) {
                    comunidadesTotales.add( (String) snapshot.child("Nombre").getValue());
                }
                comunidadesTotales(comunidadesTotales);



            //Manipulacion de datos del snapshot y llamados a metodos si el usuario esta validado.

                // recupera atributo de validacion del usuario actual.
                boolean val = (boolean) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Validado").getValue();
                String nombre = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Nombre").getValue();

                // revisa si el usuario ya ha sido validado
                if (val) {
                    //recupera grupos a los que pertenece el usuario actual.
                    for (DataSnapshot snapshot : dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Grupos").getChildren()) {
                        comunidadesUsuario.add( (String) snapshot.getValue());
                    }
                    comunidadesUsuario(comunidadesUsuario);



                    // Despues de recuperar todos los datos necesarios, se llama a la actividad de comunidades
                    continuarActividad(false, nombre);



            // Manipulacion de datos del snapshot y llamados a metodos si el no esta validado.
                } else {
                    // si no muestre popup
                    popupRegistro(nombre, currentUserID);
                }
            }
            @Override
            public void fallo(DatabaseError databaseError) {}
        });

    }

    // Continua con la actividad de comunidades dependiendo si el usuario es nuevo o no.
    private void continuarActividad(boolean usuarioNuevo, String nombre) {
        if (!usuarioNuevo) {
            startActivity(new Intent(MenuRedMujeres.this, MisComunidades.class)
                    .putStringArrayListExtra("misComunidades", comunidadesUsuario)          //Envío de las comunidades a las que pertenece el usuario actual
                    .putStringArrayListExtra("comunidadesTotales", comunidadesTotales)      //Envío de todos las comunidades
                    .putExtra("userID", currentUser)                                        //Envío del ID del usuario actual
                    .putExtra("userName", nombre));                                         //Envío del nombre del usuario actual
        } else {
            startActivity(new Intent(MenuRedMujeres.this, ComunidadesRedMujeres.class).putStringArrayListExtra("comunidadesTotales", comunidadesTotales));
        }
    }

    // Proceso de validación. Envía una solicited con los datos del usuario al administrador
    // por correo, y este puede aprobar o rechazar la solicitud
    // Modifica el valor del atributo SolicitudEnviada del usuario en cuestión
    private void procesarValidacion(String currentUserID) {

        DatabaseReference usuario = mDatabase.getReference();

        bd.autCallback(usuario, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {

                String nombre = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Nombre").getValue();
                String genero = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Género").getValue();
                String carne = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Carne").getValue();
                String correo = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("CorreoUCR").getValue();

                // Envía solicitud y actualiza atributo SolicitudEnviada
                enviarSolicitudAdmin(nombre, genero, carne, correo);
                usuario.child("usuarios_red_mujeres").child(currentUserID).child("SolicitudEnviada").setValue(true);

                //Método básico y ***temporal*** de verificación
                verificarSolicitud(currentUserID);

                // Vuelve a la pagina principal de UCR Eats,
                // dado que no existe página inicial per se y la app se inicializa en esta
                startActivity(new Intent(MenuRedMujeres.this, InterestPointsActivity.class));
            }

            @Override
            public void fallo(DatabaseError databaseError) {}
        });
    }

    // Verificación temporal. Acepta solicitudes tras revisar:
    // 1. Que el usuario no esté validado
    // 2. Que el usuario haya envidado una solicitud
    // 3. Que el usuario sea mujer
    // Actualiza la BD acorde a esto
    public void verificarSolicitud(String currentUserID){

        DatabaseReference usuario = mDatabase.getReference();

        bd.autCallback(usuario, new FirebaseListener() {
            @Override
            public void exito(DataSnapshot dataSnapshot) {

                boolean validado = (boolean) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Validado").getValue();
                boolean solicitudEnviada = (boolean) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("SolicitudEnviada").getValue();
                String genero = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("Género").getValue();
                String correoUCR = (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUserID).child("CorreoUCR").getValue();

                // Si el usuario aún no ha sido validado y recientemente envió una solicitud
                if (validado == false) {
                    if(solicitudEnviada == true){
                        // Verifica género de usuario
                        if(genero.equals("Mujer")){
                            // Acepta solicitud
                            usuario.child("usuarios_red_mujeres").child(currentUserID).child("Validado").setValue(true);
                            // Enviar confirmación por correo
                            enviarConfirmacion(true, correoUCR);

                        }
                        else{
                            // Rechaza solicitud
                            // Mantiene en true el valor de SolicitudEnviada para denotar que
                            // el usuario ha sido rechazado
                            usuario.child("usuarios_red_mujeres").child(currentUserID).child("Validado").setValue(false);
                            // Enviar confirmación por correo
                            enviarConfirmacion(false, correoUCR);
                        }
                    }
                }

            }
            @Override
            public void fallo(DatabaseError databaseError) {}
        });


    }

    // Método que compone el correo de solicitud, lo llena con datos del usuario recuperados de BD
    // Y utiliza SendMailTask para enviarlo al administrador para revisión
    // Modifica el valor del atributo SolicitudEnviada del usuario en cuestión
    public void enviarSolicitudAdmin(String nombre, String genero, String carne, String correo) {
        List<String> toEmailList = new ArrayList<>();
        toEmailList.add("admredmujeres@gmail.com");

        String asunto = "Pendiente revisión: Nueva solicitud de unión a Red de Mujeres";
        String cuerpo = "Hola Admin, recientemente el/la estudiante " + nombre +
                " ha enviado una solicitud para unirse al módulo de Red de Mujeres." + "<br/>" +
                "Su información es la siguiente: " + "<br/>" +
                "Nombre: " + nombre + "<br/>" +
                "Carne: " + carne + "<br/>" +
                "Género: " + genero + "<br/>" +
                "Correo Institucional: " + correo + "<br/>" +
                "Favor aceptar o denegar esta solicitud. ";

        //Envío de correo de solicitud a cuenta de admin, desde correo oficial de Campus UCR
        try {
            new SendMailTask(MenuRedMujeres.this).execute("campus.virtual.ucr@gmail.com", //remitente
                    "Campus2020", //contraseña remitente
                    toEmailList, //lista de destinatarios
                    asunto, //asunto
                    cuerpo); //mensaje en el cuerpo
        } catch (Exception e) {
            Log.i("Excepcion", e.getMessage());
        }

        //Otro método para enviar solicitudes, permite a los estudiantes modificar el cuerpo del correo
        //De esta manera, se manda la solicitud desde una dirección personal, desde la aplicación de correo
        //Instalada en el dispositivo del usuario
        //Útil si se desea incluir información adicional/input del solicitante
//        Intent i = new Intent(Intent.ACTION_SEND);
//        i.setType("message/rfc822");
//        i.putExtra(Intent.EXTRA_EMAIL , new String[]{"admredmujeres@gmail.com"});
//        i.putExtra(Intent.EXTRA_SUBJECT, asunto);
//        i.putExtra(Intent.EXTRA_TEXT, cuerpo);
//        try {
//            startActivity(Intent.createChooser(i, "Enviando solicitud..."));
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MenuRedMujeres.this, "Error. Ningún cliente de correo electrónico ha sido instalado en el dispositivo.", Toast.LENGTH_SHORT).show();
//        }
    }

    private void popupRegistro(String nombre, String currentUserID) {
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
                        procesarValidacion(currentUserID);
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

    // Envía respuesta de solicitud a ingreso a la comunidad
    public void enviarConfirmacion(boolean aceptado, String correoUCR) {
        List<String> toEmailList = new ArrayList<>();
        toEmailList.add(correoUCR);

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

    // Metodos para sacar informacion de la bd de contexto y poder utilizarla
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

    // Recupera toda la informacion relacionada a los grupos



}


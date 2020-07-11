package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
/**Actividad para el despliegue de los detalles de una comunidad
 * */
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadDetalle extends AppCompatActivity {

    private Comunidad comunidad;    //Almacena los datos por ser desplegados en la vista
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad_detalle);

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        // Instancia base de datos
        mDatabase = FirebaseDatabase.getInstance();

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        comunidad = intent.getParcelableExtra("comunidad");
        int vis = intent.getIntExtra("vis", 1);
        String usuarioID = intent.getStringExtra("userID");
        String usuarioNombre = intent.getStringExtra("userName");

        //Instanciación de los elementos del Layout
        ImageView imageView = findViewById(R.id.image_Comunity_CD);
        TextView textViewCommunityName = findViewById(R.id.text_Community_Name_CD);
        TextView textViewCommunityDescription = findViewById(R.id.text_Description_Content_CD);
        TextView textViewNoMembers = findViewById(R.id.text_No_Members_CD);
        TextView textViewMemberList = findViewById(R.id.text_Members_List_CD);
        Button buttonJoinCommunity = findViewById(R.id.button_Join_Community_CD);

        //Se actualizan los elementos del layout con los detalles de una comunidad
        imageView.setImageResource(comunidad.getCommunityImgRes());
        textViewCommunityName.setText(comunidad.getCommunityName());
        textViewCommunityDescription.setText("Descripción:\n" + comunidad.getCommunityDescription());
        textViewNoMembers.setText("Miembros ("+comunidad.getCommunityNoMembers()+"):");

        //Se llena despliegan los miembros de la comunidad
        ArrayList<String> miembros = comunidad.getCommunityMembers();
        textViewMemberList.setText("");
        for(int i = 0; i<miembros.size(); ++i)
        {
//            textViewMemberList.append(" - " + miembros.get(i) + "\n"); //Llenar con los miembros de la BD
            obtenerNombreUsuario(miembros.get(i), textViewMemberList);
        }
        //Condición para definir qué se muestra en el botón y su acción
        if(vis == 0)
        {
            //Se cambia el texto al botón
            buttonJoinCommunity.setText("Ir al mapa de la Comunidad");

            //Se define la acción para redireccionar al mapa
            buttonJoinCommunity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Llamado a la actividad que despliega el mapa
                    System.out.println(usuarioID);
                    startActivity(new Intent(ComunidadDetalle.this, MainRedMujeres.class)
                            .putExtra("usuarioID", usuarioID)
                            .putExtra("usuarioName", usuarioNombre));
                }
            });
        }
        else
        {
            buttonJoinCommunity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //popUpUnirse(getApplicationContext());   //Despliegue del mensaje de confirmación para unirse al grupo seleccionado

                    //Llamado a la actividad que despliega el formulario
                    startActivity(new Intent(ComunidadDetalle.this, FormularioComunidad.class)
                            .putExtra("usuarioID", usuarioID)
                            .putExtra("usuarioName", usuarioNombre)
                            .putExtra("comunidad", comunidad));
                }
            });
        }
    }

    //Método que devuelve el nombre del usuario de la comunidad según el ID que recibo como parámetro
    private void obtenerNombreUsuario(String miembroID, TextView textViewMemberList)
    {
        DatabaseReference usuario = mDatabase.getReference("usuarios_red_mujeres").child(miembroID);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String,Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
//                nombreUsuarioTemp = map.get("Nombre").toString();
                textViewMemberList.append(" - " + map.get("Nombre").toString() + "\n");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usuario.addListenerForSingleValueEvent(valueEventListener);
    }
}

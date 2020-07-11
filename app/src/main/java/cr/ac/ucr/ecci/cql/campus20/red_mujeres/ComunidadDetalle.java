package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
/**Actividad para el despliegue de los detalles de una comunidad
 * */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadDetalle extends AppCompatActivity {

    private Comunidad comunidad;    //Almacena los datos por ser desplegados en la vista

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad_detalle);

        //Se oculta el ActionBar para "reemplezarlo" por el AppBar definido
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

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
        textViewCommunityDescription.setText(comunidad.getCommunityDescription());
        textViewNoMembers.setText(comunidad.getCommunityNoMembers());
        //textViewMemberList.setText(); //Llenar con los miembros de la BD

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
}

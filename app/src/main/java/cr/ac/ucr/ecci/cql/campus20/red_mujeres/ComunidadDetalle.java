package cr.ac.ucr.ecci.cql.campus20.red_mujeres;
/**Actividad para el despliegue de los detalles de una comunidad
 * */
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
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
                    popUpUnirse(getApplicationContext());   //Despliegue del mensaje de confirmación para unirse al grupo seleccionado
                }
            });
        }
    }

    //Método que despliega un popup para determinar si un usuario quiere unirse a una comunidad
    public void popUpUnirse(Context context)
    {
        // Creación del diálogo con AlertDialog builder. Primero se hace el constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(ComunidadDetalle.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Unirse");
        builder.setMessage("¿Desea unirse a la comunidad " + comunidad.getCommunityName() + "?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "Afirmativo" , Toast.LENGTH_SHORT).show();
                        comunidad.Unirse(context);  //Llamado al método que agrega un usuario a una comunidad
                    }
                });

        String negativeText = "No";
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       // Toast.makeText(context, "Negativo" , Toast.LENGTH_SHORT).show();
                    }
                });

        //Creación y despliegue del diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

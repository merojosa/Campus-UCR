package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

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

import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadDetalle extends AppCompatActivity {
    private Comunidad comunidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad_detalle);

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        comunidad = intent.getParcelableExtra("comunidad");

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

        buttonJoinCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpUnirse(getApplicationContext());
            }
        });
    }

    public void popUpUnirse(Context context)
    {
        // create a dialog with AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(ComunidadDetalle.this, R.style.AppTheme_RedMujeres);

        builder.setTitle("Unirse");
        builder.setMessage("¿Desea unirse a la comunidad " + comunidad.getCommunityName() + "?");

        String positiveText = "Sí";
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(context, "Afirmativo" , Toast.LENGTH_SHORT).show();
                        comunidad.Unirse(context);
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

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

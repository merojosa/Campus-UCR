package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.ecci.cql.campus20.R;

public class ComunidadDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad_detalle);

        //Se recibe objeto Comunidad
        Intent intent = getIntent();
        Comunidad comunidad = intent.getParcelableExtra("comunidad");

        //Instanciación de los elementos del Layout
        ImageView imageView = findViewById(R.id.image_Comunity_CD);
        TextView textViewCommunityName = findViewById(R.id.text_Community_Name_CD);
        TextView textViewCommunityDescription = findViewById(R.id.text_Description_Content_CD);
        TextView textViewNoMembers = findViewById(R.id.text_No_Members_CD);
        TextView textViewMemberList = findViewById(R.id.text_Members_List_CD);

        //Se actualizan los elementos del layout con los detalles de una comunidad
        imageView.setImageResource(comunidad.getCommunityImgRes());
        textViewCommunityName.setText(comunidad.getCommunityName());
        textViewCommunityDescription.setText(comunidad.getCommunityDescription());
        textViewNoMembers.setText(comunidad.getCommunityNoMemebers());
        //textViewMemberList.setText(); //Llenar con los miembros de la BD
        //Toast.makeText(getApplicationContext(), comunidad.getCommunityName(), Toast.LENGTH_SHORT).show();

    }
}

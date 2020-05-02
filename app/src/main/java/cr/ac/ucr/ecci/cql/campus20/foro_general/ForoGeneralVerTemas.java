package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import cr.ac.ucr.ecci.cql.campus20.MainActivity;
import cr.ac.ucr.ecci.cql.campus20.R;

public class ForoGeneralVerTemas extends AppCompatActivity {
    // definimos la lista de datos
    private ListView lvItems;
    private AdaptadorTemas adaptadorTemas;
    //private List<Temas> mTemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_temas);

        //se establece la vista de la lista de temas
        lvItems = findViewById(R.id.lvItems);
        adaptadorTemas = new AdaptadorTemas(this, GetArrayItems());
        lvItems.setAdapter(adaptadorTemas);

        /*
        // ListView Item Click Listener para las llamadas a las opciones de los items
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                Temas item = (Temas)lvItems.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(), "Position: " + itemPosition +
                        "  ListItem: " + item.toString(), Toast.LENGTH_LONG).show();
            }
        });
        */



        // Inicialización de la barra de navegación general
        BottomNavigationView generalNavigationView = findViewById(R.id.bottom_navigation);

        // Dejar por defecto el ícono de foro seleccionado
        generalNavigationView.setSelectedItemId(R.id.foro);

        // Ejecución del listener para cambiar de actividad
        generalNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // Dependiendo del item seleccionado, ejecutará el Intent del módulo específico
                switch(menuItem.getItemId()){
                    case R.id.ucreats:
                        break;
                    case R.id.mujeres:
                        break;
                    case R.id.foro:
                        break;
                    case R.id.lugares:
                        break;
                }
                return false;
            }
        });



    }

    //metodo para obtener la lista de temas
    private ArrayList<Temas> GetArrayItems(){
        ArrayList<Temas> mTemas = new ArrayList<>();

        mTemas.add(new Temas("General/Noticias", R.drawable.foro1, "Lo mas nuevo"));
        mTemas.add(new Temas("Escuelas", R.drawable.foro_escuelas, "."));
        mTemas.add(new Temas("Profesores", R.drawable.foro_profesores, "."));
        mTemas.add(new Temas("Becas", R.drawable.foro_becas, "Desde como aplicar hasta como gastar"));
        mTemas.add(new Temas("Residencias", R.drawable.foro_residencias, "Mas barato que alquilar ..."));
        mTemas.add(new Temas("Buses", R.drawable.foro_buses, "Todo sobre el interno, algo sobre los externos"));
        mTemas.add(new Temas("Mejores calificadas", R.drawable.foro_calificado, "Las preguntas mas valoradas por la comunidad"));

        return mTemas;
    }
}


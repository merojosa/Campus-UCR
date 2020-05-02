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

        mTemas.add(new Temas("Agua", R.drawable.i01, "Al menos 8 vasos al día"));
        mTemas.add(new Temas("Vino", R.drawable.i02, "No exceda una copa al día"));
        mTemas.add(new Temas("Café", R.drawable.i03, "Evite tomarlo"));
        mTemas.add(new Temas("Carnes", R.drawable.i04, "Al menos tres veces a la semana"));
        mTemas.add(new Temas("Hamburguesa", R.drawable.i05, "Solo caseras y bajas en grasa"));

        return mTemas;
    }
}


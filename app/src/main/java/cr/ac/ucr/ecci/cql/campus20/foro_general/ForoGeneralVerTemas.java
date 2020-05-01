package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

public class ForoGeneralVerTemas extends AppCompatActivity {
    // definimos la lista de datos
    private ListView listView;
    private List<Temas> mTemas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_temas);

        // Obtenemos la lista
        listView = (ListView) findViewById(R.id.listTemas);

        mTemas = new ArrayList<Temas>();

        mTemas.add(new Temas("Agua", "i01", "Al menos 8 vasos al día"));
        mTemas.add(new Temas("Vino", "i02", "No exceda una copa al día"));
        mTemas.add(new Temas("Café", "i03", "Evite tomarlo"));
        mTemas.add(new Temas("Carnes", "i04", "Al menos tres veces a la semana"));
        mTemas.add(new Temas("Hamburguesa", "i05", "Solo caseras y bajas en grasa"));

        // definimos el adaptador para la lista
        ArrayAdapter<Temas> adapter = new ArrayAdapter<Temas>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, mTemas);

        // asignamos el adaptador al ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener para las llamadas a las opciones de los items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                Temas item = (Temas)listView.getItemAtPosition(position);
                // Show Alert
                Toast.makeText(getApplicationContext(), "Position: " + itemPosition +
                        "  ListItem: " + item.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

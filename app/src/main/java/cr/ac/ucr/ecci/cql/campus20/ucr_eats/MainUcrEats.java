package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

// Referencias para crear lista de cards:
// https://github.com/tutsplus/Android-CardViewRecyclerView
public class MainUcrEats extends AppCompatActivity
{
    private List<SodaCard> sodaCards = null;
    private RecyclerView recyclerViewSodas;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucr_eats);

        recyclerViewSodas = (RecyclerView)findViewById(R.id.ucr_eats_rv);

        recyclerViewSodas.setLayoutManager(new LinearLayoutManager(this));

        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewSodas.setHasFixedSize(true);

        obtenerSodas();
        inicializarAdapter();
    }

    /*
     * Esto creo que es lo de Diego, la cosa es usar la consulta de la base de datos y ojo, muy importante,
     * la unica informacion que se ocupa es el nombre de la soda y el nombre de la imagen (que tiene que estar en la carpeta
     * drawable). Por lo tanto, para encapsularlo y evitarnos conflictos, hice una clase SodaCard (que tiene nombre e imagen nada
     * mas). Entonces, Diego, use esa clase para inicializar la lista sodaCards. El brete suyo me parece que es filtrar esa
     * info para solamente tener dicho nombre e imagen.
     */
    private void obtenerSodas()
    {
        sodaCards = new ArrayList<>();
        // Importante tener el nombre de las imagenes con caracteres alfanumericos y '_' unicamente.
        sodaCards.add(new SodaCard("Soda La U", R.drawable.la_u));
        sodaCards.add(new SodaCard("Plaza Chou", R.drawable.plaza_chou));
    }

    private void inicializarAdapter(){
        RVAdapter adapter = new RVAdapter(sodaCards);
        recyclerViewSodas.setAdapter(adapter);
    }
}

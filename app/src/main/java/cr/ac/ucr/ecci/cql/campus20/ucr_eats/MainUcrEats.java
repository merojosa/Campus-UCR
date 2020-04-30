package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;

// Referencias para crear lista de cards:
// https://github.com/tutsplus/Android-CardViewRecyclerView
public class MainUcrEats extends AppCompatActivity
{
    private EditText inputSearch;
    private RVAdapter sodasAdapter;

    private List<SodaCard> sodaCards = null;
    private RecyclerView recyclerViewSodas;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucr_eats);
        getSupportActionBar().hide();

        setupInputSearch();
        setupRecyclerView();

        obtenerSodas();
        inicializarAdapter();
    }

    private void setupInputSearch() {
        this.inputSearch = findViewById(R.id.search_filter);
        this.inputSearch.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
        this.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                filtrar(s.toString());
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewSodas = (RecyclerView)findViewById(R.id.ucr_eats_rv);
        recyclerViewSodas.setLayoutManager(new LinearLayoutManager(this));

        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewSodas.setHasFixedSize(true);
    }

    private void filtrar(String texto) {

        ArrayList<SodaCard> filtrarLista = new ArrayList<>();

        for(SodaCard usuario : sodaCards) {
            if(usuario.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
        }

        sodasAdapter.filter(filtrarLista);
    }

    private void obtenerSodas()
    {
        // initialize the soda list
        sodaCards = new ArrayList<>();
        int index = 0;
        // get soda names
        List<String> sodaName = obtenerNombreSodas();

        // fill soda list
        sodaCards.add(new SodaCard(sodaName.get(index++), R.drawable.la_u));
        sodaCards.add(new SodaCard(sodaName.get(index++), R.drawable.plaza_chou));
        sodaCards.add(new SodaCard(sodaName.get(index++), R.drawable.la_u));
    }

    /**
     * metodo que se espera tenga el retorno de una lista de string con los nombres de las sodas
     */
    private List<String> obtenerNombreSodas() {
        List<String> names = new ArrayList<String>();
        names.add("Soda La U");
        names.add("Plaza Chou");
        names.add("Soda Facultad Derecho");

        return names;
    }

    private void inicializarAdapter(){
        this.sodasAdapter = new RVAdapter(sodaCards);
        recyclerViewSodas.setAdapter(sodasAdapter);
    }
}

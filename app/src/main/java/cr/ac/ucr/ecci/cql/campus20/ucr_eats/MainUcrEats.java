package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.RVAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.RestaurantRepository;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.viewmodels.RestaurantViewModel;

// Referencias para crear lista de cards:
// https://github.com/tutsplus/Android-CardViewRecyclerView
public class MainUcrEats extends AppCompatActivity
{
    private RestaurantViewModel restaurantViewModel;

    private EditText inputSearch;
    private RVAdapter sodasAdapter;
    private RecyclerView recyclerViewSodas;

    private RestaurantRepository repository;
    private List<Restaurant> restaurantsList = null;
    private List<SodaCard> sodaCards = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucr_eats);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();


        this.repository = new RestaurantRepository(getApplication());

        fillRestaurants();
        setupInputSearch();
        setupRecyclerView();
        inicializarAdapter();


        this.restaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        this.restaurantViewModel.getAllRestaurants().observe(this, restaurants -> {
            sodaCards = sodasAdapter.convertToSodaCards(restaurants);
            sodasAdapter.setSodaCards(restaurants);
        });
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
                filter(s.toString());
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewSodas = (RecyclerView)findViewById(R.id.ucr_eats_rv);
        recyclerViewSodas.setLayoutManager(new LinearLayoutManager(this));

        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewSodas.setHasFixedSize(true);
    }

    private void filter(String texto) {

        ArrayList<SodaCard> filtrarLista = new ArrayList<>();

        for(SodaCard usuario : sodaCards) {
            if(usuario.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                filtrarLista.add(usuario);
            }
        }

        sodasAdapter.filter(filtrarLista);
    }

    private void inicializarAdapter(){
        this.sodasAdapter = new RVAdapter(this, sodaCards);
        recyclerViewSodas.setAdapter(sodasAdapter);
    }

    /**
     * método con fines sólo de prueba de concepto.
     */
    private void fillRestaurants() {
        repository.deleteAll();
        Restaurant restaurant1 = new Restaurant(R.drawable.la_u, "Soda La U", "la_u", 9.934497, -84.051063,
                "Mo", (short)0, (short)1000);
        repository.insert(restaurant1);

        Restaurant restaurant2 = new Restaurant(R.drawable.plaza_chou, "Plaza Chou", "plaza_chou", 9.934748, -84.051578,
                "Mo", (short)0, (short)1000);
        repository.insert(restaurant2);
    }

}

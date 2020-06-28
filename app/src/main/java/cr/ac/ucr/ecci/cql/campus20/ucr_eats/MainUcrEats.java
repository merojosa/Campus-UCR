package cr.ac.ucr.ecci.cql.campus20.ucr_eats;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.adapters.RVAdapter;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.models.Restaurant;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.RatingRepository;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.repositories.RestaurantRepository;


// Referencias para crear lista de cards:
// https://github.com/tutsplus/Android-CardViewRecyclerView
public class MainUcrEats extends AppCompatActivity
{
    private EditText inputSearch;
    private RVAdapter sodasAdapter;
    private RecyclerView recyclerViewSodas;
    private TextView noResults;

    private RestaurantRepository repository;
    private RatingRepository repo;
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
        repo = new RatingRepository(getApplication());
        //fillRestaurants();

        setupInputSearch();
        setupRecyclerView();
        inicializarAdapter();

        this.noResults = this.findViewById(R.id.noResultsText);

        this.getFirebaseRestaurant();

    }


    private void getFirebaseRestaurant()
    {
        UcrEatsFirebaseDatabase db = new UcrEatsFirebaseDatabase();

        DatabaseReference ref = db.getRestaurantsRef();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // Get all meals/children data from snapshot
                Iterable<DataSnapshot> sodaData = dataSnapshot.getChildren();
                ArrayList<Restaurant> sodas = new ArrayList<>();
                // Iterate array
                int id = 1;
                for(final DataSnapshot soda : sodaData)
                {
                    Log.e("Nombre:", ""+soda.getValue());
                    Restaurant so = soda.getValue(Restaurant.class);
                    so.setId(id++);
                    if (so != null)
                    {
                        so.setFirebaseId(soda.getKey());
                        so.setServings(soda);
                    }

                    if(soda.exists()) {
                        Log.e("datos", "" + soda.getValue());
                        sodas.add(so);
                    }
                }

                if (sodas.size() > 0)
                    sodasAdapter.setSodaCard(sodas);
                    sodaCards = sodasAdapter.getSodaCards();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("FIREBASE", "Failed to read value.", error.toException());
            }
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

        if(sodaCards != null)
        {
            for(SodaCard usuario : sodaCards) {
                if(usuario.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                    filtrarLista.add(usuario);
                }
            }
        }

        if(filtrarLista.isEmpty()) {
            this.noResults.setVisibility(View.VISIBLE);
        }
        else
            this.noResults.setVisibility(View.GONE);


        sodasAdapter.filter(filtrarLista);
    }

    private void inicializarAdapter(){
        this.sodasAdapter = new RVAdapter(this, sodaCards);
        recyclerViewSodas.setAdapter(sodasAdapter);
    }


}

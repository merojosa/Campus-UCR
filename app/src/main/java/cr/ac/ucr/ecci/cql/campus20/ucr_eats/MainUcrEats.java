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
    private List<Soda> sodas;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ucr_eats);


        rv = (RecyclerView)findViewById(R.id.ucr_eats_rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true); // Si no se cambia el tamanno, hacer esto mejorar el performance

        initializeData();
        initializeAdapter();
    }

    private void initializeData()
    {
        sodas = new ArrayList<>();
        // Importante tener el nombre de las imagenes con caracteres alfanumericos y '_' unicamente.
        sodas.add(new Soda("Soda La U", R.drawable.la_u));
        sodas.add(new Soda("Plaza Chou", R.drawable.plaza_chou));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(sodas);
        rv.setAdapter(adapter);
    }
}

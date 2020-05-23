package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterPregunta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class ForoGeneralVerPreguntas extends AppCompatActivity {
    private LiveData<List<Pregunta>> preguntas;
    private PreguntaViewModel mPreguntaViewModel;
    private TextView tituloTema;
    private RecyclerView recyclerViewPreguntas;
    private RVAdapterPregunta preguntasAdapter;
    private List<PreguntaCard> preguntaCards = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_preguntas);

        Intent mIntent = getIntent();
        int idTemaSeleccionado = mIntent.getIntExtra("idTemaSeleccionado", 0);
        String temaSeleccionado = mIntent.getStringExtra("temaSeleccionado");

        recyclerViewPreguntas = (RecyclerView)findViewById(R.id.verPreguntasRV);
        recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(this));
        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewPreguntas.setHasFixedSize(true);

        this.preguntasAdapter = new RVAdapterPregunta(this, preguntaCards);
        recyclerViewPreguntas.setAdapter(preguntasAdapter);

        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        preguntas = mPreguntaViewModel.getPreguntasTema(idTemaSeleccionado);
        tituloTema = (TextView) findViewById(R.id.temaSeleccionado);
        tituloTema.setText(temaSeleccionado);
        preguntas.observe(this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                if(preguntas.size() > 0){
//                    tituloTema.setText(preguntas.get(0).texto);
////                    tituloTema.setTextSize(10);
                    preguntaCards = preguntasAdapter.convertToPreguntaCards(preguntas);
                    preguntasAdapter.setPreguntaCards(preguntas);
                }else{
                    tituloTema.setText(temaSeleccionado + ": No tiene preguntas!");
                }
            }
        });

    }
}

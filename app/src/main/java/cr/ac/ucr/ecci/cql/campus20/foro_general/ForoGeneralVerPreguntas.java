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

public class ForoGeneralVerPreguntas extends AppCompatActivity implements RVAdapterPregunta.OnPreguntaListener {
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

        // Intent para obtener el id del tema seleccionado en la pantalla anterior
        // Ademas, el nombre del tema seleccionado en la pantalla anterior
        Intent mIntent = getIntent();
        int idTemaSeleccionado = mIntent.getIntExtra("idTemaSeleccionado", 0);
        String temaSeleccionado = mIntent.getStringExtra("temaSeleccionado");

        // Setear el view para las preguntas
        recyclerViewPreguntas = (RecyclerView)findViewById(R.id.verPreguntasRV);
        recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(this));
        // Si no se cambia el tamanno, hacer esto mejora el performance
        recyclerViewPreguntas.setHasFixedSize(true);

        // Setear el adaptador para las preguntas
        this.preguntasAdapter = new RVAdapterPregunta(this, preguntaCards, this);
        recyclerViewPreguntas.setAdapter(preguntasAdapter);

        // Instancia el viewModel para recuperar la lista asincrónicamente
        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        preguntas = mPreguntaViewModel.getPreguntasTema(idTemaSeleccionado);
        // Setea el titulo del tema seleccionado en la pantalla de ver preguntas
        tituloTema = (TextView) findViewById(R.id.temaSeleccionado);
        tituloTema.setText(temaSeleccionado);
        preguntas.observe(this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                // Si la lista tiene preguntas agrega las preguntas
                if(preguntas.size() > 0){
                    preguntaCards = preguntasAdapter.convertToPreguntaCards(preguntas);
                    preguntasAdapter.setPreguntaCards(preguntas);
                }else{
                    tituloTema.setText(temaSeleccionado + ": No tiene preguntas!");
                }
            }
        });

    }

    /**
     * Metodo que agrega el listener de click en las preguntas para enviar a la actividad de ver respuestas
     * @param position
     */
    @Override
    public void onPreguntaClick(int position) {
        PreguntaCard preguntaSeleccionada = preguntaCards.get(position);

        Intent intent = new Intent(getApplicationContext(), ForoGeneralVerRespuestas.class);
        intent.putExtra("preguntaSeleccionada", preguntaSeleccionada);

        startActivity(intent);
    }
}

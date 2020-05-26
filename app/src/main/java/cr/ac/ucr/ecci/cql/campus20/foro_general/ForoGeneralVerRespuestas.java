package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.Adapters.RVAdapterRespuesta;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

public class ForoGeneralVerRespuestas extends AppCompatActivity {

    LiveData<List<Respuesta>> respuestas;
    RespuestaViewModel mRespuestaViewModel;
    private TextView tituloPregunta;
    private RecyclerView recyclerViewRespuestas;
    private RVAdapterRespuesta adapterRespuesta;
    private List<Respuesta> listaRespuestas;
    FloatingActionButton buttonAgregarRespuestas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_respuestas);
        Intent mIntent = getIntent();

        // Boton flotante de Agregar Respuestas
        buttonAgregarRespuestas = findViewById(R.id.buttonAgregarRespuestas);

        PreguntaCard preguntaSeleccionada = mIntent.getParcelableExtra("preguntaSeleccionada");

        int idPreguntaSeleccionada = preguntaSeleccionada.getId();

        recyclerViewRespuestas = (RecyclerView) findViewById(R.id.verRespuestasRV);

        recyclerViewRespuestas.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewRespuestas.setHasFixedSize(true);

        adapterRespuesta = new RVAdapterRespuesta(this, listaRespuestas);
        recyclerViewRespuestas.setAdapter(adapterRespuesta);

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);

        respuestas = mRespuestaViewModel.getRespuestasDePregunta(idPreguntaSeleccionada);

        tituloPregunta = (TextView) findViewById(R.id.preguntaSeleccionada);
        tituloPregunta.setText(preguntaSeleccionada.getTexto());

        respuestas.observe(this, new Observer<List<Respuesta>>() {
            @Override
            public void onChanged(List<Respuesta> respuestas) {
                if (respuestas.size() > 0) {
                    adapterRespuesta.setRespuestas(respuestas);
                } else {
                    tituloPregunta.setText(preguntaSeleccionada.getTexto() + ": " + "No hay respuestas aun.");
                }
            }
        });

        // Asocia evento clic al boton
        buttonAgregarRespuestas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crearRespuesta(preguntaSeleccionada);
            }
        });
    }

    /**
     * Este metodo dirige a la actividad de crear respuesta
     *
     * @param pregunta indica la pregunta que será enviada mediante el intent para poder usarla
     * para crear la respuesta asociada a esta pregunta
     */
    private void crearRespuesta(PreguntaCard pregunta) {
        Intent intent = new Intent(this, CrearRespuestaForoGeneral.class);
        intent.putExtra("preguntaSeleccionada", pregunta);

        startActivity(intent);
    }


}

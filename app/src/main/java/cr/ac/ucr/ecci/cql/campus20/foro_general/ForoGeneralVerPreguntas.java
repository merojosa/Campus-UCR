package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.PreguntaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Pregunta;

public class ForoGeneralVerPreguntas extends AppCompatActivity {
    private LiveData<List<Pregunta>> preguntas;
    private PreguntaViewModel mPreguntaViewModel;
    private TextView tema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foro_general_ver_preguntas);
        Intent mIntent = getIntent();
        int idTemaSeleccionado = mIntent.getIntExtra("idTemaSeleccionado", 0);
        mPreguntaViewModel = new ViewModelProvider(this).get(PreguntaViewModel.class);
        preguntas = mPreguntaViewModel.getPreguntasTema(idTemaSeleccionado);
        tema = (TextView) findViewById(R.id.pruebaPreguntas);
        preguntas.observe(this, new Observer<List<Pregunta>>() {
            @Override
            public void onChanged(List<Pregunta> preguntas) {
                if(preguntas.size() > 0){
                    tema.setText(preguntas.get(0).texto);
                }else{
                    tema.setText("Este Tema NO tiene preguntas");
                }
            }
        });

    }
}

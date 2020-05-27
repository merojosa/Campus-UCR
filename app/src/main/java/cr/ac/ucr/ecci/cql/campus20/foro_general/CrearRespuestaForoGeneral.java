package cr.ac.ucr.ecci.cql.campus20.foro_general;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.ucr.ecci.cql.campus20.R;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ViewModels.RespuestaViewModel;
import cr.ac.ucr.ecci.cql.campus20.foro_general.models.Respuesta;

//Crea respuesta a partir de la pregunta que se seleccionó del lista de preguntas.
public class CrearRespuestaForoGeneral extends AppCompatActivity {

    private EditText mEditText;
    private TextView titulo;
    private Button btnCrearRespuesta;
    private PreguntaCard pregunta;
    private RespuestaViewModel mRespuestaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_respuesta_foro_general);

        Intent mIntent = getIntent();
        pregunta = mIntent.getParcelableExtra("preguntaSeleccionada");

        mRespuestaViewModel = new ViewModelProvider(this).get(RespuestaViewModel.class);

        // Codigo para agregar borde al espacio para rellenar la pregunta
        mEditText = (EditText) findViewById(R.id.textoCrearRespuesta);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor("#00ffffff"));
        gd.setStroke(2, Color.parseColor("#00C0F3"));
        mEditText.setBackground(gd);

        // Codigo para manejar color del boton y evento de click
        btnCrearRespuesta = (Button) findViewById(R.id.btnCrearRespuesta);
        btnCrearRespuesta.setBackgroundColor(Color.parseColor("#00C0F3"));
        btnCrearRespuesta.setTextColor(Color.BLACK);
        btnCrearRespuesta.setText("Crear Respuesta");
        btnCrearRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verificarPregunta()) {
                    agregarPregunta();
                }
            }
        });

    }

    /**
     * Este método inserta una nueva pregunta partir de lo que se digite, luego de insertar en
     * la base de datos se devuelve a la vista de respuesta para dicha pregunta
     */
    private void agregarPregunta() {
        String textoRespuesta = mEditText.getText().toString();
        int idPregunta = pregunta.getId();

        Respuesta respuesta = new Respuesta(0, textoRespuesta, idPregunta, 0, 0);
        mRespuestaViewModel.insert(respuesta);
        //Luego de insertar volver a vista previa
        Intent intent = new Intent(this, ForoGeneralVerRespuestas.class);
        intent.putExtra("preguntaSeleccionada", pregunta);
        startActivity(intent);
    }

    private boolean verificarPregunta() {
        if (mEditText.getText().toString().equals("")) {
            Toast.makeText(CrearRespuestaForoGeneral.this, "Por favor digite su respuesta", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}

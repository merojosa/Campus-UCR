package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import cr.ac.ucr.ecci.cql.campus20.R;

public class CompartirRutaFragment extends DialogFragment {

    //Contructor vacio para dialogo de alerta
    public CompartirRutaFragment(){

    }

    public static CompartirRutaFragment newInstance(String title) {
        CompartirRutaFragment fragment = new CompartirRutaFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");
        //MainRedMujeres mujeres = new MainRedMujeres();
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setMessage(R.string.pregunta_compartir_ruta)
                .setPositiveButton(R.string.afirmativo_compartir_ruta, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Correr la magia
                    }
                })
                .setNegativeButton(R.string.negativo_compartir_ruta, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //TODO Averiguar bien por qué no funciona el llamado a la navegación
                        //mujeres.iniciarRuta();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}

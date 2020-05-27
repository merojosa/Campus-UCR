package cr.ac.ucr.ecci.cql.campus20;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

// Se importan las actividades principales que estarán disponibles desde el fragmento
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolViewActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.InterestPointsActivity;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearPreguntaForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.CrearRespuestaForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerPreguntas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerRespuestas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.red_mujeres.MainRedMujeres;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationBarFragment extends android.app.Fragment
{
    /**
     * Construtor del fragmento
     */
    public NavigationBarFragment() {
        // Required empty public constructor
    }

    /**
     * Método que se encarga de crear una nueva instancia del fragmento
     * @return fragment, que es una instancia del fragmento
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationBarFragment newInstance() {
        NavigationBarFragment fragment = new NavigationBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Método que se encarga de hacer inicializar el fragmento
     * @param savedInstanceState se usa para almacenar información del estado de la actividad
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Función que se encarga de crear la vista del fragmento y devolverla
     * @param inflater es un componente para crear instancias de View basadas en XML
     * @param container es el ViewGroup donde la vista del fragmento será insertado
     * @param savedInstanceState es donde se guarda datos y estados del fragmento
     * @return v, que es un View que contiene la vista del fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_navigation_bar, container, false);

        // Se crea la barra de navegación dentro del fragmento
        BottomNavigationView navegacion = (BottomNavigationView) v.findViewById(R.id.bottom_navigation);

        // Se declara el método para obtener la selección de los íconos de la barra
        navegacion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Se deja el ícono inicial de la barra sin seleccionar por defecto
        navegacion.getMenu().getItem(0).setCheckable(false);

        // Chequea cuál de las actividades principales es la que está llamando al módulo para
        // marcar ese ícono como seleccionado
        if (getActivity() instanceof LoginActivity)
            navegacion.getMenu().getItem(0).setChecked(false);

        if (getActivity() instanceof MainUcrEats)
            // Ícono del módulo de ucr eats
            navegacion.getMenu().getItem(0).setChecked(true);
        else
            if (getActivity() instanceof MainRedMujeres)
                // Ícono del módulo de mujeres ucr
                navegacion.getMenu().getItem(1).setChecked(true);
            else
                if (getActivity() instanceof MainForoGeneral || getActivity() instanceof CrearPreguntaForoGeneral
                        || getActivity() instanceof ForoGeneralVerTemas || getActivity() instanceof ForoGeneralVerPreguntas
                        || getActivity() instanceof CrearRespuestaForoGeneral || getActivity() instanceof ForoGeneralVerRespuestas)
                    // Ícono del módulo de foro
                    navegacion.getMenu().getItem(2).setChecked(true);
                else
                    if ( getActivity() instanceof InterestPointsActivity ||  getActivity() instanceof FacultiesActivity ||
                            getActivity() instanceof SchoolsActivity || getActivity() instanceof SchoolViewActivity)
                        // Ícono del módulo de localización
                        navegacion.getMenu().getItem(3).setChecked(true);

        // Se devuelve la vista con la barra de navegación creada
        return v;
    }

    // Se lanza cada actividad, dependiendo de la selección del usuario
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            if(getActivity() instanceof ConfiguracionActivity)
            {
                irActividadElegidaConfirmacion(item.getItemId());
                return true;
            }

            return irActividadElegida(item.getItemId());
        }
    };

    private void irActividadElegidaConfirmacion(int actividadId)
    {
        new AlertDialog.Builder(getActivity())
                .setTitle("Title")
                .setMessage("¿Quiere guardar esta app como la predeterminada? " +
                        "La siguiente vez que vuelva iniciará con esta app.")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Guardar App", (dialog, whichButton) ->
                {
                    LoginBD loginBD = new FirebaseBD();
                    loginBD.escribirDatos("algo", actividadId);
                    irActividadElegida(actividadId);
                })
                .setNegativeButton("Cancelar", (dialog, which) ->
                {
                    irActividadElegida(actividadId);
                }).show();
    }

    private boolean  irActividadElegida(int actividadId)
    {
        switch (actividadId)
        {

            case R.id.ucreats:  // En caso de que se haya seleccionado el ícono de UcrEats
                Intent intentUCREats = new Intent(getActivity(), MainUcrEats.class);
                startActivity(intentUCREats);
                return true;

            case R.id.foro:     // En caso de que se haya seleccionado el ícono de Foro
                Intent intentForo = new Intent(getActivity(), MainForoGeneral.class);
                startActivity(intentForo);
                return true;

            case R.id.mujeres:  // En caso de que se haya seleccionado el ícono de Mujeres
                Intent intentMujeres = new Intent(getActivity(), MainRedMujeres.class);
                startActivity(intentMujeres);
                return true;

            case R.id.lugares:  // En caso de que se haya seleccionado el ícono de localización
                Intent intentLocalizacion = new Intent(getActivity(), InterestPointsActivity.class);
                startActivity(intentLocalizacion);
                return true;
        }
        return false;
    }
}

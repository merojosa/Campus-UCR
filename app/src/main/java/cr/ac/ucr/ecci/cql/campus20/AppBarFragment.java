package cr.ac.ucr.ecci.cql.campus20;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.activites.OrdenesPendientesActivity;


public class AppBarFragment extends Fragment
{


    public AppBarFragment() {
        // Required empty public constructor
    }

    public static AppBarFragment newInstance()
    {
        AppBarFragment fragment = new AppBarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_app_bar, container, false);

        // Settear el toolbar
        Toolbar toolbar = view.findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        // Cambiar el color de los 3 puntos.
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.blanco_UCR), PorterDuff.Mode.SRC_ATOP);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Si se hizo click a preferencias, se va a la actividad de Configuracion
                if (item.getItemId() == R.id.itemPreferencias) {
                    startActivity(new Intent(getActivity(), ConfiguracionActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.itemCerrarSesion) {
                    // Cerrar sesion
                    CampusBD login = new FirebaseBD();
                    login.cerrarSesion();

                    // Matar todas las actividades anteriores y volver al login
                    ActivityCompat.finishAffinity(getActivity());
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return true;
                } else if (item.getItemId() == R.id.ordenesPendientes) {
                    startActivity(new Intent(getActivity(), OrdenesPendientesActivity.class));
                    return true;
                }
                return false;
            }
        });

        return view;
    }
}

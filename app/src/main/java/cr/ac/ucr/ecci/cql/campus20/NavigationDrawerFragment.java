package cr.ac.ucr.ecci.cql.campus20;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.foro_general.ForoGeneralVerTemas;
import cr.ac.ucr.ecci.cql.campus20.foro_general.MainForoGeneral;
import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends android.app.Fragment {
    // Variables que se necesitan para el Drawer
    DrawerLayout dl;
    ActionBarDrawerToggle t;
    NavigationView nv;

    int mMenuId;

    /**
     * Construtor del fragmento
     */
    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Método que se encarga de crear una nueva instancia del fragmento
     * @return fragment, que es una instancia del fragmento
     */
    public static NavigationDrawerFragment newInstance() {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
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
        setHasOptionsMenu(true);
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
        final View v = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        dl = (DrawerLayout)v;
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        t = new ActionBarDrawerToggle(getActivity(), dl,R.string.Open, R.string.Close);
        dl.addDrawerListener(t);
        t.syncState();


        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)v.findViewById(R.id.nv_foro);

        // Se declara el método para obtener la selección de los íconos de la barra
        nv.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return v;
    }

    // Se lanza cada actividad, dependiendo de la selección del usuario
    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.home_foro:
                    Intent intentForo = new Intent(getActivity(), MainForoGeneral.class);
                    startActivity(intentForo);
                    return true;
                case R.id.temas_foro:
                    Intent intent2Foro = new Intent(getActivity(), ForoGeneralVerTemas.class);
                    startActivity(intent2Foro);
                    return true;
            }
            return false;
        }
    };

    /**
     * Función que se llamo cada vez que se selecciona un item
     * @param item es el item del menu que se selecciono
     * @return booleano para ver si funciono el llamado
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
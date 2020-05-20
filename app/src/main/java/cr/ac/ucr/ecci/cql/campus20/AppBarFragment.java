package cr.ac.ucr.ecci.cql.campus20;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import cr.ac.ucr.ecci.cql.campus20.ucr_eats.MainUcrEats;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppBarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppBarFragment extends Fragment
{


    public AppBarFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AppBarFragment newInstance() {
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
        toolbar.getOverflowIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.blanco_UCR), PorterDuff.Mode.SRC_ATOP);


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                // Si se hizo click a preferencias, se va a la actividad de Configuracion
                if (item.getItemId() == R.id.itemPreferencias)
                {
                    Intent intent = new Intent(getActivity(), ConfiguracionActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        return view;
    }
}

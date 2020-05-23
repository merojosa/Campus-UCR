package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cr.ac.ucr.ecci.cql.campus20.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapPlaceDetail extends Fragment {

    public MapPlaceDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_place_detail, container, false);
    }
}

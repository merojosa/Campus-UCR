package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolPlacesPopUp extends AppCompatActivity {

    private Place place;
    private View view;

    public SchoolPlacesPopUp(){}

    public SchoolPlacesPopUp(View view, Place place){

        this.place = place;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.school_place_item, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        this.view = popupView;

        //Se configura  lo necesario para el popup de un place de la escueal
        TextView title = popupView.findViewById(R.id.titleSchoolItem);
        TextView floor = popupView.findViewById(R.id.floor);
        ImageView wifi = popupView.findViewById(R.id.imageWiFi);
        AppCompatCheckBox wifiCheck = popupView.findViewById(R.id.checkboxWiFi);
        TextView capacity = popupView.findViewById(R.id.capacity);
        ImageView computers = popupView.findViewById(R.id.imagePc);
        AppCompatCheckBox computersCheck = popupView.findViewById(R.id.checkboxPC);
        ImageView projector = popupView.findViewById(R.id.imageProjector);
        AppCompatCheckBox projectorCheck = popupView.findViewById(R.id.checkboxProjector);
        ImageView extintor = popupView.findViewById(R.id.imageExtintor);
        AppCompatCheckBox extintorCheck = popupView.findViewById(R.id.checkboxExtintor);

        title.setText(place.getName());
        floor.setText(Integer.toString(place.getFloor()));
        capacity.setText(Integer.toString(place.getCapacity()));

        wifi.setImageResource(R.drawable.icon_wifi);
        wifiCheck.setChecked(place.getWifi());
        wifiCheck.setEnabled(false);

        if(place.getType().equals(Place.TYPE_LABORATORY)) {
            computers.setImageResource(R.drawable.icon_pc);
            computersCheck.setChecked(place.isComputers());
            computersCheck.setEnabled(false);
            projector.setImageResource(R.drawable.icon_projector);
            projectorCheck.setChecked(place.isComputers());
            projectorCheck.setEnabled(false);
            extintor.setImageResource(R.drawable.icon_ext);
            extintorCheck.setChecked(place.isComputers());
            extintorCheck.setEnabled(false);
        }else {
            computers.setVisibility(View.GONE);
            computersCheck.setVisibility(View.GONE);
            projector.setVisibility(View.GONE);
            projectorCheck.setVisibility(View.GONE);
            extintor.setVisibility(View.GONE);
            extintorCheck.setVisibility(View.GONE);
        }
        final PopupWindow popPlaces = new PopupWindow(popupView, width, height, focusable);
        popPlaces.showAtLocation(popupView, Gravity.CENTER, 0, 0);

    }

}

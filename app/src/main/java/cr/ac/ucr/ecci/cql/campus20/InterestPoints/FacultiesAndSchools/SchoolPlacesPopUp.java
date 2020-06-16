package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import android.content.Context;
import android.os.Bundle;
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


        show(view, place);



    }

    public void show(View view, Place place){

        this.place = place;
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.activity_school_place_item, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true;
        this.view = popupView;


        TextView floor = popupView.findViewById(R.id.floor);
        floor.setText(Integer.toString(place.getFloor()));

        ImageView wifi = popupView.findViewById(R.id.imageWiFi);
        wifi.setImageResource(R.drawable.icon_wifi);
        AppCompatCheckBox wifiCheck = popupView.findViewById(R.id.checkboxWiFi);
        wifiCheck.setChecked(place.isWifi());

        if(place.getType().equals(Place.TYPE_LABORATORY)){
            TextView capacity = popupView.findViewById(R.id.capacity);
            capacity.setText(Integer.toString(place.getCapacity()));

            ImageView computers = popupView.findViewById(R.id.imagePc);
            computers.setImageResource(R.drawable.icon_pc);
            AppCompatCheckBox computersCheck = popupView.findViewById(R.id.checkboxPC);
            computersCheck.setChecked(place.isComputers());

            ImageView projector = popupView.findViewById(R.id.imageProjector);
            projector.setImageResource(R.drawable.icon_projector);
            AppCompatCheckBox projectorCheck = popupView.findViewById(R.id.checkboxProjector);
            projectorCheck.setChecked(place.isComputers());

            ImageView extintor = popupView.findViewById(R.id.imageExtintor);
            extintor.setImageResource(R.drawable.icon_ext);
            AppCompatCheckBox extintorCheck = popupView.findViewById(R.id.checkboxExtintor);
            extintorCheck.setChecked(place.isComputers());





//        setElementsInView();
        final PopupWindow popPlaces = new PopupWindow(popupView, width, height, focusable);
        popPlaces.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        TextView floor = findViewById(R.id.floor);
//        floor.setText(Integer.toString(place.getFloor()));
//
//        ImageView wifi = findViewById(R.id.imageWiFi);
//        wifi.setImageResource(R.drawable.icon_wifi);
//        AppCompatCheckBox wifiCheck = findViewById(R.id.checkboxWiFi);
//        wifiCheck.setChecked(place.isWifi());
//
//        if(place.getType().equals(Place.TYPE_LABORATORY)){
//            TextView capacity = findViewById(R.id.capacity);
//            capacity.setText(Integer.toString(place.getCapacity()));
//
//            ImageView computers = findViewById(R.id.imagePc);
//            computers.setImageResource(R.drawable.icon_pc);
//            AppCompatCheckBox computersCheck = findViewById(R.id.checkboxPC);
//            computersCheck.setChecked(place.isComputers());
//
//            ImageView projector = findViewById(R.id.imageProjector);
//            projector.setImageResource(R.drawable.icon_projector);
//            AppCompatCheckBox projectorCheck = findViewById(R.id.checkboxProjector);
//            projectorCheck.setChecked(place.isComputers());
//
//
//            ImageView extintor = findViewById(R.id.imageExtintor);
//            extintor.setImageResource(R.drawable.icon_ext);
//            AppCompatCheckBox extintorCheck = findViewById(R.id.checkboxExtintor);
//            extintorCheck.setChecked(place.isComputers());
//
//        }






    }
}

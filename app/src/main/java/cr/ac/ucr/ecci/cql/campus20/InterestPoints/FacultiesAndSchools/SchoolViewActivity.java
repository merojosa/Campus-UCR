package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentsList;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler{

    private ListView listView;

    private Place place;
    private Faculty faculty;
    private CommentsList.CommentListOnClickHandler onClickHandler;

    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout mLayout;
    private PopupWindow mPopupWindow;

    private boolean listHelper; //Para saber si ya hay items de opciones en la lista
    private String auxLastItemSelected; //Para guardar el ultimo elemento seleccionado y ayudar a ocultar elementos

    private final static String[] listOptions = {"Laboratorios", "Asociacion de estudiantes", "Baños"};
    private List<Place> optionsSchools =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        this.place = getIntent().getParcelableExtra("place");
        mContext = getApplicationContext();
        mActivity = SchoolViewActivity.this;
        mLayout = findViewById(R.id.school_view);

        Intent intentPlace = getIntent();
        String placeName = intentPlace.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.schoolName);

        populateOptionsList();

        listHelper = false;
        listView = (ListView) findViewById(R.id.listSchoolItems);
        setListComponents();

        tittle.setText(placeName);
        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentPopUp commentPopUp = new CommentPopUp(view, place);
            }
        });
        /*POPUP*/

    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    //Para hacer pruebas
    public void auxTest(List<Place> list, String[] array){
        for (String val:array) {
            School s =  new School();
            s.setName(val);
            list.add(s);
        }
    }

    public void setListComponents(){

        //Para pruebas
        List<Place> valuesLabs = new ArrayList<>();
        List<Place> valuesAsc = new ArrayList<>();
        List<Place> valuesBath = new ArrayList<>();

        String[] valuesLabs1 = new String[] { "Lab1", "Lab2", "Lab3"};
        String[] valuesAsc1 = new String[] { "Horario: 7:00-19:00"};
        String[] valuesBath1 = new String[] { "Baño 1er piso", "Baño 2do piso", "Baño 3er piso"};

        auxTest(valuesLabs, valuesLabs1);
        auxTest(valuesAsc, valuesAsc1);
        auxTest(valuesBath, valuesBath1);
        //

        //Creacion del adaptador
        SchoolPlacesAdapter adapter = new SchoolPlacesAdapter(optionsSchools, this, listOptions);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place  itemValue = (Place) listView.getItemAtPosition(position);
                //Si se le da click a un elemento que conforma el dropdown
                if(itemValue.getName().equals(listOptions[0]) || itemValue.getName().equals(listOptions[1]) || itemValue.getName().equals(listOptions[2])){
                    if(!listHelper){

                        //Sacar a metodo aparte
                        List<Place> auxList = new ArrayList<>();
                        if(itemValue.getName() == listOptions[0]){ //Laboratorios
                            auxList =valuesLabs;
                            auxLastItemSelected = listOptions[0];
                        }else if(itemValue.getName() == listOptions[1]){ //Asocias
                            auxList =valuesAsc;
                            auxLastItemSelected = listOptions[1];
                        }else if(itemValue.getName() == listOptions[2]){ //Baños
                            auxList =valuesBath;
                            auxLastItemSelected = listOptions[2];
                        }
                        addOptionsOnList(optionsSchools, position, auxList);
                        //

                        listHelper = true;
                        adapter.verifyImage(listHelper, itemValue.getName());

                    }else{
                        //cuando ya se muestran opciones en la lista se debe limpiar
                        optionsSchools.clear();
                        populateOptionsList();
                        if(!itemValue.getName().equals(auxLastItemSelected)){  //Si seleccione uno diferente al ultimo seleccionado debo mostrar sus items

                            //Sacar a metodo aparte
                            List<Place> auxList = new ArrayList<>();
                            int auxPos = 0;
                            if(itemValue.getName() == listOptions[0]){ //Laboratorios
                                auxList =valuesLabs;
                                auxLastItemSelected = listOptions[0];
                            }else if(itemValue.getName() == listOptions[1]){ //Asocias
                                auxList =valuesAsc;
                                auxLastItemSelected = listOptions[1];
                                auxPos = 1;
                            }else if(itemValue.getName() == listOptions[2]){ //Baños
                                auxList =valuesBath;
                                auxLastItemSelected = listOptions[2];
                                auxPos = 2;
                            }
                            addOptionsOnList(optionsSchools, auxPos, auxList);
                            //

                        }else{
                            listHelper = false;
                        }
                    }
                    adapter.verifyImage(listHelper, itemValue.getName());
                    adapter.notifyDataSetChanged();
                }else{
                    //cuando se le da click a un item de baño, lab... especifico
                    //Debe levantar el fragmento u otra activity
                }

            }
        });
    }

    //Metodo para agregar items relacionados con la opcion seleccionada
    public void addOptionsOnList(List<Place> itemsList, int position,  List<Place> options){
        for (int i = 0; i < options.size(); i++) {
            itemsList.add(position + i+1, options.get(i));
        }
    }

    //Para las lista de opciones para mostrar en la vista de escuelas
    public void populateOptionsList(){
        for (String val: listOptions) {
            School s = new School();
            s.setName(val);
            optionsSchools.add(s);
        }
    }

}

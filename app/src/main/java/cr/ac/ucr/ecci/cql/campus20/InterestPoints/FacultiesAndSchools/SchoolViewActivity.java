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
import android.graphics.Bitmap;
import android.net.Uri;
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
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Asociation;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Bathroom;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Laboratory;
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

    private List<Place> laboratoriesList;
    private List<Place> bathroomsList;
    private List<Place> asociationList;


    private DatabaseReference refLabs;
    private DatabaseReference refBathrooms;
    private DatabaseReference refAsociation;

    private ValueEventListener listenerLabs;
    private ValueEventListener listenerBathrooms;
    private ValueEventListener listenerAsociation;
    private CommentPopUp commentPopUp;


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

        //Para opcines del dropdown
        populateOptionsList();
        getPlacesInSchool();
        listHelper = false;
        listView = (ListView) findViewById(R.id.listSchoolItems);
        setListComponents();

        tittle.setText(placeName);
        Activity activity = this;
        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commentPopUp = new CommentPopUp(view, activity, place);
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

    /*
     * MPS4 - 02 Foto en el comentario
     * Participantes: D: Sebastián Cruz, N: Luis Carvajal
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            Uri uri = data.getData();
            commentPopUp.setImg(uri);
        }
    }

    public void setListComponents(){

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
                            auxList =laboratoriesList;
                            auxLastItemSelected = listOptions[0];
                        }else if(itemValue.getName() == listOptions[1]){ //Asocias
                            auxList =asociationList;
                            auxLastItemSelected = listOptions[1];
                        }else if(itemValue.getName() == listOptions[2]){ //Baños
                            auxList =bathroomsList;
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
                                auxList =laboratoriesList;
                                auxLastItemSelected = listOptions[0];
                            }else if(itemValue.getName() == listOptions[1]){ //Asocias
                                auxList =asociationList;
                                auxLastItemSelected = listOptions[1];
                                auxPos = 1;
                            }else if(itemValue.getName() == listOptions[2]){ //Baños
                                auxList =bathroomsList;
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

                    SchoolPlacesPopUp schoolPlacesPopUp = new SchoolPlacesPopUp(view, itemValue);

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

    private void getPlacesInSchool(){
        laboratoriesList = new ArrayList<>();
        bathroomsList = new ArrayList<>();
        asociationList = new ArrayList<>();

        FirebaseDB db = new FirebaseDB();
        getLabs(db);
        getBathrooms(db);
//        getAsociation(db);
    }

    public void getLabs(FirebaseDB db){
        refLabs = db.getReference(Place.TYPE_LABORATORY);
        listenerLabs = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot laboratory : dataSnapshot.getChildren()) {
                    if(laboratory.getValue(Laboratory.class).getId_school_fk() == place.getId()){
                        laboratoriesList.add(laboratory.getValue(Laboratory.class));
                    }
                }
                removeListenerLabs();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        };
        refLabs.addValueEventListener(listenerLabs);
    }

    public void getBathrooms(FirebaseDB db){
        refBathrooms = db.getReference(Place.TYPE_BATHROOM);
        listenerBathrooms = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot bathroom : dataSnapshot.getChildren()) {
                    if(bathroom.getValue(Bathroom.class).getId_school_fk() == place.getId()){
                        bathroomsList.add(bathroom.getValue(Bathroom.class));
                    }
                }
                removeListenerBathrooms();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        };
        refBathrooms.addValueEventListener(listenerBathrooms);
    }


    private void removeListenerLabs(){
        if(refLabs != null && listenerLabs != null)
            refLabs.removeEventListener(listenerLabs);
    }

    private void removeListenerBathrooms(){
        if(refBathrooms != null && listenerBathrooms != null)
            refBathrooms.removeEventListener(listenerBathrooms);
    }

}

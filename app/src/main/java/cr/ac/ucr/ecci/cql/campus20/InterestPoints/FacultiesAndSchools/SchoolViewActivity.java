package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private ListView listView;

    private School school;
    private Place place;
    private Faculty faculty;

    private boolean listHelper; //Para saber si ya hay items de opciones en la lista
    private String auxLastItemSelected; //Para guardar el ultimo elemento seleccionado y ayudar a ocultar elementos

    private final static String[] listOptions = {"Laboratorios", "Asociacion de estudiantes", "Baños"};

    String schoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        Intent intentSchool = getIntent();
        schoolName = intentSchool.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.schoolName);
//        School school = School.select(getApplicationContext(), schoolName);
//        if(school != null) {
//            ImageView image = findViewById(R.id.schoolImage);
//            image.setImageResource(school.getImage());
//        }
        tittle.setText(schoolName);

        listHelper = false;
        listView = (ListView) findViewById(R.id.listSchoolItems);
        setListComponents();

    }

//    /**
//     * EFE: send the user to the location in maps
//     * REQ:
//     * view: send by the button that calls this method
//     * latitude : latitude of the point that the user wants to go.
//     * longitude: longitude of the point that the user wants to go.
//     * MOD: ---
//     * */
//    public void goTo(View view) {
//        Intent intent = new Intent(this, Map.class);
//        startActivity(intent);
//    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void setListComponents(){
        String[] values = new String[] { "Laboratorios", "Asociacion de estudiantes", "Baños"};

        String[] valuesLabs = new String[] { "Lab1", "Lab2", "Lab3"};
        String[] valuesAsc = new String[] { "Horario: 7:00-19:00"};
        String[] valuesBath = new String[] { "Baño 1er piso", "Baño 2do piso", "Baño 3er piso"};

        List<String> itemsList = new ArrayList<String>(Arrays.asList(values));

        SchoolPlacesAdapter adapter = new SchoolPlacesAdapter(itemsList, this, listHelper);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listView.requestFocus();
                String  itemValue = (String) listView.getItemAtPosition(position);
                if(!listHelper){

                    //Sacar a metodo aparte
                    String[] auxArray = {};
                    if(itemValue == listOptions[0]){ //Laboratorios
                        auxArray =valuesLabs;
                        auxLastItemSelected = listOptions[0];
                    }else if(itemValue == listOptions[1]){ //Asocias
                        auxArray =valuesAsc;
                        auxLastItemSelected = listOptions[1];
                    }else if(itemValue == listOptions[2]){ //Baños
                        auxArray =valuesBath;
                        auxLastItemSelected = listOptions[2];
                    }
                    addOptionsOnList(itemsList, position, auxArray);
                    //
                    listHelper = true;
                    adapter.verifyImage(listHelper);


                }else{
                    //cuando ya se muestran opciones en la lista se debe limpiar
                    itemsList.clear();
                    addOptionsOnList(itemsList, -1, listOptions); //Se manda -1 para evitar caida en este caso
                    if(!itemValue.equals(auxLastItemSelected)){  //Si seleccione uno diferente al ultimo seleccionado debo mostrar sus items
                        //Sacar a metodo aparte
                        String[] auxArray = {};
                        int auxPos = 0;
                        if(itemValue == listOptions[0]){ //Laboratorios
                            auxArray =valuesLabs;
                            auxLastItemSelected = listOptions[0];
//                            auxPos = 0;
                        }else if(itemValue == listOptions[1]){ //Asocias
                            auxArray =valuesAsc;
                            auxLastItemSelected = listOptions[1];
                            auxPos = 1;
                        }else if(itemValue == listOptions[2]){ //Baños
                            auxArray =valuesBath;
                            auxLastItemSelected = listOptions[2];
                            auxPos = 2;
                        }
                        addOptionsOnList(itemsList, auxPos, auxArray);
                        //
                    }else{
                        listHelper = false;
                        adapter.verifyImage(listHelper);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    //Metodo para agregar items relacionados con la opcion seleccionada
    public void addOptionsOnList(List<String> itemsList, int position, String[] options){
        for (int i = 0; i < options.length; i++) {
            itemsList.add(position + i+1, options[i]);
        }
    }

}

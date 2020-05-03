package cr.ac.ucr.ecci.cql.campus20.FacultiesAndSchools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class FacultiesActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<GeneralData> temp = new ArrayList<>();
    private List<Faculty> facultiesList;

//    private Faculty faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Facultades");
            getSupportActionBar().show();
        }

        mRecyclerView = findViewById(R.id.rv_list_item);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);

        facultiesList = Faculty.getFacultiesList(getApplicationContext());

        setDataList();
        mListAdapter.setListData(temp);
    }

    // el clic en una facultad debe llevarme a la lista de escuelas
    @Override
    public void onClick(String title) {

        boolean finded = false;
        int index = 0;
        while (index < facultiesList.size() && !finded){
            if(facultiesList.get(index).getTitle().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }
        //testDatabase(index);

        Intent childActivity = new Intent(FacultiesActivity.this, SchoolsActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);

        startActivity(childActivity);
    }

    public void setDataList(){
        temp.addAll(facultiesList);
    }
/*
    private void testDatabase(int index){
        /*Prueba de base de datos, obtiene las escuelas de una facultad y la ubicación de cada escuela.
        List<School> schools = new ArrayList<>();
        schools = School.read(getApplicationContext(), index);
        for(School s : schools){
            Log.d("schoolName", s.getName());
            Place p = Place.read(getApplicationContext(), s.getId_place_fk());
            Coordinate c = Coordinate.read(getApplicationContext(), p.getId());
            Log.d("coordinate", "Coordinates for " + s.getName() + ": " + Double.toString(c.getLatitude()) + ", " + Double.toString(c.getLongitude()));
            List<Comment> commentList = new ArrayList<>();
            commentList = Comment.read(getApplicationContext(), s.getId_place_fk());
            for(Comment comment : commentList){
                Log.d("comment", comment.getDescription() + " " + comment.getDate());
            }
        }
    }
*/
}

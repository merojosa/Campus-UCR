package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;


public class SchoolsActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private ListAdapter mListAdapter;

    private List<Place> temp = new ArrayList<>();
    private List<School> schoolsList;
    private ProgressBar spinner;

    private DatabaseReference ref;
    private ValueEventListener listener;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        spinner = findViewById(R.id.schoolProgressBar);
        spinner.setVisibility(View.VISIBLE);
        Intent intentFaculties = getIntent();
        String facultyName = intentFaculties.getStringExtra(Intent.EXTRA_TEXT);
        int facultyId = intentFaculties.getIntExtra(Intent.EXTRA_INDEX, 0);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(facultyName); //Se saca el nombre de la faultad donde estoy
            getSupportActionBar().show();
        }

        RecyclerView mRecyclerView = findViewById(R.id.rv_list_item);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);

        temp = new ArrayList<>();
        schoolsList = new ArrayList<>();
        getSchoolsList(facultyId);
    }

    @Override
    public void onPause(){
        super.onPause();
        removeListener();
    }

    @Override
    public void onClick(String title) {

        boolean finded = false;
        int index = 0;
        while (index < schoolsList.size() && !finded){
            if(schoolsList.get(index).getName().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }
        Intent childActivity = new Intent(SchoolsActivity.this, SchoolViewActivity.class);// Cambiar por map
        childActivity.putExtra("typeActivity", 1);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra("attribute", schoolsList.get(index).getDescription());

        // Setting school and coordinate objects
        School school = schoolsList.get(index);
        childActivity.putExtra("place", school);
        childActivity.putExtra("title", school.getName());
        goToMap(school, childActivity);

    }

    /*Reads the list from Firebase RTD and updates the UI when the list fetch is completed asynchronously.*/
    private void getSchoolsList(int facultyId){
        FirebaseDB db = new FirebaseDB();
        ref = db.getReference(Place.TYPE_SCHOOL);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot school : dataSnapshot.getChildren()){
                    schoolsList.add(school.getValue(School.class));
                }
                setDataList();
                mListAdapter.setListData(temp);
                mListAdapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE);
                removeListener();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        };
        ref.orderByChild("id_faculty_fk").equalTo(facultyId).addValueEventListener(listener);
    }

    private void goToMap(School school, Intent childActivity){
        // Enviando los datos correctos al mapa
        childActivity.putExtra("place", school);
        childActivity.putExtra("index", 1);
        childActivity.putExtra("title", school.getName());
        childActivity.putExtra("latitude", school.getLatitude());
        childActivity.putExtra("longitude", school.getLongitude());
        startActivity(childActivity);
    }

    public void setDataList(){
        temp.addAll(schoolsList);
    }

    private void removeListener(){
        if(ref != null && listener != null)
            ref.removeEventListener(listener);
    }
}

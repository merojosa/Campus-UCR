package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Coordinate;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;


public class SchoolsActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<GeneralData> temp = new ArrayList<>();
    private List<School> schoolsList;
    private ProgressBar spinner;

    private FirebaseDB db;
    private School school;
    private Coordinate coordinateGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db = new FirebaseDB();
        spinner = findViewById(R.id.schoolProgressBar);
        spinner.setVisibility(View.VISIBLE);
        Intent intentFaculties = getIntent();
        String facultyName = intentFaculties.getStringExtra(Intent.EXTRA_TEXT);
        int facultyId = intentFaculties.getIntExtra(Intent.EXTRA_INDEX, 0);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(facultyName); //Se saca el nombre de la faultad donde estoy

            getSupportActionBar().show();
        }

        mRecyclerView = findViewById(R.id.rv_list_item);

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
    public void onClick(String title) {

        boolean finded = false;
        int index = 0;
        while (index < schoolsList.size() && !finded){
            if(schoolsList.get(index).getTitle().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }
        Intent childActivity = new Intent(SchoolsActivity.this, Map.class);
        childActivity.putExtra("typeActivity", 1);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra("attribute", schoolsList.get(index).getDescription());

        // Setting school and coordinate objects
        this.school = schoolsList.get(index);
        this.coordinateGlobal = new Coordinate();
        //coordinate.setLatitude(9.911820721309361);
        //coordinate.setLongitude(-84.08615402814974);
        getSpecificCoordenates(school, childActivity);


    }

    /*Reads the list from Firebase RTD and updates the UI when the list fetch is completed asynchronously.*/
    private void getSchoolsList(int facultyId){
        DatabaseReference ref = db.getReference("School");
        ref.orderByChild("id_faculty_fk").equalTo(facultyId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot school : dataSnapshot.getChildren()){
                    schoolsList.add(school.getValue(School.class));
                }
                setDataList();
                mListAdapter.setListData(temp);
                mListAdapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getSpecificCoordenates(School school, Intent childActivity){
        DatabaseReference ref = db.getReference("Place");
        ref.orderByChild("id").equalTo(school.getId_place_fk()).addValueEventListener(new ValueEventListener() {
            private Place place;

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot tempPlace : dataSnapshot.getChildren()){
                    place = tempPlace.getValue(Place.class);
                }

                // ------------------ Tomando las cooredenadas del lugar ---------------------------

                DatabaseReference ref2 = db.getReference("Coordinate");
                ref2.orderByChild("id_place_fk").equalTo(place.getId()).addValueEventListener(new ValueEventListener() {
                    private Coordinate coordinate;

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot tempCoordinate : dataSnapshot.getChildren()){
                            coordinate = tempCoordinate.getValue(Coordinate.class);
                        }
                        // Enviando los datos correctos al mapa

                        childActivity.putExtra("place", school);
                        childActivity.putExtra("index", 1);
                        childActivity.putExtra("coordinate", coordinate);

                        startActivity(childActivity);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
                    }
                });

                // ---------------------------------------------------------------------------------

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        });
    }

   public void setDataList(){ temp.addAll(schoolsList); }
}

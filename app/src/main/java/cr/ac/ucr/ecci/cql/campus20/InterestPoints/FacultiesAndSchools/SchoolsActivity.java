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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db = new FirebaseDB(getApplicationContext());
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
        /*schoolsList = school.read(getApplicationContext(), facultyName); //revisar la intancia facultyName

        setDataList();
        mListAdapter.setListData(temp);*/

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
        startActivity(childActivity);
    }

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

   public void setDataList(){ temp.addAll(schoolsList); }
}

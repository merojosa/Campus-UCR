package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class FacultiesActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<Place> temp = new ArrayList<>();
    private List<Faculty> facultiesList;

    private ProgressBar spinner;

    private DatabaseReference ref;
    private ValueEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);
        if(getSupportActionBar() != null){
            setActivityTitle();
        }

        spinner = findViewById(R.id.facultyProgressBar);
        spinner.setVisibility(View.VISIBLE);
        setupRecyclerView();
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);
        facultiesList = new ArrayList<>();
        temp = new ArrayList<>();
        getFacultiesList();
    }

    public void onPause(){
        super.onPause();
        removeListener();
    }

    // el clic en una facultad debe llevarme a la lista de escuelas
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(String title) {

        boolean finded = false;
        int index = 0;
        while (index < facultiesList.size() && !finded){
            if(facultiesList.get(index).getName().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }

        Intent childActivity = new Intent(FacultiesActivity.this, SchoolsActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra(Intent.EXTRA_INDEX, facultiesList.get(index).getId());

        startActivity(childActivity);
    }

    /*This method creates the search box in toolbar and filters the rows according to the search criteria.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ip_search_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)menuItem.getActionView();
        searchView.setQueryHint("Buscar...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onQueryTextChange(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mListAdapter.filter(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setupRecyclerView() {
        mRecyclerView = findViewById(R.id.rv_list_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void getFacultiesList(){
        FirebaseDB db = new FirebaseDB();
        ref = db.getReference(Place.TYPE_FACULTY);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot faculty : dataSnapshot.getChildren()) {
                    facultiesList.add(faculty.getValue(Faculty.class));
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

        ref.addValueEventListener(listener);
    }

    public void setDataList(){
        temp.addAll(facultiesList);
    }

    private void setActivityTitle(){
        ActivityInfoDao activityInfoDao;
        IPRoomDatabase roomDatabase = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").build();
        activityInfoDao = roomDatabase.activityInfoDao();
        AsyncTask.execute(() -> {
            Objects.requireNonNull(getSupportActionBar()).setTitle(activityInfoDao.getActivityName(DeploymentScript.ActivityNames.FACULTIES.ordinal()));
            getSupportActionBar().show();
        });
    }

    private void removeListener(){
        if(ref != null && listener != null)
            ref.removeEventListener(listener);
    }

}

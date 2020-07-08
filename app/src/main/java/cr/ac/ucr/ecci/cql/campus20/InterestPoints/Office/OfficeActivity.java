package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Office;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Office;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class OfficeActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<Place> temp = new ArrayList<>();
    private List<Office> officeList;

    private ProgressBar spinner;

    private DatabaseReference ref;
    private ValueEventListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);

        if (getSupportActionBar() != null) {
            setActivityTitle();
        }

        spinner = findViewById(R.id.officeProgressBar);
        spinner.setVisibility(View.VISIBLE);

        setupRecyclerView();
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);
        temp = new ArrayList<>();
        officeList = new ArrayList<>();
        getOfficesList();
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
        while (index < officeList.size() && !finded){
            if(officeList.get(index).getName().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }
        Intent childActivity = new Intent(OfficeActivity.this, Map.class);
        childActivity.putExtra("typeActivity", 5);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra("attribute", officeList.get(index).getDescription());


        // Setting school and coordinate objects
        Office office = officeList.get(index);

        childActivity.putExtra("place", (Parcelable) office);
        childActivity.putExtra("index", 2);

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

    /*Reads the list from Firebase RTD and updates the UI when the list fetch is completed asynchronously.*/
    private void getOfficesList(){
        FirebaseDB db = new FirebaseDB();
        ref = db.getReference(Place.TYPE_OFFICE);
        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot office : dataSnapshot.getChildren()){
                    officeList.add(office.getValue(Office.class));
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
        temp.addAll(officeList);
    }

    private void setActivityTitle(){
        ActivityInfoDao activityInfoDao;
        IPRoomDatabase roomDatabase = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").build();
        activityInfoDao = roomDatabase.activityInfoDao();
        AsyncTask.execute(() -> {
            Objects.requireNonNull(getSupportActionBar()).setTitle(activityInfoDao.getActivityName(DeploymentScript.ActivityNames.OFFICES.ordinal()));
            getSupportActionBar().show();
        });
    }

    private void removeListener(){
        if(ref != null && listener != null)
            ref.removeEventListener(listener);
    }
}
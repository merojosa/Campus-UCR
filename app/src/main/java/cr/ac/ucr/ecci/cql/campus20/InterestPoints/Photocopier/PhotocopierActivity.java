package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Photocopier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Photocopier;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.ActivityInfoDao;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.RoomModel.IPRoomDatabase;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class PhotocopierActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<Place> temp = new ArrayList<Place>();
    private List<Photocopier> photocopierList;

    private ProgressBar spinner;
    private Photocopier photocopier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photocopier);

        if(getSupportActionBar() != null){
            setActivityTitle();
        }

        spinner = findViewById(R.id.photocopierProgressBar);
        spinner.setVisibility(View.VISIBLE);

        setupRecyclerView();
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);
        temp = new ArrayList<>();
        photocopierList = new ArrayList<>();
        getPhotocopierList();
    }

    @Override
    public void onClick(String title) {
        boolean finded = false;
        int index = 0;
        while (index < photocopierList.size() && !finded){
            if(photocopierList.get(index).getName().equals(title)){
                finded = true;
            }else{
                ++index;
            }
        }
        Intent childActivity = new Intent(PhotocopierActivity.this, Map.class);
        childActivity.putExtra("typeActivity", "photocopier");
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra("attribute", photocopierList.get(index).getDescription());


        // Setting school and coordinate objects
        this.photocopier = photocopierList.get(index);

        childActivity.putExtra("place", photocopier);
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
    private void getPhotocopierList(){
        FirebaseDB db = new FirebaseDB();
        DatabaseReference ref = db.getReference("Photocopier");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot photocopier : dataSnapshot.getChildren()){
                    photocopierList.add(photocopier.getValue(Photocopier.class));
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

    public void setDataList(){
        temp.addAll(photocopierList);
    }

    private void setActivityTitle(){
        ActivityInfoDao activityInfoDao;
        IPRoomDatabase roomDatabase = Room.databaseBuilder(getApplicationContext(), IPRoomDatabase.class, "IPRoomDatabase").build();
        activityInfoDao = roomDatabase.activityInfoDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                getSupportActionBar().setTitle(activityInfoDao.getActivityName(DeploymentScript.ActivityNames.PHOTOCOPIERS.ordinal()));
                getSupportActionBar().show();
            }
        });
    }
}
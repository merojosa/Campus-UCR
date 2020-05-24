package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.SearchView;

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
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class FacultiesActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<GeneralData> temp = new ArrayList<>();
    private List<Faculty> facultiesList;

    private ProgressBar spinner;

    private FirebaseDB db;

//    private Faculty faculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new FirebaseDB(getApplicationContext());
        //new DeploymentScript().RunScript(getApplicationContext(), db);
        setContentView(R.layout.activity_faculties);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Facultades");
            getSupportActionBar().show();
        }

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        setupRecyclerView();
        mListAdapter = new ListAdapter(this);
        mRecyclerView.setAdapter(mListAdapter);
        facultiesList = new ArrayList<>();
        temp = new ArrayList<>();
        //facultiesList = Faculty.getFacultiesList(getApplicationContext());
        getFacultiesList();
        //setDataList();
        //mListAdapter.setListData(temp);
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

        Intent childActivity = new Intent(FacultiesActivity.this, SchoolsActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);

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
        mRecyclerView = (RecyclerView)findViewById(R.id.rv_list_item);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    private void getFacultiesList(){
        DatabaseReference ref = db.getReference("Faculty");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot faculty : dataSnapshot.getChildren()){
                    facultiesList.add(faculty.getValue(Faculty.class));
                }
                setDataList();
                mListAdapter.setListData(temp);
                mListAdapter.notifyDataSetChanged();
                spinner.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setDataList(){
        temp.addAll(facultiesList);
    }

}

package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.DeploymentScript;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
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
        DeploymentScript.RunScript(getApplicationContext());
        setContentView(R.layout.activity_faculties);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Facultades");
            getSupportActionBar().show();
        }

        /*mRecyclerView = findViewById(R.id.rv_list_item);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);*/
        setupRecyclerView();
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

    public void setDataList(){
        temp.addAll(facultiesList);
    }

}

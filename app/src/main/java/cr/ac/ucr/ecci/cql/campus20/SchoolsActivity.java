package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.IPModel.School;


public class SchoolsActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<GeneralData> temp = new ArrayList<>();
    private List<School> schoolsList;

    private School school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intentFaculties = getIntent();
        String facultyName = intentFaculties.getStringExtra(Intent.EXTRA_TEXT);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(facultyName);
            getSupportActionBar().show();
        }

        mRecyclerView = findViewById(R.id.rv_list_item);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mListAdapter = new ListAdapter(this);
            mRecyclerView.setAdapter(mListAdapter);


        school = new School();

        schoolsList = school.getSchoolList(getApplicationContext(), facultyName);

        setDataList();
        mListAdapter.setListData(temp);

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
        Intent childActivity = new Intent(SchoolsActivity.this, SchoolViewActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
//        childActivity.putExtra("attribute", Integer.toString(facultiesList.get(index).getAttribute()));

        startActivity(childActivity);
    }

    public void setDataList(){
        temp.addAll(schoolsList);
    }
}


/*

public class FacultiesActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private List<GeneralData> temp = new ArrayList<>();
    private List<Faculty> facultiesList;

    private Faculty faculty;

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

        faculty = new Faculty();
        facultiesList = faculty.getFacultiesList(getApplicationContext());

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
        Intent childActivity = new Intent(FacultiesActivity.this, FacultyViewActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
//        childActivity.putExtra("attribute", Integer.toString(facultiesList.get(index).getAttribute()));

        startActivity(childActivity);
    }

    public void setDataList(){
        temp.addAll(facultiesList);
    }
}



 */
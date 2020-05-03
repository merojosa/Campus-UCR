package cr.ac.ucr.ecci.cql.campus20.FacultiesAndSchools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;


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
            getSupportActionBar().setTitle(facultyName); //Se saca el nombre de la faultad donde estoy

            getSupportActionBar().show();
        }

        mRecyclerView = findViewById(R.id.rv_list_item);

        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(linearLayoutManager);
            mRecyclerView.setHasFixedSize(true);
            mListAdapter = new ListAdapter(this);
            mRecyclerView.setAdapter(mListAdapter);

        schoolsList = school.read(getApplicationContext(), facultyName); //revisar la intancia facultyName

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
        childActivity.putExtra("attribute", schoolsList.get(index).getDescription());

        startActivity(childActivity);
    }

   public void setDataList(){ temp.addAll(schoolsList); }
}

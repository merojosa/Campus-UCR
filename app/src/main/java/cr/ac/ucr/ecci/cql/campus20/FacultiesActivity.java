package cr.ac.ucr.ecci.cql.campus20;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.IPModel.Faculty;

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

        createFaculties();
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

    // Únicamente para efectos de prueba
    public void createFaculties() {
        List<Faculty> list = new ArrayList<>();
        String[] Faculties = {"Artes", "Ciencias Agroalimentarias", "Ciencias Básicas", "Ciencias Económicas", "Ciencias Sociales", "Derecho",
                "Educación", "Farmacia","Ingeniería", "Letras", "Medicina", "Microbiología", "Odontología"};

        for (int i = 0; i < Faculties.length; ++i) {
            list.add(new Faculty(i, Faculties[i], ""));
        }
        for(Faculty f : list){
            //TODO: Fix primary key constraint violation, check if records already exist in database before inserting.
            f.insert(getApplicationContext());
        }
    }

}

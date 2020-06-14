package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentPopUp;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment.CommentsList;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.School;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Mapbox.Map;
import cr.ac.ucr.ecci.cql.campus20.R;

public class SchoolViewActivity extends AppCompatActivity implements ListAdapter.ListAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;

    private School school;
    private Place place;
    private Faculty faculty;
    private CommentsList.CommentListOnClickHandler onClickHandler;

    String schoolName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        Intent intentSchool = getIntent();
        schoolName = intentSchool.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.schoolName);
        tittle.setText(schoolName);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CommentPopUp commentPopUp = new CommentPopUp(); //Mandar cosas
                commentPopUp.showCommentsPopup(view);
            }
        });
        /*POPUP*/

    }

    @Override
    public void onClick(String title) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}

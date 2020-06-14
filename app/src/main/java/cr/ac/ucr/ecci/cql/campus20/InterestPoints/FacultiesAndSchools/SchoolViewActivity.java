package cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout mLayout;
    private PopupWindow mPopupWindow;

    String schoolName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_view);

        mContext = getApplicationContext();
        mActivity = SchoolViewActivity.this;
        mLayout = findViewById(R.id.school_view);

        Intent intentSchool = getIntent();
        schoolName = intentSchool.getStringExtra(Intent.EXTRA_TEXT);

        TextView tittle = findViewById(R.id.schoolName);
        tittle.setText(schoolName);

        /*POPUP*/
        Button popButton = findViewById(R.id.comments);
        popButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Comment> comments = new ArrayList<>();
                /*Comentarios de prueba*/
                /*comments.add(new Comment(0, 0, "Hola mundo", "", 4.3f, "", 1, 0));
                comments.add(new Comment(1, 0, "Adios mundo", "", 4.1f, "", 1, 1));
                comments.add(new Comment(2, 0, "Adios mundo", "", 4.1f, "", 1, 1));
                comments.add(new Comment(3, 0, "Adios mundo", "", 4.1f, "", 1, 1));
                comments.add(new Comment(4, 0, "Adios mundo", "", 4.1f, "", 1, 1));
                comments.add(new Comment(5, 0, "Adios mundo", "", 4.1f, "", 1, 1));
                comments.add(new Comment(6, 0, "Adios mundo", "", 4.1f, "", 1, 1));*/
                /*Comentarios de Firebase*/
                showComments(view);
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

    /*Reads the list from Firebase RTD and updates the UI when the list fetch is completed asynchronously.*/
    private void showComments(View view){
        FirebaseDB db = new FirebaseDB();
        DatabaseReference ref = db.getReference("Comment");
        List<Comment> commentList = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comment : dataSnapshot.getChildren()) {
                    commentList.add(comment.getValue(Comment.class));
                }
                CommentPopUp commentPopUp = new CommentPopUp(view, commentList); //Mandar cosas
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        });
    }

}

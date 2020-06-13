package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

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
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CommentPopUp extends AppCompatActivity implements CommentsList.CommentListOnClickHandler {

    private RecyclerView mRecyclerView;
    private CommentsList mCommensList;
    private List<GeneralData> tmp = new ArrayList<>();
    private List<Comment> Comentarios;
    private FirebaseDB db;

    public CommentPopUp(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new FirebaseDB();

        setupRecyclerView();
        mCommensList = new CommentsList(this);
        mRecyclerView.setAdapter(mCommensList);
        Comentarios = new ArrayList<>();
        tmp = new ArrayList<>();
        getCommentsList();
    }


    @Override
    public void onClick(String title) {
        /*
        Intent childActivity = new Intent(CommentPopUp.this, CommentsActivity.class);
        childActivity.putExtra(Intent.EXTRA_TEXT, title);
        childActivity.putExtra(Intent.EXTRA_INDEX, Comentarios.get(index).getId());

        startActivity(childActivity);
*/

    }

    private void setupRecyclerView(){
        mRecyclerView = findViewById(R.id.comments_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    //Mandarle de quién
    private void getCommentsList(){
        DatabaseReference ref = db.getReference("Comment");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot comment : dataSnapshot.getChildren()){
                    Comentarios.add(comment.getValue(Comment.class));
                }
                setDataList();
                mCommensList.setListData(tmp);
                mCommensList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        });
    }

    //Quizá hacer consulta aquí
    public void setDataList(){
        tmp.addAll(Comentarios);
    }


    /**
     * Crea lo necesario para levantar el popup
     * @param view
     */
    public void showCommentsPopup(final View view) {

        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.activity_comment_pop_up, null);
        int width = 375;//LinearLayout.LayoutParams.MATCH_PARENT; //revisar que sirve mejor
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; //Para la parte de atrás

        /*Métodos de CommentPopUp*/

        final PopupWindow popComments = new PopupWindow(popupView, width, height, focusable);
        popComments.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        //Para salir tocar afuera
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popComments.dismiss();
                return true;
            }
        });
    /*POPUP*/
    }

}
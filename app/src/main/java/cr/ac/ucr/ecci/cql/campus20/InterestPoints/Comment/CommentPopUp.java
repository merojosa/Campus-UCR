package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CommentPopUp extends AppCompatActivity implements CommentsList.CommentListOnClickHandler {

    private RecyclerView mRecyclerView;
    private List<Comment> tmp;
    private List<Comment> Comentarios;
    private CommentsList mListAdapter;
    private FirebaseDB db;
    private DatabaseReference ref;
    private ValueEventListener listener;
    private View view;
    private RatingBar rt;
    private EditText editComment;
    private Button getRating;
    private Place place;
    private String comment;
    private float rate;
    private Button setLike;
    private Button setDislike;
    private int like;
    private int dislike;

    public CommentPopUp(){}

    /**
     * Crea lo necesario para levantar el popup
     * @param view
     */
    public CommentPopUp(final View view, Place place) {
        db = new FirebaseDB();
        this.place = place;
    /*Popup*/
        LayoutInflater inflater = (LayoutInflater)
                view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.activity_comment_pop_up, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT; //revisar que sirve mejor
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; //Para la parte de atrás
        this.view = popupView;
        Comentarios = place.comments != null? place.comments : new ArrayList<>();
        tmp = new ArrayList<Comment>();

        /*Lista*/
        setupRecyclerView();
        mListAdapter = new CommentsList(this);
        mRecyclerView.setAdapter(mListAdapter);
        setDataList(); //foreing key
        mListAdapter.setListData(tmp);
        mListAdapter.notifyDataSetChanged();

        /*Ratingbar y comentario*/
        setupCommentRating();

        setCommentsListener();


        final PopupWindow popComments = new PopupWindow(popupView, width, height, focusable);
        popComments.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        //Para salir tocar afuera
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popComments.dismiss();
                ref.removeEventListener(listener);
                return true;
            }
        });

        setUpSendButton();
        /*POPUP*/
    }

    private void setUpSendButton(){
        Button sendButton = view.findViewById(R.id.enviar_c_r);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Agrega un comentario a firebase, probar solo con ECCI*/
                Comment comment = Comentarios.get(Comentarios.size() -1);
                comment.setId(comment.getId() + 1);
                ref.child(Integer.toString(comment.getId())).setValue(comment);
            }
        });
    }

    private void setupRecyclerView(){
        mRecyclerView = view.findViewById(R.id.comments_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
/*
        setLike = view.findViewById(R.id.like);
        setDislike = view.findViewById(R.id.dislike);

        setLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
*/
    }

    private void setupCommentRating(){
        editComment = view.findViewById(R.id.comentario);
        rt = view.findViewById(R.id.ratingBar);
        getRating = view.findViewById(R.id.enviar_c_r);
        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = rt.getRating();
                comment = editComment.getText().toString();
                String kk = String.valueOf(rate);
                Toast.makeText(view.getContext(), comment + kk, Toast.LENGTH_LONG).show();
            }
        });

    }

    //Quizá hacer consulta aquí
    public void setDataList(){
        tmp = new ArrayList<>();
        tmp.addAll(Comentarios);
    }

    /*Solo para probar, hay que poner algo más útil.*/
    @Override
    public void onClick(String title) {
        Toast.makeText(view.getContext(), title, Toast.LENGTH_LONG);
    }

    private void setCommentsListener(){

        db = new FirebaseDB();

        listener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Comentarios.clear();
                for (DataSnapshot comment : dataSnapshot.getChildren()) {
                    Comentarios.add(comment.getValue(Comment.class));
                }
                setDataList();
                mListAdapter.setListData(tmp);
                mListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        };

        ref = db.getReference(place.getType()).child(Integer.toString(place.getId())).child("comments");
        ref.addValueEventListener(listener);

    }
}
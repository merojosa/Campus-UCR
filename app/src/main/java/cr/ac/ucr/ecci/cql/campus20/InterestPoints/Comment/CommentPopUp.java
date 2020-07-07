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
import android.util.Log;
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
import java.util.Calendar;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.FacultiesActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.FacultiesAndSchools.SchoolsActivity;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.GeneralData;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Faculty;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.ListAdapter;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.Utilities.UtilDates;
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
    private Button setLike;
    private Button setDislike;
    private int like;
    private int dislike;
    private Button sortRating;
    private boolean auxSorting;

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
       // setupLikesnDislikes

        //Para ordenar por rating
        auxSorting = true; //true para ascendente, false para descendente
        sortRating = this.view.findViewById(R.id.sortRating);
        sortRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListAdapter.orderByRating(auxSorting);
                auxSorting = !auxSorting;
            }
        });

        final PopupWindow popComments = new PopupWindow(popupView, width, height, focusable);
        popComments.setAnimationStyle(R.style.popup_window_animation);
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
        /*POPUP*/
    }

    private void setupRecyclerView(){
        mRecyclerView = view.findViewById(R.id.comments_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onClick(int position, boolean like){
        Comment comment = Comentarios.get(position);
        if(comment != null) {
            if(like)
                ref.child(Integer.toString(comment.getId())).child("like").setValue(comment.getLike() + 1);
            else
                ref.child(Integer.toString(comment.getId())).child("dislike").setValue(comment.getDislike() + 1);
        }
    }

    private void setupCommentRating() {
        editComment = view.findViewById(R.id.comentario);
        rt = view.findViewById(R.id.ratingBar);
        getRating = view.findViewById(R.id.enviar_c_r);

        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Construcción del comentario*/
                Comment comment;
                //Si no hay comentarios agrega uno con ID 0
                if (Comentarios.isEmpty()){
                    comment = new Comment();
                    comment.setId(0);
                    comment.setId_place_fk(place.getId());
                    comment.setType(Place.TYPE_SCHOOL);
                    comment.setDescription(editComment.getText().toString());
                    comment.setDate(UtilDates.DateToString(Calendar.getInstance().getTime()));
                    float rate = rt.getRating();
                    comment.setRating(rate); // Repensar
                    Comentarios.add(comment);
                }else { //Si si hay agreguea uno más
                    comment = Comentarios.get(Comentarios.size()-1);
                    comment.setId(comment.getId() + 1);
                    comment.setId_place_fk(place.getId());
                    comment.setType(Place.TYPE_SCHOOL);
                    comment.setDescription(editComment.getText().toString());
                    comment.setDate(UtilDates.DateToString(Calendar.getInstance().getTime()));
                    float rate = rt.getRating();
                    comment.setRating(rate);// Repensar
                }
                //inserta en firebase
                ref.child(Integer.toString(comment.getId())).setValue(comment);
            }
        });
    }

    public void setDataList(){
        tmp = new ArrayList<>();
        tmp.addAll(Comentarios);
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
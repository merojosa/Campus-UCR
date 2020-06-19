package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.R;

public class CommentDetail extends AppCompatActivity {

    private Comment comment;
    private TextView date;
    private TextView commentDescription;
    private RatingBar ratingBar;
    private TextView like;
    private TextView dislike;
    private ImageView photo;
    private Button goBack;

    /*
    * MPS4-01 Detalle de comentarios
    * D: Luis Carvajal N: Sebastián Cruz
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        this.comment = getIntent().getParcelableExtra("comment");

        date = findViewById(R.id.date);
        commentDescription = findViewById(R.id.commentText);
        ratingBar = findViewById(R.id.ratingBar);
        like = findViewById(R.id.no_like_detail);
        dislike = findViewById(R.id.no_dislike_detail);
        //photo = findViewById(R.id.commentPhoto);
        goBack = findViewById(R.id.commentGoBack);

        commentDescription.setText(comment.getDescription());
        date.setText(comment.getDate());
        like.setText(Integer.toString(comment.getLike()));
        dislike.setText(Integer.toString(comment.getDislike()));


        Log.d("Comment", comment.getDescription());

    }
}
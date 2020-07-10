package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
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
    private ProgressBar spinner;
    private FirebaseDB db;
    private StorageReference mStorageRef;

    /*
    * MPS4-01 Detalle de comentarios
    * D: Luis Carvajal N: Sebastián Cruz
    * */
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new FirebaseDB();
        mStorageRef = FirebaseStorage.getInstance().getReference(FirebaseDB.CLOUDSTORE_PREFIX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        this.comment = getIntent().getParcelableExtra("comment");

        date = findViewById(R.id.date);
        commentDescription = findViewById(R.id.commentText);
        ratingBar = findViewById(R.id.ratingBar);
        like = findViewById(R.id.no_like_detail);
        dislike = findViewById(R.id.no_dislike_detail);
        photo = findViewById(R.id.commentPhoto);
        goBack = findViewById(R.id.commentGoBack);
        spinner = findViewById(R.id.commentPhotoProgressBar);

        if(comment == null)
            return;

        commentDescription.setText(comment.getDescription());
        date.setText(comment.getDate());
        like.setText(Integer.toString(comment.getLike()));
        dislike.setText(Integer.toString(comment.getDislike()));

        /*Uses Picasso library to load a photo into imageView*/
        if(comment.getPhotoPath() != null) {
            spinner.setVisibility(View.VISIBLE);
            /*Downloads the photo from Firebase and shows it.*/
            mStorageRef.child(comment.getPhotoPath()).getDownloadUrl().addOnSuccessListener(uri -> {
                Picasso.get()
                        .load(uri)
                        .into(photo);
                spinner.setVisibility(View.GONE);
            }).addOnFailureListener(exception -> {
                /*If the photo isn't available (maybe because is still uploading) shows an error toast.*/
                spinner.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error: no se pudo cargar la imagen.", Toast.LENGTH_LONG).show();
            });
        }
    }
}
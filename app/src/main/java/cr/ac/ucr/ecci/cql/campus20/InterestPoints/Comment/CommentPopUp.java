package cr.ac.ucr.ecci.cql.campus20.InterestPoints.Comment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cr.ac.ucr.ecci.cql.campus20.BuildConfig;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Comment;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.FirebaseDB;
import cr.ac.ucr.ecci.cql.campus20.InterestPoints.IPModel.Place;
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
    private Activity activity;
    private RatingBar rt;
    private EditText editComment;
    private Button getRating;
    private Place place;
    private Uri imgUri;
    private StorageReference mStorageRef;
    private ImageButton sortRating;
    private boolean auxSorting;
    private boolean isPhotoLoaded;
    private String cameraFilePath;

    private static int CAMERA_REQUEST_CODE = 21;
    private static int CAMERA_PERMISSION_REQUEST = 22;
    private static int STORAGE_PERMISSION_REQUEST = 23;

    /**
     * Creates the necessary things to show up the popup.
     * @param view View where the popup is going to be opened.
     * @param activity The activity from where the popup opening was triggered.
     * @param place Place object containing its data stored in Firebase.
     */
    @SuppressLint("ClickableViewAccessibility")
    public CommentPopUp(final View view, Activity activity, Place place) {
        db = new FirebaseDB();
        mStorageRef = FirebaseStorage.getInstance().getReference(FirebaseDB.CLOUDSTORE_PREFIX);
        this.place = place;
        this.activity = activity;

        /*Popup*/
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.activity_comment_pop_up, null);
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        this.view = popupView;
        Comentarios = place.comments != null? place.comments : new ArrayList<>();
        tmp = new ArrayList<>();

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
        setPhotoListener();
       // setupLikesnDislikes

        //Para ordenar por rating
        auxSorting = true; //true para ascendente, false para descendente
        sortRating = this.view.findViewById(R.id.sortRating);
        sortRating.setOnClickListener(v -> {
            mListAdapter.orderByRating(auxSorting);
            auxSorting = !auxSorting;
            if(auxSorting){
                sortRating.setBackground(view.getContext().getResources().getDrawable(R.drawable.sort_rating_asc));
            }else{
                sortRating.setBackground(view.getContext().getResources().getDrawable(R.drawable.sort_rating_des));
            }
        });

        final PopupWindow popComments = new PopupWindow(popupView, width, height, true);
        popComments.setAnimationStyle(R.style.popup_window_animation);
        popComments.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        //Para salir tocar afuera
        popupView.setOnTouchListener((v, event) -> {
            //Close the window when clicked
            popComments.dismiss();
            ref.removeEventListener(listener);
            return true;
        });
        /*POPUP*/

    }

    /*
     * MPS4 - 02 Foto en el comentario
     * Participantes: D: Sebastián Cruz, N: Luis Carvajal
     */
    /**
     * Listener for Upload photo button.
     * OnClick calls selectImage() method.
     * */
    private void setPhotoListener(){
        Button upload = view.findViewById(R.id.foto);
        upload.setOnClickListener(v -> selectImage());
    }

    /**
     * Launches a dialog to pick the image from camera or gallery, or cancel.
     * */
    private void selectImage() {
        final CharSequence[] options = { "Tomar foto", "Seleccionar imagen","Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("Subir una foto");

        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Tomar foto")) {
                takePicture();

            } else if (options[item].equals("Seleccionar imagen")) {
                pickFromGallery();

            } else if (options[item].equals("Cancelar")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * Picks an image from gallery.
     * */
    private void pickFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        int GALLERY_REQUEST_CODE = 20;
        activity.startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    /**
     * Takes a picture using the camera. Asks for permissions if needed and then opens the camera.
     * The picture taken is sent to BaseCommentPopUp, who returns it here because this class is not
     * an activity.
     * */
    public void takePicture(){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
        } else if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST);
        }else {
            try {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
                activity.startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }catch(IOException ex){
                ex.printStackTrace();
                Toast.makeText(view.getContext(), "Ha ocurrido un error.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Saves the camera taken picture to external storage.
     * */
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
        cameraFilePath = "file://" + image.getAbsolutePath();
        return image;
    }

    /**
     * Sets the selected picture Uri.
     * @param uri Uniform resource identifier for the picture.
     * */
    public void setImg(Uri uri){
        imgUri = uri;
        isPhotoLoaded = true;
    }

    /**
     * Notifies this class from BaseCommentPopUp when the photo has been taken.
     * */
    public void notifyPhotoTaken(){
        isPhotoLoaded = true;
    }

    /*
     * MPS4 - 02 Foto en el comentario
     * Participantes: D: Sebastián Cruz, N: Luis Carvajal
     */
    /**
     * Uploads the selected picture to Firebase cloud storage.
     * @param filename Photo filename.
     * */
    private void uploadPhoto(String filename){
        StorageReference photoRef = mStorageRef.child(filename);
        /*File compressed = null;
        try {
            compressed = new Compressor(view.getContext()).compressToFile(new File(imgUri.toString()));
        }catch(IOException ex){
            ex.printStackTrace();
        }*/

        photoRef.putFile(imgUri)
            .addOnSuccessListener(taskSnapshot -> Toast.makeText(view.getContext(), "El comentario ha sido enviado.", Toast.LENGTH_LONG).show())
            .addOnFailureListener(exception -> Toast.makeText(view.getContext(), "No se pudo subir la imagen.", Toast.LENGTH_LONG).show());
    }

    /*
     * MPS4 - 02 Foto en el comentario
     * Participantes: D: Sebastián Cruz, N: Luis Carvajal
     */
    /**
     * Gets the file extension for a given URI.
     * @param uri File uri for the file whose extension is needed.
     * @return String containing the file extension.
     * */
    private String getExtension(Uri uri){
        if(uri != null) {
            ContentResolver cr = view.getContext().getContentResolver();
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getExtensionFromMimeType(cr.getType(uri));
        }
        return "";
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

    @Override
    public void onClick(Comment comment){
        Intent childActivity = new Intent(view.getContext(), CommentDetail.class);
        childActivity.putExtra("comment", comment);
        ((Activity)view.getContext()).startActivity(childActivity);
    }

    private void setupCommentRating() {
        editComment = view.findViewById(R.id.comentario);
        rt = view.findViewById(R.id.ratingBar);
        getRating = view.findViewById(R.id.enviar_c_r);

        getRating.setOnClickListener(v -> {
            /*Construcción del comentario*/
            Comment comment = new Comment();

            //Si no hay comentarios agrega uno con ID 0
            if (Comentarios.isEmpty()){
                comment.setId(0);
            }else { //Si si hay agreguea uno más
                comment.setId(Comentarios.get(Comentarios.size() - 1).getId() + 1);
            }
            //inserta en firebase
            if(isPhotoLoaded){
                String filename = getExtension(imgUri) + System.currentTimeMillis();
                uploadPhoto(filename);
                comment.setPhotoPath(filename);
            }
            comment.setId_place_fk(place.getId());
            comment.setType(Place.TYPE_SCHOOL);
            comment.setDescription(editComment.getText().toString());
            comment.setDate(UtilDates.DateToString(Calendar.getInstance().getTime()));
            float rate = rt.getRating();
            comment.setRating(rate); // Repensar

            ref.child(Integer.toString(comment.getId())).setValue(comment)
                    .addOnSuccessListener(aVoid -> Toast.makeText(view.getContext(), "El comentario ha sido enviado.", Toast.LENGTH_LONG).show())
                    .addOnFailureListener(e -> Toast.makeText(view.getContext(), "No se pudo enviar el comentario..", Toast.LENGTH_LONG).show());
            clearComment();
        });

        editComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().trim().length()==0){
                    getRating.setEnabled(false);
                } else {
                    getRating.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getRating.setEnabled(false);

    }

    /**
     * Clears the editText comment field so that the user realizes the comment was sent.
     * */
    private void clearComment(){
        imgUri = null;
        editComment = view.findViewById(R.id.comentario);
        editComment.setText("");
        isPhotoLoaded = false;
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
                if(Comentarios.size() > 0)
                    mRecyclerView.smoothScrollToPosition(Comentarios.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "No se pudo cargar la lista.", Toast.LENGTH_LONG).show();
            }
        };

        ref = db.getReference(place.getType()).child(Integer.toString(place.getId())).child("comments");
        ref.addValueEventListener(listener);
    }

    public String getCameraFilePath(){
        return cameraFilePath;
    }
}
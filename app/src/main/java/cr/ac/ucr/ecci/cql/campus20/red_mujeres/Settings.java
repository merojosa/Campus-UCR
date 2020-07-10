package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cr.ac.ucr.ecci.cql.campus20.FirebaseListener;
import cr.ac.ucr.ecci.cql.campus20.R;

public class Settings extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        private FirebaseDatabase mDatabase;
        private MenuRedMujeres m = new MenuRedMujeres();
        private FireBaseRedMujeres bd = new FireBaseRedMujeres();


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            String userID = m.getCurrentUserID();
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference ref = mDatabase.getReference("usuarios_red_mujeres");


            EditTextPreference contactoNom = findPreference("contactoNom");
            if (contactoNom != null) {
                contactoNom.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
                    @Override
                    public CharSequence provideSummary(EditTextPreference preference) {
                        verificarContacto(userID);
                        String text = preference.getText();
                        if (TextUtils.isEmpty(text)) {
                            //contactoNom.setText("");
                            return "No establecido";
                        }
                        ref.child(userID).child("ContactoConfianza").child("Nombre").setValue(text);

                        return text;
                    }
                });

                contactoNom.setDialogTitle("Establecer Nombre");
            }

            EditTextPreference contactoNum = findPreference("contactoNum");
            if (contactoNum != null) {
                contactoNum.setOnBindEditTextListener(new EditTextPreference.OnBindEditTextListener() {
                            @Override
                            public void onBindEditText(@NonNull EditText editText) {
                                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                            }
                });

                contactoNum.setSummaryProvider(new Preference.SummaryProvider<EditTextPreference>() {
                    @Override
                    public CharSequence provideSummary(EditTextPreference preference) {
                        verificarContacto(userID);
                        String text = preference.getText();
                        if (TextUtils.isEmpty(text)) {
                            //contactoNum.setText("");
                            return "No establecido";
                        }
                        ref.child(userID).child("ContactoConfianza").child("Numero").setValue(text);
                        return text;
                    }
                });
                contactoNum.setDialogTitle("Establecer Número");
            }

        }

        public void verificarContacto(String currentUser) {
            DatabaseReference root = mDatabase.getReference();

            bd.autCallback(root, new FirebaseListener() {
                @Override
                public void exito(DataSnapshot dataSnapshot) {
                    String nombre =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Nombre").getValue();
                    String numero =  (String) dataSnapshot.child("usuarios_red_mujeres").child(currentUser).child("ContactoConfianza").child("Numero").getValue();
                    EditTextPreference contactoNom = findPreference("contactoNom");
                    EditTextPreference contactoNum = findPreference("contactoNum");
                    contactoNom.setText(nombre);
                    contactoNum.setText(numero);



                }

                @Override
                public void fallo(DatabaseError databaseError) {}
            });

        }


    }
}
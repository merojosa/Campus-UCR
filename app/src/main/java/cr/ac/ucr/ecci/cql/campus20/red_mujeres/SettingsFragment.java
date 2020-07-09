package cr.ac.ucr.ecci.cql.campus20.red_mujeres;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import cr.ac.ucr.ecci.cql.campus20.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}
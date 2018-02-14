package mydatabase.android.a13zulu.com.mydatabase.settings_screen;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import mydatabase.android.a13zulu.com.mydatabase.AppDialog;
import mydatabase.android.a13zulu.com.mydatabase.R;



public class SettingsFragment extends PreferenceFragment{
    private static final String TAG = "SettingsFragment";
    public static final int DIALOG_CONFIRM_DELETE = 1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);
        bindIntPreferenceValueToSummary(findPreference("pref_out_of_stock_limit"));
        //bindStringPreferenceValueToSummary(findPreference("pref_sync_frequency"));

        Preference eraseDataButton = findPreference(getString(R.string.pref_erase_data_key));
        eraseDataButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Log.d(TAG, "onPreferenceClick: called");
                //TODO display confirmation dialog fragment, erase data if yes is pressed
                AppDialog dialog = new AppDialog();
                Bundle args = new Bundle();
                args.putInt(AppDialog.DIALOG_ID, DIALOG_CONFIRM_DELETE);
                args.putString(AppDialog.DIALOG_MESSAGE, "All records will be deleted permanently.");
                args.putInt(AppDialog.DIALOG_POSITIVE, R.string.dialog_ok);
                args.putInt(AppDialog.DIALOG_NEGATIVE, R.string.dialog_cancel);

                dialog.setArguments(args);
                dialog.show(getFragmentManager(), null);

                return true;
            }
        });
    }

    private static Preference.OnPreferenceChangeListener bindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
                // For list preferences, look up the correct display value in
                // the preference's 'entries' list.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Set the summary to reflect the new value.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
            }else{
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    private static void bindIntPreferenceValueToSummary(Preference preference){
        // set the listener to watch for value changes
        preference.setOnPreferenceChangeListener(bindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with preference's current value
        bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getInt(preference.getKey(), 0));
    }


//    private static void bindStringPreferenceValueToSummary(Preference preference){
//        // set the listener to watch for value changes
//        preference.setOnPreferenceChangeListener(bindPreferenceSummaryToValueListener);
//
//        // Trigger the listener immediately with preference's current value
//        bindPreferenceSummaryToValueListener.onPreferenceChange(preference,
//                PreferenceManager
//                        .getDefaultSharedPreferences(preference.getContext())
//                        .getString(preference.getKey(), ""));
//    }

}

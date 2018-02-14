package mydatabase.android.a13zulu.com.mydatabase.settings_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import mydatabase.android.a13zulu.com.mydatabase.AppDialog;


public class SettingsActivity extends AppCompatActivity implements AppDialog.DialogEvents{
    private static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


    @Override
    public void onPositiveDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onPositiveDialogResult: called");
    }

    @Override
    public void onNegativeDialogResult(int dialogId, Bundle args) {
        Log.d(TAG, "onNegativeDialogResult: called");
    }

    @Override
    public void onDialogCancelled(int dialogId) {
        Log.d(TAG, "onDialogCancelled: called");
    }
}

package mydatabase.android.a13zulu.com.mydatabase.settings_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;



public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}

package mydatabase.android.a13zulu.com.mydatabase.welcome_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;

/**
 * Displays welcome screen.
 */
public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        WelcomeFragment welcomeFragment = (WelcomeFragment) getSupportFragmentManager().findFragmentById(R.id.welcome_content_frame);
        if (welcomeFragment == null) {
            welcomeFragment = WelcomeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), welcomeFragment, R.id.welcome_content_frame);
        }

        WelcomePresenter presenter = new WelcomePresenter(welcomeFragment,
                ActivityUtils.getOutOfStockSharedPref(this),
                Injection.provideItemsRepository(getApplicationContext()));
    }
}

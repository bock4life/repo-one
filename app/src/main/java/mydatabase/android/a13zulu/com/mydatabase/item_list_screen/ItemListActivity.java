package mydatabase.android.a13zulu.com.mydatabase.item_list_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;

public class ItemListActivity extends AppCompatActivity {
    private static final String TAG = "ItemListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: called");


//        ItemListFragment itemListFragment = ItemListFragment.newInstance();
//        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), itemListFragment, R.id.content_frame);

        ItemListFragment itemListFragment =
                (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (itemListFragment == null) {
            // create the fragment
            itemListFragment = ItemListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), itemListFragment, R.id.content_frame);

        }
        // create Presenter, it is passed to the View in its constructor
        ItemListPresenter presenter = new ItemListPresenter(Injection.provideItemsRepository(getApplicationContext()), itemListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

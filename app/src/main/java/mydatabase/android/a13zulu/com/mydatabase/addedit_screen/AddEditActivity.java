package mydatabase.android.a13zulu.com.mydatabase.addedit_screen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;

/**
 * Displays an add or edit item screen.
 */
public class AddEditActivity extends AppCompatActivity {
    private static final String TAG = "AddEditActivity";

    public static final int REQUEST_ADD_ITEM = 1;

    public static final String SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY";

    public static final String EXTRA_ITEM_ID = "EXTRA_ITEM_ID";

    private AddEditPresenter mAddEditPresenter;

    private AddEditFragment mAddEditFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

         mAddEditFragment = (AddEditFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        String itemId = getIntent().getStringExtra(AddEditFragment.ARGUMENT_EDIT_ITEM_ID);

        if (mAddEditFragment == null) {
            mAddEditFragment = AddEditFragment.newInstance();
            if (getIntent().hasExtra(AddEditFragment.ARGUMENT_EDIT_ITEM_ID)) {
                actionBar.setTitle("Edit item");
                Bundle bundle = new Bundle();
                bundle.putString(AddEditFragment.ARGUMENT_EDIT_ITEM_ID, itemId);
                mAddEditFragment.setArguments(bundle);
            } else {
                actionBar.setTitle("New Item");
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mAddEditFragment, R.id.contentFrame);
        }

        boolean shouldLoadDataFromRepo = true;

        // prevent presenter from loading data
        // from the repository if this is a config change

        if (savedInstanceState != null) {
            shouldLoadDataFromRepo = savedInstanceState.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();

        mAddEditPresenter = new AddEditPresenter(itemId,
                Injection.provideItemsRepository(getApplicationContext()),
                mAddEditFragment, shouldLoadDataFromRepo, fragmentManager);

        mAddEditFragment.setPresenter(mAddEditPresenter);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: called");
        super.onBackPressed();
    }
}

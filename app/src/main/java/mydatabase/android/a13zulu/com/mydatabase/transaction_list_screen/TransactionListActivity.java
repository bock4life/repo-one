package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;


public class TransactionListActivity extends AppCompatActivity {
    private static final String TAG = "TransactionListActivity";

    public static final String SHOW_OUT_OF_STOCK_ITEMS = "SHOW_OUT_OF_STOCK_ITEMS";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TransactionListFragment transactionListFragment =
                (TransactionListFragment) getSupportFragmentManager().findFragmentById(R.id.transaction_list_activity_content_frame);

        // When user clicks on "Out Of stock items" true value will be passed as extra.
        boolean showOutOfStock = getIntent().getBooleanExtra(SHOW_OUT_OF_STOCK_ITEMS, false);

        if (transactionListFragment == null) {
            transactionListFragment = TransactionListFragment.newInstance();

            // passing true value to the fragment so it can modify UI as required.
            if(showOutOfStock){
                Log.d(TAG, "onCreate: show out of stock");
                Bundle args = new Bundle();
                args.putBoolean(SHOW_OUT_OF_STOCK_ITEMS, true);
                transactionListFragment.setArguments(args);
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), transactionListFragment, R.id.transaction_list_activity_content_frame);
        }

        // Instance of Presenter is passed to the View in constructor.
        TransactionListPresenter presenter = new TransactionListPresenter(Injection.provideTransactionRepository(getApplicationContext()),
                Injection.provideItemsRepository(getApplicationContext()),
                transactionListFragment, showOutOfStock);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

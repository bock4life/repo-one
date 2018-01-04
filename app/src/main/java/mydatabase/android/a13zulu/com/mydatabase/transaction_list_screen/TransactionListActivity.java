package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;


public class TransactionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TransactionListFragment transactionListFragment =
                (TransactionListFragment) getSupportFragmentManager().findFragmentById(R.id.transaction_list_activity_content_frame);

        if(transactionListFragment == null){
            transactionListFragment = TransactionListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), transactionListFragment, R.id.transaction_list_activity_content_frame);
        }

        TransactionListPresenter presenter = new TransactionListPresenter(Injection.provideTransactionRepository(getApplicationContext()), transactionListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

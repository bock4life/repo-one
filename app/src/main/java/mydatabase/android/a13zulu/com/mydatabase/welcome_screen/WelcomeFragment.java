package mydatabase.android.a13zulu.com.mydatabase.welcome_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.storage_list_screen.StorageListActivity;
import mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen.TransactionListActivity;

/**
 * Fragment containing UI elements of Welcome Screen.
 */

public class WelcomeFragment extends Fragment implements WelcomeContract.View{
    private static final String TAG = "WelcomeFragment";

    private WelcomeContract.UserActionListener mPresenter;

    private ImageView mStorageRoomsImageView;
    private TextView mStorageRoomsTextView;

    private ImageView mTransactionsImageView;
    private TextView mTransactionsTextView;

    private ImageView mOutOfStockImageView;
    private TextView mOutOfStockTextView;
    private TextView mOutOfStockNumberTextView;

    private ImageView mSettingsImageView;
    private TextView mSettingsTextView;


    public WelcomeFragment(){

    }

    public static WelcomeFragment newInstance(){
        return new WelcomeFragment();
    }

    @Override
    public void setPresenter(WelcomeContract.UserActionListener presenter) {
        mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        mStorageRoomsImageView = rootView.findViewById(R.id.frag_welcome_storagerooms_iv);
        mStorageRoomsTextView = rootView.findViewById(R.id.frag_welcome_storagerooms_tv);
        mTransactionsImageView = rootView.findViewById(R.id.frag_welcome_transactions_iv);
        mTransactionsTextView = rootView.findViewById(R.id.frag_welcome_transactions_tv);
        mOutOfStockImageView = rootView.findViewById(R.id.frag_welcome_outofstock_iv);
        mOutOfStockTextView = rootView.findViewById(R.id.frag_welcome_outofstock_tv);
        mOutOfStockNumberTextView = rootView.findViewById(R.id.frag_welcome_outofstock_number_tv);
        mSettingsImageView = rootView.findViewById(R.id.frag_welcome_settings_iv);
        mSettingsTextView = rootView.findViewById(R.id.frag_welcome_settings_tv);


        //TODO create FrameLayouts for all View Group and replace the individual listeners
        View.OnClickListener storageClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mPresenter.storageListClicked();
            }
        };
        mStorageRoomsImageView.setOnClickListener(storageClick);
        mStorageRoomsTextView.setOnClickListener(storageClick);

        View.OnClickListener transactionClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.transactionsClicked();
            }
        };
        mTransactionsImageView.setOnClickListener(transactionClick);
        mTransactionsTextView.setOnClickListener(transactionClick);

        View.OnClickListener outOfStockClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.outOfStockClicked();
            }
        };
        mOutOfStockImageView.setOnClickListener(outOfStockClick);
        mOutOfStockTextView.setOnClickListener(outOfStockClick);
        mOutOfStockNumberTextView.setOnClickListener(outOfStockClick);



        View.OnClickListener settingsClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.settingsClicked();
            }
        };
        mSettingsImageView.setOnClickListener(settingsClick);
        mSettingsTextView.setOnClickListener(settingsClick);




        return rootView;
    }

    @Override
    public void showStorageList() {
        Log.d(TAG, "showStorageList: called");
        Intent intent = new Intent(getContext(), StorageListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTransactions() {
        Log.d(TAG, "showTransactions: called");
        Intent intent = new Intent(getContext(), TransactionListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showOutOfStockItems() {
        Log.d(TAG, "showOutOfStockItems: called");
    }

    @Override
    public void showSettings() {
        Log.d(TAG, "showSettings: called");
    }
}

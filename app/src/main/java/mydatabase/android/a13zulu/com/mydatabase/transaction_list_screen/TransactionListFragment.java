package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.item_list_screen.ItemListActivity;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditFragment;

import static mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditFragment.ARGUMENT_EDIT_ITEM_ID;

/**
 * This UI show the list of transactions or items that are low in stock.
 */

public class TransactionListFragment extends Fragment implements TransactionListContract.View {
    private static final String TAG = "TransactionListFragment";
    private static int mOutOfStockLimit;

    private TransactionListRecyclerAdapter mTransactionAdapter;
    private OutOfStockItemAdapter mItemAdapter;
    private TransactionListContract.UserActionListener mPresenter;

    // TODO replace with one click listener interface

    TransactionListRecyclerAdapter.OnTransactionClickListener mClickListener = new TransactionListRecyclerAdapter.OnTransactionClickListener() {
        @Override
        public void onStorageClick(long storageId) {
            mPresenter.openStorageRoomDetails(storageId);
        }

        @Override
        public void onItemClick(long storageRoomId, long itemId) {
            mPresenter.openItemDetails(storageRoomId, itemId);
        }
    };

    OutOfStockItemAdapter.OnItemClickListener mItemClickListener = new OutOfStockItemAdapter.OnItemClickListener() {
        @Override
        public void onStorageClick(long storageId) {
            mPresenter.openStorageRoomDetails(storageId);
        }

        @Override
        public void onItemClick(long storageId, long itemId) {
            mPresenter.openItemDetails(storageId, itemId);
        }
    };


    public TransactionListFragment() {

    }

    public static TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            Log.d(TAG, "onCreate: Item Adapter created");
            mItemAdapter = new OutOfStockItemAdapter(new ArrayList<Item>(0), mItemClickListener);
            mOutOfStockLimit = ActivityUtils.getOutOfStockSharedPref(getContext());
        } else {
            Log.d(TAG, "onCreate: Transaction Adapter created");
            mTransactionAdapter = new TransactionListRecyclerAdapter(new ArrayList<ItemTransaction>(0), mClickListener);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.frag_transaction_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if(mItemAdapter!=null){
            Log.d(TAG, "onCreateView: Item Adapter attached");
            recyclerView.setAdapter(mItemAdapter);
        }else{
            Log.d(TAG, "onCreateView: Transaction Adapter attached");
            recyclerView.setAdapter(mTransactionAdapter);
        }

        mPresenter.loadList(mOutOfStockLimit);
        return rootView;
    }

    @Override
    public void setPresenter(TransactionListContract.UserActionListener presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showTransactionList(List<ItemTransaction> itemTransactions) {
        mTransactionAdapter.replaceData(itemTransactions);
    }

    @Override
    public void showOutOfStockItems(List<Item> lowStockItemTransactions) {
        mItemAdapter.replaceData(lowStockItemTransactions);
    }

    @Override
    public void showNoTransactions() {
        //TODO display massage
    }

    @Override
    public void showNoOutOfStockItems() {

    }

    @Override
    public void showStorageRoomUI(long storageRoomId) {
        Intent intent = new Intent(getContext(), ItemListActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, storageRoomId);
        startActivity(intent);
    }

    @Override
    public void showItemDetailsUI(long storageRoomId, long itemId) {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, storageRoomId);
        intent.putExtra(ARGUMENT_EDIT_ITEM_ID, String.valueOf(itemId));
        startActivityForResult(intent, AddEditActivity.REQUEST_EDIT_ITEM);
    }

}

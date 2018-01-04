package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

/**
 * UI for List of transactions
 */

public class TransactionListFragment extends Fragment implements TransactionListContract.View{
    private static final String TAG = "TransactionListFragment";

    private TransactionListRecyclerAdapter mAdapter;
    private TransactionListContract.UserActionListener mPresenter;

    TransactionListRecyclerAdapter.OnTransactionClickListener mClickListener = new TransactionListRecyclerAdapter.OnTransactionClickListener() {
        @Override
        public void onStorageClick(long storageId) {
            //TODO finish
        }

        @Override
        public void onItemClick(long itemId) {
            //TODO finish
        }
    };


    public TransactionListFragment(){

    }

    public static TransactionListFragment newInstance(){
        return new TransactionListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new TransactionListRecyclerAdapter(new ArrayList<ItemTransaction>(0), mClickListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.frag_transaction_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mPresenter.loadTransactionList();
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
        mAdapter.replaceData(itemTransactions);
    }

    @Override
    public void showNoTransactions() {

    }

    @Override
    public void showStorageRoomUI(long storageRoomId) {

    }

    @Override
    public void showItemDetailsUI(long itemId) {

    }
}

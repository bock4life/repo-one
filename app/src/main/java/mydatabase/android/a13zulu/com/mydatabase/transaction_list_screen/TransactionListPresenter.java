package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsRepository;

/**
 * Listens to user actions from the UI {@link TransactionListFragment},
 * retrieves the data and updates UI as required.
 */

public class TransactionListPresenter implements TransactionListContract.UserActionListener{
    private static final String TAG = "TransactionsPresenter";

    private final TransactionsRepository mTransactionsRepository;
    private final TransactionListContract.View mView;


    public TransactionListPresenter(@Nonnull TransactionsRepository transactionsRepository,
                                    @Nonnull TransactionListContract.View view){
        mTransactionsRepository = transactionsRepository;
        mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadTransactionList() {
        loadTransactions();
    }

    private void loadTransactions(){
        mTransactionsRepository.getTransactions(new TransactionsDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<ItemTransaction> transactions) {
                processTransactionList(transactions);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processTransactionList(List<ItemTransaction> itemTransactions){
        if(itemTransactions.isEmpty()){
            mView.showNoTransactions();
        }else{
            mView.showTransactionList(itemTransactions);
        }
    }

    @Override
    public void openStorageRoomDetails(long storageId) {
        mView.showStorageRoomUI(storageId);
    }

    @Override
    public void openItemDetails(long itemId) {
        mView.showItemDetailsUI(itemId);
    }
}

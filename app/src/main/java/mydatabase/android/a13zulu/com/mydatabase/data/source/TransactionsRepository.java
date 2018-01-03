package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

/**
 * Concrete implementation for operations with Item Transactions
 */

public class TransactionsRepository implements TransactionsDataSource{
    private static final String TAG = "TransactionsRepository";

    private static TransactionsRepository INSTANCE = null;
    private final TransactionsDataSource mTransactionsDataSource;

    private TransactionsRepository(@NonNull TransactionsDataSource transactionsDataSource) {
        mTransactionsDataSource = transactionsDataSource;
    }

    public static TransactionsRepository getInstance(TransactionsDataSource transactionsDataSource){
        if(INSTANCE == null){
            INSTANCE = new TransactionsRepository(transactionsDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getTransactions(long itemId, @NonNull final LoadTransactionsCallback callback) {
        mTransactionsDataSource.getTransactions(itemId, new LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<ItemTransaction> transactions) {
                callback.onTransactionsLoaded(transactions);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getTransaction(@NonNull long transactionId, @NonNull GetTransactionCallback callback) {

    }

    @Override
    public void saveTransaction(@NonNull long itemId, @NonNull int transactionAmount) {
        Log.d(TAG, "saveTransaction: called");
        mTransactionsDataSource.saveTransaction(itemId, transactionAmount);
    }


    @Override
    public void deleteTransaction(@NonNull long transactionId) {

    }
}

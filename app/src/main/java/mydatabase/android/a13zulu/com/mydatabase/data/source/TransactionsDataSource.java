package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

/**
 * Main entry point for accessing Transactions data.
 */

public interface TransactionsDataSource {

    interface LoadTransactionsCallback{
        void onTransactionsLoaded(List<ItemTransaction> transactions);
        void onDataNotAvailable();
    }

    interface GetTransactionCallback{
        void onTransactionLoaded(ItemTransaction transaction);
        void onDataNotAvailable();
    }

    void getTransactions(@Nullable long itemId, @NonNull LoadTransactionsCallback callback);
    void getTransaction(@NonNull long transactionId, @NonNull GetTransactionCallback callback);
    //void saveTransaction(@NonNull ItemTransaction transaction);
    void saveTransaction(@NonNull long itemId, @NonNull int transactionAmount);
    void refreshTransaction();
    void deleteTransaction(@NonNull long transactionId);
}

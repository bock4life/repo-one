package mydatabase.android.a13zulu.com.mydatabase.transaction_screen;

import android.support.annotation.NonNull;
import android.util.Log;

import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Listens for User input from {@link TransactionFragment} and updates UI as needed
 */

public class TransactionPresenter implements TransactionContract.UserActionListener {
    private static final String TAG = "TransactionPresenter";

    private int transactionQuantity;

    @NonNull
    private final long mItemId;

    @NonNull
    private final TransactionContract.View mTransactionView;

    @NonNull
    private final TransactionsDataSource mTransactionsRepository;

    /**
     * Create a Presenter for the TransactionFragment.
     *
     * @param fragment               the transactions view
     * @param transactionsRepository a repository of data for transaction
     */

    public TransactionPresenter(@NonNull long itemId,
                                @NonNull TransactionContract.View fragment,
                                @NonNull TransactionsDataSource transactionsRepository) {
        mItemId = checkNotNull(itemId);
        mTransactionView = checkNotNull(fragment);
        mTransactionsRepository = checkNotNull(transactionsRepository);
    }

    @Override
    public void start() {

    }

    @Override
    public void performTransaction(long itemId, int quantity) {
        Log.d(TAG, "performTransaction: called");
        mTransactionsRepository.saveTransaction(itemId, quantity);
        mTransactionView.closeTransactionFragment();
    }
}

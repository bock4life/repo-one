package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsRepository;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsRepository;

/**
 * Listens to user actions from the UI {@link TransactionListFragment},
 * retrieves the data and updates UI as required.
 */

public class TransactionListPresenter implements TransactionListContract.UserActionListener{
    private static final String TAG = "TransactionsPresenter";

    private final TransactionsRepository mTransactionsRepository;
    private final ItemsRepository mItemsRepository;
    private final TransactionListContract.View mView;

    private boolean mShowOutOfStock = false;


    public TransactionListPresenter(@Nonnull TransactionsRepository transactionsRepository,
                                    @Nonnull ItemsRepository itemsRepository,
                                    @Nonnull TransactionListContract.View view,
                                    boolean showOutOfStock){
        mTransactionsRepository = transactionsRepository;
        mItemsRepository = itemsRepository;
        mView = view;
        mShowOutOfStock = showOutOfStock;

        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadList(int outOfStockLimit) {
        if(mShowOutOfStock){
            loadOutOfStockItems(outOfStockLimit);
        }else{
            loadTransactions();
        }
    }


    private void loadOutOfStockItems(int limit){
        mItemsRepository.getItems(limit, new ItemsDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                processItemList(items);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    private void processItemList(List<Item> items){
        if(items.isEmpty()){
            mView.showNoOutOfStockItems();
        }else{
            mView.showOutOfStockItems(items);
        }
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
    public void openItemDetails(long storageId, long itemId) {
        mView.showItemDetailsUI(storageId, itemId);
    }
}

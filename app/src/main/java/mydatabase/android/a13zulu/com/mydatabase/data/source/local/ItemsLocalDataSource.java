package mydatabase.android.a13zulu.com.mydatabase.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import mydatabase.android.a13zulu.com.mydatabase.data.DaoSession;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.data.MyObjectBox;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * ObjectBox Database implementation
 */

public class ItemsLocalDataSource implements ItemsDataSource, TransactionsDataSource{
    private static final String TAG = "ItemsLocalDataSource";
    private static ItemsLocalDataSource INSTANCE;
    private BoxStore mBoxStore;
    private DaoSession mDaoSession;
    Box<Item> mItemBox;
    Box<ItemTransaction> mTransactionBox;


    private ItemsLocalDataSource(@NonNull Context context){
        checkNotNull(context);

        mBoxStore = MyObjectBox.builder().androidContext(context).build(); //TODO confirm that context is correct
        mDaoSession = new DaoSession(mBoxStore);

        mItemBox = mBoxStore.boxFor(Item.class);
        mTransactionBox = mBoxStore.boxFor(ItemTransaction.class);
    }

    public static ItemsLocalDataSource getInstance(@NonNull Context context){
        if(INSTANCE == null){
            INSTANCE = new ItemsLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Operations with Items
     */
    @Override
    public void getItems(@NonNull LoadItemsCallback callback) {
        Log.d(TAG, "getItems: called");
        List<Item> items = mItemBox.getAll();
        if(items.isEmpty()){
            callback.onDataNotAvailable();
        } else {
            callback.onItemsLoaded(items);
        }
    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull GetItemCallback callback) {
        Log.d(TAG, "getItem: called");
        callback.onItemLoaded(mItemBox.get(itemId));
    }

    @Override
    public void saveItem(@NonNull Item item) {
        Log.d(TAG, "saveItem: called");
        mItemBox.put(item);

        List<Item> itemList = mItemBox.getAll();

        for(int i = 0; i < itemList.size(); i++){
            Log.d(TAG, "Item: " + itemList.get(i).toString());
        }
    }

    @Override
    public void refreshItems() {
        Log.d(TAG, "refreshItems: called");
    }

    @Override
    public void deleteItem(@NonNull long itemId) {
//        Log.d(TAG, "deleteItem: called");
//        mItemBox.remove(itemId);//TODO check if correct
    }


    /**
     * Operations with Item Transactions
     */

    @Override
    public void getTransactions(@Nullable long itemId, @NonNull LoadTransactionsCallback callback) {

    }

    @Override
    public void getTransaction(@NonNull long transactionId, @NonNull GetTransactionCallback callback) {

    }

    @Override
    public void saveTransaction(@NonNull long itemId, @NonNull int transactionAmount) {
        Item item = mItemBox.get(itemId);
        String itemName = item.getItemName();
        Date currentDate = new Date();
        ItemTransaction transaction = new ItemTransaction(itemName, transactionAmount, currentDate);
        item.getItemTransactions().add(transaction);
        item.setItemQuantity(item.getItemQuantity() + transactionAmount);
        mItemBox.put(item);
    }

    @Override
    public void refreshTransaction() {

    }

    @Override
    public void deleteTransaction(@NonNull long transactionId) {

    }

}

package mydatabase.android.a13zulu.com.mydatabase.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import mydatabase.android.a13zulu.com.mydatabase.data.DaoSession;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.data.Item_;
import mydatabase.android.a13zulu.com.mydatabase.data.MyObjectBox;
import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.StorageRoomsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;

/**
 * ObjectBox Database implementation
 */

public class ItemsLocalDataSource implements ItemsDataSource, TransactionsDataSource, StorageRoomsDataSource {
    private static final String TAG = "ItemsLocalDataSource";
    private static ItemsLocalDataSource INSTANCE;
    private BoxStore mBoxStore;
    private DaoSession mDaoSession;
    Box<Item> mItemBox;
    Box<ItemTransaction> mTransactionBox;
    Box<StorageRoom> mStorageRoomBox;


    private ItemsLocalDataSource(@NonNull Context context) {

        mBoxStore = MyObjectBox.builder().androidContext(context).build();
        mDaoSession = new DaoSession(mBoxStore);

        mItemBox = mBoxStore.boxFor(Item.class);
        mTransactionBox = mBoxStore.boxFor(ItemTransaction.class);
        mStorageRoomBox = mBoxStore.boxFor(StorageRoom.class);
    }

    public static ItemsLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ItemsLocalDataSource(context);
        }
        return INSTANCE;
    }

    /**
     * Operations with Items
     */
    @Override
    public void getItems(long storageId, @NonNull LoadItemsCallback callback) {
        Log.d(TAG, "getItems: called");
        List<Item> items = mStorageRoomBox.get(storageId).getItems();
        if (items.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onItemsLoaded(items);
        }
    }

    @Override
    public void getItems(int limit, @Nonnull LoadItemsCallback callback) {
        //TODO test
//        List<Item> outOfStockItems = mItemBox.query().less(Item_.itemQuantity, Double.valueOf(limit)).build().find();
//        callback.onItemsLoaded(outOfStockItems);
        List<Item> outOfStockItems = mItemBox.query().less(Item_.itemQuantity, limit).build().find();
        callback.onItemsLoaded(outOfStockItems);
    }

    @Override
    public void getItem(long itemId, @NonNull GetItemCallback callback) {
        Log.d(TAG, "getItem: called");
        callback.onItemLoaded(mItemBox.get(itemId));
    }

    @Override
    public void saveItem(long storageId, @NonNull Item item) {
        //TODO update with relation to StorageRoom
        Log.d(TAG, "saveItem: called");
        StorageRoom storage = mStorageRoomBox.get(storageId);
        storage.getItems().add(item);
        mStorageRoomBox.put(storage);
    }

    @Override
    public void deleteItem(long itemId) {
        //TODO finish
    }


    /**
     * Operations with Item Transactions
     */

    @Override
    public void getTransactions(long itemId, @NonNull LoadTransactionsCallback callback) {
        Log.d(TAG, "getTransactions: called");
        List<ItemTransaction> transactions = mItemBox.get(itemId).getItemTransactions();
        if (transactions.isEmpty()) {
            callback.onDataNotAvailable();
        } else {
            callback.onTransactionsLoaded(transactions);
        }
    }

    @Override
    public void getTransactions(@Nonnull LoadTransactionsCallback callback) {
        Log.d(TAG, "getTransactions: called without item Id");
        List<ItemTransaction> transactions = mTransactionBox.getAll();
        if(transactions.isEmpty()){
            callback.onDataNotAvailable();
        }else{
            callback.onTransactionsLoaded(transactions);
        }
    }

    @Override
    public void getTransaction(long transactionId, @NonNull GetTransactionCallback callback) {

    }

    @Override
    public void saveTransaction(long itemId, @NonNull int transactionAmount) {
        Item item = mItemBox.get(itemId);
        String itemName = item.getItemName();
        Date currentDate = new Date();
        ItemTransaction transaction = new ItemTransaction(itemName,
                transactionAmount,
                currentDate,
                mItemBox.get(itemId).getItemQuantity() + transactionAmount);

        item.getItemTransactions().add(transaction);
        item.setItemQuantity(item.getItemQuantity() + transactionAmount);
        mItemBox.put(item);
    }

    @Override
    public void deleteTransaction(long transactionId) {

    }

    /**
     * StorageRoom operations
     */

    @Override
    public void getStorageRooms(@Nonnull LoadStorageRoomsCallback callback) {
        callback.onStorageRoomsLoader(mStorageRoomBox.getAll());
    }

    @Override
    public void getStorageRoom(@Nonnull long storageRoomId, @Nonnull GetStorageRoomCallback callback) {
        callback.onStorageRoomLoaded(mStorageRoomBox.get(storageRoomId));
    }

    @Override
    public void saveStorageRoom(@Nonnull StorageRoom storageRoom) {
        mStorageRoomBox.put(storageRoom);
    }


    @Override
    public void deleteStorageRoom(@Nonnull long storageRoomId) {

    }
}

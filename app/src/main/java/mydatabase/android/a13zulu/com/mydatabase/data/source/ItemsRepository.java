package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.Item;

/**
 * Concrete implementation to load items from data source.
 */

public class ItemsRepository implements ItemsDataSource {
    private static final String TAG = "ItemsRepository";

    private static ItemsRepository INSTANCE = null;

    private final ItemsDataSource mItemsDataSource;

    /*
    This variable has package local visibility so it can be accessed from test.]
     */
    Map<String, Item> mCachedItems;
    /*
    Marks the cache is invalid, to force an update the next time data is requested.
    This variable has package local visibility so it can be accessed from tests.
     */

    boolean mCacheIsDirty = false;

    // Prevent direct instantiation.
    private ItemsRepository(@NonNull ItemsDataSource itemsLocalDataSource) {
        mItemsDataSource = itemsLocalDataSource;
    }

    public static ItemsRepository getInstance(ItemsDataSource itemsDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ItemsRepository(itemsDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getItems(@Nonnull long storageId, @NonNull final LoadItemsCallback callback) {
        Log.d(TAG, "getItems: called");

        mItemsDataSource.getItems(storageId, new LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                callback.onItemsLoaded(new ArrayList<>(items));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getItems(int limit, @Nonnull final LoadItemsCallback callback) {
        mItemsDataSource.getItems(limit, new LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                callback.onItemsLoaded(new ArrayList<>(items));
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void getItem(@NonNull long itemId, @NonNull final GetItemCallback callback) {
        Log.d(TAG, "getItem: called");

        mItemsDataSource.getItem(itemId, new GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                callback.onItemLoaded(item);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    @Override
    public void saveItem(@Nonnull long storageId, @NonNull Item item) {
        Log.d(TAG, "saveItem: called");
        //TODO finish
        mItemsDataSource.saveItem(storageId, item);
    }

    @Override
    public void deleteItem(@NonNull long itemId) {

    }
}

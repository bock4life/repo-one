package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.support.annotation.NonNull;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.Item;

/**
 * Main entry point for accessing Items data.
 * For simplicity, only getTasks() and getTask() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new task is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */

public interface ItemsDataSource {

    interface LoadItemsCallback{

        void onItemsLoaded(List<Item> items);

        void onDataNotAvailable();
    }

    interface GetItemCallback{

        void onItemLoaded(Item item);

        void onDataNotAvailable();
    }


    void getItems(@Nonnull long storageId, @NonNull LoadItemsCallback callback);

    /**
     *  User for querying Out of stock items.
     *  Every item that has quantity lower than limit will be added to the list.
     * @param limit number of items.
     * @param callback passing received data back.
     */
    void getItems(int limit, @Nonnull LoadItemsCallback callback);
    void getItem(@NonNull long itemId, @NonNull GetItemCallback callback);
    void saveItem(@Nonnull long storageId, @NonNull Item item);
    void deleteItem(@NonNull long itemId);


}

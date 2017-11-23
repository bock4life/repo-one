package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.support.annotation.NonNull;

import java.util.List;

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


    void getItems(@NonNull LoadItemsCallback callback);
    void getItem(@NonNull long itemId, @NonNull GetItemCallback callback);
    void saveItem(@NonNull Item item);
    void refreshItems();
    void deleteItem(@NonNull long itemId);


}

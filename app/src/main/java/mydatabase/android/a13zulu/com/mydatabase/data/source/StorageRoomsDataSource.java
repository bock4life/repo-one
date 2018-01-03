package mydatabase.android.a13zulu.com.mydatabase.data.source;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;

/**
 * Main entry point for accessing StorageRooms data.
 * For simplicity only getStorageRooms() and getStorageRoom() have callbacks.
 * Consider adding callbacks to other methods to inform the user of network/database
 * errors or successful operations.
 *
 * For example, when new StorageRoom is created, it's synchronously stored in database,
 * but usually every operation on database or network should be executed
 * on a different thread.
 */

public interface StorageRoomsDataSource {

    interface LoadStorageRoomsCallback{
        void onStorageRoomsLoader(List<StorageRoom> storageRooms);
    }

    interface GetStorageRoomCallback{
        void onStorageRoomLoaded(StorageRoom storageRoom);
    }

    void getStorageRooms(@Nonnull LoadStorageRoomsCallback callback);
    void getStorageRoom(@Nonnull long storageRoomId, @Nonnull GetStorageRoomCallback callback);
    void saveStorageRoom(@Nonnull StorageRoom storageRoom);
    void deleteStorageRoom(@Nonnull long storageRoomId);
}

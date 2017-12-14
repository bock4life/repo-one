package mydatabase.android.a13zulu.com.mydatabase.data.source;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;

/**
 * Concrete implementation to load StorageRooms from data source.
 */

public class StorageRoomsRepository implements StorageRoomsDataSource{
    private static final String TAG = "StorageRoomsRepository";

    private static StorageRoomsRepository INSTANCE = null;

    private final StorageRoomsDataSource mStorageRoomsDataSource;

    // prevent direct instantiation.
    private StorageRoomsRepository(@Nonnull StorageRoomsDataSource storageRoomsDataSource){
        mStorageRoomsDataSource = storageRoomsDataSource;
    }

    public static StorageRoomsRepository getInstance(StorageRoomsDataSource storageRoomsDataSource){
        if(INSTANCE == null){
            INSTANCE = new StorageRoomsRepository(storageRoomsDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getStorageRooms(@Nonnull final LoadStorageRoomsCallback callback) {
        Log.d(TAG, "getStorageRooms: called");
        mStorageRoomsDataSource.getStorageRooms(new LoadStorageRoomsCallback() {
            @Override
            public void onStorageRoomsLoader(List<StorageRoom> storageRooms) {
                callback.onStorageRoomsLoader(new ArrayList<>(storageRooms));
            }
        });
    }

    @Override
    public void getStorageRoom(@Nonnull long storageRoomId, @Nonnull final GetStorageRoomCallback callback) {
        Log.d(TAG, "getStorageRoom: called");
        mStorageRoomsDataSource.getStorageRoom(storageRoomId, new GetStorageRoomCallback() {
            @Override
            public void onStorageRoomLoaded(StorageRoom storageRoom) {
                callback.onStorageRoomLoaded(storageRoom);
            }
        });
    }

    @Override
    public void saveStorageRoom(@Nonnull StorageRoom storageRoom) {
        Log.d(TAG, "saveStorageRoom: called");
        mStorageRoomsDataSource.saveStorageRoom(storageRoom);
    }

    @Override
    public void refreshStorageRooms() {
        //TODO finish.
    }

    @Override
    public void deleteStorageRoom(@Nonnull long storageRoomId) {
        Log.d(TAG, "deleteStorageRoom: called");
        mStorageRoomsDataSource.deleteStorageRoom(storageRoomId);
    }
}

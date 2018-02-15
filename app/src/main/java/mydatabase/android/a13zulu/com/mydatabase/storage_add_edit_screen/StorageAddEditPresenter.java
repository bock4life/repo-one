package mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen;


import android.util.Log;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;
import mydatabase.android.a13zulu.com.mydatabase.data.source.StorageRoomsDataSource;

/**
 * Listens to user action from the UI {@link StorageAddEditFragment}, retrieves that data
 * and updates UI as required.
 */

public class StorageAddEditPresenter implements StorageAddEditContract.UserActionListener, StorageRoomsDataSource.GetStorageRoomCallback {
    private static final String TAG = "StorageAddEditPresenter";

    @Nonnull
    private final StorageRoomsDataSource mStorageRoomsRepo;

    @Nonnull
    private final StorageAddEditContract.View mStorageAddEditView;

    @Nullable
    private String mStorageId;

    private StorageRoom mStorageRoom;


    /**
     * @param storageRoomsRepo   a repository of data for storage's
     * @param storageAddEditView the storage add/edit view
     * @param storageId          ID og the storage to edit or null for new storage
     */

    public StorageAddEditPresenter(@Nonnull StorageRoomsDataSource storageRoomsRepo,
                                   @Nonnull StorageAddEditContract.View storageAddEditView,
                                   @Nullable String storageId) {
        mStorageRoomsRepo = storageRoomsRepo;
        mStorageAddEditView = storageAddEditView;
        mStorageId = storageId;
    }

    @Override
    public void start() {
        if(!isNewStorage()){
            Log.d(TAG, "start: called");
            populateStorageRoom();
        }
    }

    @Override
    public void saveStorageRoom(String storageName, String storageDescription, int color) {
        if(mStorageId == null){
            addNewStorage(storageName, storageDescription, color);
        }else{
            updateStorage(storageName, storageDescription, color);
        }
        mStorageAddEditView.showStorageList();
    }

    private void addNewStorage(String storageName, String storageDescription, int color) {
        //create StorageRoom object without ID
        mStorageRoom = new StorageRoom(storageName, storageDescription, color);
        mStorageRoomsRepo.saveStorageRoom(mStorageRoom);
    }

    private void updateStorage(String storageName,
                               String storageDescription, int color) {
        //create StorageRoom object with ID
        mStorageRoom = new StorageRoom(Long.parseLong(mStorageId), storageName, storageDescription, color);
        mStorageRoomsRepo.saveStorageRoom(mStorageRoom);
    }

    @Override
    public void getBackgroundColor(int color) {
        mStorageAddEditView.setBackground(color);
    }

    @Override
    public void populateStorageRoom() {
        Log.d(TAG, "populateStorageRoom: called");
        if(isNewStorage()){
            throw new RuntimeException("populateStorageRoom() was called but the storage is new");
        }
        mStorageRoomsRepo.getStorageRoom(Long.parseLong(mStorageId), this);
    }

    @Override
    public void onStorageRoomLoaded(StorageRoom storageRoom) {
        mStorageAddEditView.setStorageName(storageRoom.getName());
        mStorageAddEditView.setStorageDescription(storageRoom.getDescription());
        mStorageAddEditView.setBackground(storageRoom.getBackgroundColor());
        mStorageAddEditView.setTitle(storageRoom.getName());
    }

    private boolean isNewStorage(){
        return mStorageId == null;
    }
}

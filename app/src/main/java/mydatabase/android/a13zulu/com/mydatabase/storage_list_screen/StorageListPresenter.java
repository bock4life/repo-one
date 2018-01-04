package mydatabase.android.a13zulu.com.mydatabase.storage_list_screen;

import android.app.Activity;
import android.util.Log;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;
import mydatabase.android.a13zulu.com.mydatabase.data.source.StorageRoomsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.StorageRoomsRepository;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditActivity;

/**
 * Listens to user actions from the UI {@link StorageListFragment},
 * retrieves the data and updates the UI as required.
 */

public class StorageListPresenter implements StorageListContract.UserActionListener{

    private static final String TAG = "StorageListPresenter";

    private final StorageRoomsRepository mStorageRoomsRepository;
    private final StorageListContract.View mStorageListView;

    private boolean mFirstLoad = true;

    public StorageListPresenter(@Nonnull StorageRoomsRepository storageRoomsRepository,
                                @Nonnull StorageListContract.View storageListView){
        mStorageRoomsRepository = storageRoomsRepository;
        mStorageListView = storageListView;

        mStorageListView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {
        if(StorageAddEditActivity.REQUEST_ADD_STORAGE_ROOM == requestCode && Activity.RESULT_OK == resultCode){
            mStorageListView.showSuccessfullySavedMessage();
            loadStorages();
        }
    }

    @Override
    public void loadStorageRooms() {
        loadStorages();
    }

    private void loadStorages(){
        mStorageRoomsRepository.getStorageRooms(new StorageRoomsDataSource.LoadStorageRoomsCallback() {
            @Override
            public void onStorageRoomsLoader(List<StorageRoom> storageRooms) {
                processStorageRooms(storageRooms);
            }
        });
    }

    private void processStorageRooms(List<StorageRoom> storageRooms){
        if(storageRooms.isEmpty()){
            mStorageListView.showNoStorageRooms();
        }else{
            mStorageListView.showStorageRooms(storageRooms);
        }
    }

    @Override
    public void addNewStorageRoom() {
        Log.d(TAG, "addNewStorageRoom: called");
        mStorageListView.showAddStorageRoom();
    }

    @Override
    public void openStorageRoomDetails(@Nonnull StorageRoom requestedStorageRoom) {
        mStorageListView.showStorageRoomUI(requestedStorageRoom.getId());
    }
}

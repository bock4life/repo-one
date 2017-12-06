package mydatabase.android.a13zulu.com.mydatabase.storage_list_screen;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;
import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;

/**
 *  This specifies the contract between the view and the presenter
 */

public interface StorageListContract {

    // Implemented by View
    interface View extends BaseView<UserActionListener> {

        void setLoadingIndicator(boolean active);
        void showStorageRooms(List<StorageRoom> storageRooms);
        void showAddStorageRoom();
        void showStorageRoomUI(long storageRoomId);
        void showLoadingStorageRoomsError();
        void showNoStorageRooms();
        void showSuccessfullySavedMessage();
    }
    // Implemented by Presenter
    // Instantiated by View
    interface UserActionListener extends BasePresenter{
        void result(int requestCode, int resultCode); // TODO implement
        void loadStorageRooms();
        void addNewStorageRoom(); // opens blank Storage AddEdit screen
        void openStorageRoomDetails(@Nonnull StorageRoom requestedStorageRoom);// open ItemsListActivity
    }
}

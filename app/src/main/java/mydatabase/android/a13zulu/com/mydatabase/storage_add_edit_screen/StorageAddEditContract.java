package mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen;

        import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
        import mydatabase.android.a13zulu.com.mydatabase.BaseView;

/**
 * Contract between the view and presenter
 */

public interface StorageAddEditContract {

    interface View extends BaseView<UserActionListener> {
        void showStorageList(); // navigate back to StorageListActivity
        void setStorageName(String storageName);
        void setStorageDescription(String storageDescription);
        void setBackground(int color);
        void setTitle(String storageName);
    }

    interface UserActionListener extends BasePresenter {
        void saveStorageRoom(String storageName, String storageDescription, int color);
        void getBackgroundColor(int color);
        void populateStorageRoom();
    }
}

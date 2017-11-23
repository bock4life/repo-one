package mydatabase.android.a13zulu.com.mydatabase.main_screen;

import android.support.annotation.NonNull;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;

/**
 * This specifies the contract between the vew and the presenter
 */

public interface MainActivityContract {

    // Implemented by View
    interface View extends BaseView<UserActionListener> {
        void setLoadingIndicator(boolean active);
        void showItems(List<Item> items);
        void showAddItem();
        void showItemDetailsUi(String itemId);
        void showLoadingItemsError();
        void showNoItems();
        void showSuccessfullySavedMessage();
        boolean isActive();
    }

    // Implemented by Presenter
    // Instantiated by View
    interface UserActionListener extends BasePresenter {
        void result(int requestCode, int resultCode);
        void loadItems(boolean forceUpdate);
        void addNewItem(); // opens blank AddEdit screen
        void openItemDetails(@NonNull Item requestedItem); // opens AddEditScreen with Item details
    }
}

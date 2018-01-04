package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

/**
 * This specifies the contract between the view and the presenter
 */

public interface TransactionListContract {
    // implemented by View
    interface View extends BaseView<UserActionListener>{
        void setLoadingIndicator(boolean active);
        void showTransactionList(List<ItemTransaction> itemTransactions);
        void showNoTransactions();
        void showStorageRoomUI(long storageRoomId);
        void showItemDetailsUI(long itemId);
    }

    // implemented by Presenter, instantiated by View
    interface UserActionListener extends BasePresenter{
        void loadTransactionList();
        void openStorageRoomDetails(long storageRoomId);
        void openItemDetails(long itemId);
    }
}

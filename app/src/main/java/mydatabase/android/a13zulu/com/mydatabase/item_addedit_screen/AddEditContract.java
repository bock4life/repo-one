package mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

/**
 * Contract between the view and the presenter
 */

public interface AddEditContract {

    //Implemented by View
    interface View extends BaseView<UserActionListener>{
        void showEmptyItemNameError();
        void showEmptyItemDescriptionError();
        void showItemQuantityError();
        void showItemList();
        void setName(String name);
        void setDescription(String description);
        void setQuantity(int quantity);
        void showTransactionFragment(long itemId);

        // transaction list
        void enableTransactionList();
        void showTransactionList(List<ItemTransaction> transactionList);
        void showNoTransactions();
    }
    //Implemented by Presenter, instantiated in View
    interface UserActionListener extends BasePresenter{
        void saveItem(String name, String description, int quantity);
        void populateItem();
        void makeTransaction();

        // transaction list
        void loadTransactionList();
    }
}

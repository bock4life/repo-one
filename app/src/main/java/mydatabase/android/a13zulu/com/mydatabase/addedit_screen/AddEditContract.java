package mydatabase.android.a13zulu.com.mydatabase.addedit_screen;

        import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;

/**
 * Contract between the view and the presenter
 */

//// FIXME: 10/11/2017 editing an existing item add's a new one instead

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
        void enableTransactionList();
        void showTransactionFragment(long itemId);
    }
    //Implemented by Presenter, instantiated in View
    interface UserActionListener extends BasePresenter{
        void saveItem(String name, String description, int quantity);
        void populateItem();
        void makeTransaction();
        boolean isDataMissing();
    }
}

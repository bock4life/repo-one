package mydatabase.android.a13zulu.com.mydatabase.welcome_screen;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;

/**
 * Contract between the view and the presenter
 */

//TODO add functionality do display number of items that are out of stock.
public interface WelcomeContract {

    // implemented by View
    interface View extends BaseView<UserActionListener>{
        void showStorageList();
        void showTransactions();
        void showOutOfStockItems();
        void showSettings();
        void setLimit(int limit);
        void displayOutOfStockItemNumber(int outOfStockItemNumber);
    }

    // implemented by Presenter, instantiated in View
    interface UserActionListener extends BasePresenter{
        void loadOutOfStockItemNumber();
        void storageListClicked();
        void transactionsClicked();
        void outOfStockClicked();
        void settingsClicked();
    }
}

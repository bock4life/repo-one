package mydatabase.android.a13zulu.com.mydatabase.transaction_dialog;

import mydatabase.android.a13zulu.com.mydatabase.BasePresenter;
import mydatabase.android.a13zulu.com.mydatabase.BaseView;

/**
 * Contract between {@link TransactionFragment} and {@link TransactionPresenter}
 */

public interface TransactionContract {
    //Implemented by View
    interface View extends BaseView<UserActionListener>{
        void closeTransactionFragment();
    }
    //Implemented by Presenter
    interface UserActionListener extends BasePresenter{
        void performTransaction(long itemId, int quantity);
    }
}

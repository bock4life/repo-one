package mydatabase.android.a13zulu.com.mydatabase.welcome_screen;

import android.util.Log;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsRepository;

/**
 * Listens to user actions from the UI {@link WelcomeFragment}.
 */

public class WelcomePresenter implements WelcomeContract.UserActionListener{
    private static final String TAG = "WelcomePresenter";

    @Nonnull
    private final WelcomeContract.View mView;
    private int mOutOfStockItemLimit;

    private final ItemsRepository mItemsRepository;


    public WelcomePresenter(@Nonnull WelcomeContract.View welcomeFragment, int outOfStockItemLimit, ItemsRepository itemsRepository){
        mView = welcomeFragment;
        mOutOfStockItemLimit = outOfStockItemLimit;
        mItemsRepository = itemsRepository;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadOutOfStockItemNumber() {
        getOutOfStockNumber();
    }

    private void getOutOfStockNumber(){
        Log.d(TAG, "getOutOfStockNumber: called with limit: " + mOutOfStockItemLimit);
        mItemsRepository.getOutOfStockNumber(mOutOfStockItemLimit, new ItemsDataSource.GetOutOfStockItemsNumber() {
            @Override
            public void onOutOfStockNumberLoaded(int numberOfItems) {
                mView.displayOutOfStockItemNumber(numberOfItems);
                mView.setLimit(mOutOfStockItemLimit);
            }
        });
    }

    @Override
    public void storageListClicked() {
        mView.showStorageList();
    }

    @Override
    public void transactionsClicked() {
        mView.showTransactions();
    }

    @Override
    public void outOfStockClicked() {
        mView.showOutOfStockItems();
    }

    @Override
    public void settingsClicked() {
        mView.showSettings();
    }
}

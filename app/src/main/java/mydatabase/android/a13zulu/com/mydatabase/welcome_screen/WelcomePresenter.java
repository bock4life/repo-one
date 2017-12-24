package mydatabase.android.a13zulu.com.mydatabase.welcome_screen;

import javax.annotation.Nonnull;

/**
 * Listens to user actions from the UI {@link WelcomeFragment}.
 */

public class WelcomePresenter implements WelcomeContract.UserActionListener{
    private static final String TAG = "WelcomePresenter";

    @Nonnull
    private final WelcomeContract.View mView;

    public WelcomePresenter(@Nonnull WelcomeContract.View welcomeFragment){
        mView = welcomeFragment;
        mView.setPresenter(this);
    }

    @Override
    public void start() {

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

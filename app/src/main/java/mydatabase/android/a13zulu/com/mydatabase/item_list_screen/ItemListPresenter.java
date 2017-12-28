package mydatabase.android.a13zulu.com.mydatabase.item_list_screen;


import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsRepository;

import static android.support.v4.util.Preconditions.checkNotNull;


/**
 * Listens to user actions from the UI ({@link ItemListFragment}), retrieves the data and updates the
 * UI as required.
 */
public class ItemListPresenter implements ItemListContract.UserActionListener {
    private static final String TAG = "ItemListPresenter";

    private final ItemsRepository mItemsRepository;

    private final ItemListContract.View mMainView;

    private long mStorageId; // initialize in constructor

    private boolean mFirstLoad = true;



    public ItemListPresenter(@NonNull ItemsRepository itemsRepository,
                             @NonNull ItemListContract.View mainView,
                             @Nonnull long storageId) {
        Log.d(TAG, "ItemListPresenter: called");
        mItemsRepository = checkNotNull(itemsRepository, "itemsRepository cannot be null");
        mMainView = checkNotNull(mainView, "mainView cannot be null");
        mStorageId = storageId;

        mMainView.setPresenter(this);
    }

    @Override
    public void start() {
        Log.d(TAG, "start: called");
        loadItems(false);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        Log.d(TAG, "result: " + resultCode);
        // if Item was successfully added, show snackbar
        if (AddEditActivity.REQUEST_ADD_ITEM == requestCode &&
                Activity.RESULT_OK == resultCode) {
            mMainView.showSuccessfullySavedMessage();
            loadItems(true);
        }else { //if (AddEditActivity.REQUEST_EDIT_ITEM == requestCode &&
                //Activity.RESULT_OK == resultCode){ //TODO FIX
            loadItems(true);
        }
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        loadItems(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    /**
     * @param forceUpdate   Pass in true to refresh the data in the {@link mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource}
     * @param showLoadingUI Pass in true to display a loading icon in the UI
     */
    private void loadItems(boolean forceUpdate, final boolean showLoadingUI) {
        Log.d(TAG, "loadItems: Called");
        if (showLoadingUI) {
            mMainView.setLoadingIndicator(true);
        }

        mItemsRepository.getItems(mStorageId, new ItemsDataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                processItems(items);
                Log.d(TAG, "onItemsLoaded: ITEMS LOADED");
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable: NO ITEMS AVAILABLE");
                if (!mMainView.isActive()) {
                    return;
                }
                mMainView.showLoadingItemsError();
            }
        });
    }

    private void processItems(List<Item> items) {
        Log.d(TAG, "processItems: called");
        if (items.isEmpty()) {
            mMainView.showNoItems();
        } else {
            mMainView.showItems(items);
        }
    }

    @Override
    public void addNewItem() {
        mMainView.showAddItem(mStorageId); // open blank AddEdit screen
    }

    @Override
    public void openItemDetails(@NonNull Item requestedItem) {
        checkNotNull(requestedItem, "requestedItem cannot be null");
        mMainView.showItemDetailsUi(mStorageId, String.valueOf(requestedItem.getId()));// open AddEdit screen with existing item
    }


}

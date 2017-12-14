package mydatabase.android.a13zulu.com.mydatabase.addedit_screen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;

import static android.support.v4.util.Preconditions.checkNotNull;

/**
 * Listens to user action from the UI ({@link AddEditFragment}), retrieves that data
 * and updates UI as required.
 */

public class AddEditPresenter implements AddEditContract.UserActionListener, ItemsDataSource.GetItemCallback {
    private static final String TAG = "AddEditPresenter";

    @NonNull
    private final ItemsDataSource mItemsRepository;

    @NonNull
    private final AddEditContract.View mAddItemView;

    @Nonnull
    private long mStorageId;

    @Nullable
    private String mItemId;

    private boolean mIsDataMissing;

    @NonNull
    FragmentManager mFragmentManager;


    /**
     * Creates a Presenter for the add/edit view.
     *
     * @param itemId                 ID of the item to edit or null for a new item
     * @param itemsRepository        a repository of data for items
     * @param addItemView            the add/edit view
     * @param shouldLoadDataFromRepo whether data needs to be loader or not (for config changes)
     */
    public AddEditPresenter(@Nonnull long storageId, @Nullable String itemId,
                            @NonNull ItemsDataSource itemsRepository,
                            AddEditContract.View addItemView,
                            boolean shouldLoadDataFromRepo, FragmentManager fragmentManager) {

        mStorageId = storageId;
        mItemId = itemId;
        mItemsRepository = checkNotNull(itemsRepository);
        mAddItemView = checkNotNull(addItemView);
        mIsDataMissing = shouldLoadDataFromRepo;
        mFragmentManager = checkNotNull(fragmentManager);//TODO remove if not needed
    }

    @Override
    public void start() {
        if (!isNewItem() && mIsDataMissing) {
            populateItem();
        }
    }

    @Override
    public void saveItem(String name, String description, int quantity) {
        if (isNewItem()) {
            Log.d(TAG, "saveItem: NewItem called");
            createItem(name, description, quantity);
        } else {
            Log.d(TAG, "saveItem: Update called");
            updateItem(name, description, quantity);
        }
    }

    @Override
    public void populateItem() {
        Log.d(TAG, "populateItem: called");
        if (isNewItem()) {
            throw new RuntimeException("populateItem() was called but item is new");
        }
        mItemsRepository.getItem(Long.parseLong(mItemId), this);
    }

    @Override
    public void makeTransaction() {
        Log.d(TAG, "makeTransaction: called");

        mAddItemView.showTransactionFragment(Long.parseLong(mItemId));
    }

    @Override
    public boolean isDataMissing() {
        return mIsDataMissing;
    }


    @Override
    public void onItemLoaded(Item item) {
        mAddItemView.setName(item.getItemName());
        mAddItemView.setDescription(item.getItemDescription());
        mAddItemView.setQuantity(item.getItemQuantity());

        mAddItemView.enableTransactionList();
        //TODO add logic to enable item transactions and activate additional UI elements(RecyclerView)
        //TODO populate Recycler View with transactions
        mIsDataMissing = false;
    }

    @Override
    public void onDataNotAvailable() {
        // TODO remove this method if not needed
    }

    private boolean isNewItem() {
        return mItemId == null;
    }

    private void createItem(String name, String description, int quantity) {
        Item newItem = new Item(name, description, quantity);
        if (newItem.nameIsEmpty()) {
            Log.d(TAG, "Empty Name");
            mAddItemView.showEmptyItemNameError();
            return;
        }
        if(newItem.descriptionIsEmpty()){
            Log.d(TAG, "Empty Description");

            mAddItemView.showEmptyItemDescriptionError();
            return;
        }
        if(newItem.quantityIsIncorrect()){
            Log.d(TAG, "Incorrect Quantity");
            mAddItemView.showItemQuantityError();
            return;
        }
        Log.d(TAG, "createItem: called");
        mItemsRepository.saveItem(mStorageId, newItem);
        mAddItemView.showItemList();// navigate back to item list
    }

    private void updateItem(String name, String description, int quantity) {
        if (isNewItem()) {
            throw new RuntimeException("updateItem() was called but the Item is new");
        }
        mItemsRepository.saveItem(mStorageId, new Item(name, description, quantity));//// FIXME: 11/11/2017 should update not create new
        mAddItemView.showItemList(); // navigate back to item list
    }
}

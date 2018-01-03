package mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import javax.annotation.Nonnull;

import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.data.source.ItemsDataSource;
import mydatabase.android.a13zulu.com.mydatabase.data.source.TransactionsDataSource;

/**
 * Listens to user action from the UI ({@link AddEditFragment}), retrieves that data
 * and updates UI as required.
 */

public class AddEditPresenter implements AddEditContract.UserActionListener, ItemsDataSource.GetItemCallback {
    private static final String TAG = "AddEditPresenter";

    @NonNull
    private final ItemsDataSource mItemsRepository;

    @Nonnull
    private final TransactionsDataSource mTransactionsRepository;

    @NonNull
    private final AddEditContract.View mAddItemView;

    @Nonnull
    private long mStorageId;

    @Nullable
    private String mItemId;

    private boolean mIsDataMissing;

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
                            @Nonnull TransactionsDataSource transactionsRepository,
                            AddEditContract.View addItemView,
                            boolean shouldLoadDataFromRepo) {

        mStorageId = storageId;
        mItemId = itemId;
        mItemsRepository = itemsRepository;
        mTransactionsRepository = transactionsRepository;
        mAddItemView = addItemView;
        mIsDataMissing = shouldLoadDataFromRepo;
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
        if(!isNewItem()) {
            mAddItemView.showTransactionFragment(Long.parseLong(mItemId));
        }
    }



    @Override
    public void onItemLoaded(Item item) {
        mAddItemView.setName(item.getItemName());
        mAddItemView.setDescription(item.getItemDescription());
        mAddItemView.setQuantity(item.getItemQuantity());

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
        mItemsRepository.saveItem(mStorageId, new Item(Long.parseLong(mItemId), name, description, quantity));
        mAddItemView.showItemList(); // navigate back to item list
    }

    @Override
    public void loadTransactionList() {
        if(!isNewItem()) {
            loadTransactions();
        }
    }

    private void loadTransactions(){
        mTransactionsRepository.getTransactions(Long.parseLong(mItemId), new TransactionsDataSource.LoadTransactionsCallback() {
            @Override
            public void onTransactionsLoaded(List<ItemTransaction> transactions) {
                processTransactionList(transactions);
            }

            @Override
            public void onDataNotAvailable() {
            }
        });
    }

    private void processTransactionList(List<ItemTransaction> transactionList){
        if(transactionList.isEmpty()){
            mAddItemView.showNoTransactions();
        }else{
            mAddItemView.enableTransactionList();
            mAddItemView.showTransactionList(transactionList);
        }
    }
}

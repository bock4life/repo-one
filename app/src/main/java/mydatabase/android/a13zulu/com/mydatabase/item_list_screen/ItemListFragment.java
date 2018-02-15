package mydatabase.android.a13zulu.com.mydatabase.item_list_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;
import mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditFragment;

import static mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditFragment.ARGUMENT_EDIT_ITEM_ID;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemListFragment extends Fragment implements ItemListContract.View {
    private static final String TAG = "ItemListFragment";

    private ItemListRecyclerViewAdapter mItemListRecyclerViewAdapter;
    private ItemListContract.UserActionListener mPresenter;

    //TODO update item list when item was successfully edited

    public ItemListFragment() {
    }

    /**
     * Listens for clicks on items in Recycler View.
     */
    ItemListRecyclerViewAdapter.OnItemClickListener mClickListener = new ItemListRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Item item) {
            mPresenter.openItemDetails(item);
        }
    };

    public static ItemListFragment newInstance() {
        return new ItemListFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");
        setHasOptionsMenu(true);

        mItemListRecyclerViewAdapter = new ItemListRecyclerViewAdapter(new ArrayList<Item>(0), mClickListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit_storage:
                mPresenter.editStorage();
                break;
            case R.id.action_delete_storage:
                //TODO ask user to confirm deletion of storage
                break;
            case R.id.homeAsUp:
                getActivity().onBackPressed();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d(TAG, "onCreateView: called");

        RecyclerView recyclerView = rootView.findViewById(R.id.frag_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mItemListRecyclerViewAdapter);

        FloatingActionButton fab = rootView.findViewById(R.id.frag_main_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Main FAB clicked");
                mPresenter.addNewItem();
            }
        });

        mPresenter.loadItems(true);

        return rootView;
    }

    @Override
    public void setPresenter(ItemListContract.UserActionListener presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showItems(List<Item> items) {
        mItemListRecyclerViewAdapter.replaceData(items);
    }

    @Override
    public void showAddItem(long storageId) {
        Log.d(TAG, "showAddItem: called");
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, storageId);
        startActivityForResult(intent, AddEditActivity.REQUEST_ADD_ITEM);
    }

    @Override
    public void showEditStorage(long storageId) {
        //TODO display add edit storage screen
        Log.d(TAG, "showEditStorage: called");
        Intent intent = new Intent(getContext(), StorageAddEditActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, String.valueOf(storageId));
        startActivityForResult(intent, StorageAddEditActivity.REQUEST_EDIT_STORAGE_ROOM);
    }

    @Override
    public void showItemDetailsUi(long storageId, String itemId) {
        Intent intent = new Intent(getContext(),AddEditActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, String.valueOf(storageId));
        intent.putExtra(ARGUMENT_EDIT_ITEM_ID, itemId);
        startActivityForResult(intent, AddEditActivity.REQUEST_EDIT_ITEM);
    }

    @Override
    public void showLoadingItemsError() {
        //TODO display snackbar with error message
    }

    @Override
    public void showNoItems() {
        //TODO add UI elements with message "press + to add new items"
    }

    @Override
    public void showSuccessfullySavedMessage() {
        Toast.makeText(getContext(), "Item was successfully saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}

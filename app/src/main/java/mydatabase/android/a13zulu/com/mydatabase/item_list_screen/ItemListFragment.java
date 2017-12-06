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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.addedit_screen.AddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;

import static android.support.v4.util.Preconditions.checkNotNull;
import static mydatabase.android.a13zulu.com.mydatabase.addedit_screen.AddEditFragment.ARGUMENT_EDIT_ITEM_ID;

/**
 * A placeholder fragment containing a simple view.
 */
public class ItemListFragment extends Fragment implements ItemListContract.View {
    private static final String TAG = "ItemListFragment";

    private ItemListRecyclerViewAdapter mItemListRecyclerViewAdapter;
    private ItemListContract.UserActionListener mPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");

        mItemListRecyclerViewAdapter = new ItemListRecyclerViewAdapter(new ArrayList<Item>(0), mClickListener);
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
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showItems(List<Item> items) {
        mItemListRecyclerViewAdapter.replaceData(items);
    }

    @Override
    public void showAddItem() {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        //startActivity(intent);
        startActivityForResult(intent, AddEditActivity.REQUEST_ADD_ITEM);
    }

    @Override
    public void showItemDetailsUi(String itemId) {
        Log.d(TAG, "showItemDetailsUi: clicked");
        Intent intent = new Intent(getContext(),AddEditActivity.class);
        intent.putExtra(ARGUMENT_EDIT_ITEM_ID, itemId);
        startActivity(intent);
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

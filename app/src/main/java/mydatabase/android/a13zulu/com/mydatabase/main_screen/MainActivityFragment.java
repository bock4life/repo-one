package mydatabase.android.a13zulu.com.mydatabase.main_screen;

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
public class MainActivityFragment extends Fragment implements MainActivityContract.View {
    private static final String TAG = "MainActivityFragment";

    private ItemRecyclerViewAdapter mItemRecyclerViewAdapter;
    private MainActivityContract.UserActionListener mPresenter;

    public MainActivityFragment() {
    }

    /**
     * Listens for clicks on items in Recycler View.
     */
    ItemRecyclerViewAdapter.OnItemClickListener mClickListener = new ItemRecyclerViewAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Item item) {
            mPresenter.openItemDetails(item);
        }
    };

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: called");

        mItemRecyclerViewAdapter = new ItemRecyclerViewAdapter(new ArrayList<Item>(0), mClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d(TAG, "onCreateView: called");

        RecyclerView recyclerView = rootView.findViewById(R.id.frag_main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mItemRecyclerViewAdapter);

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
    public void setPresenter(MainActivityContract.UserActionListener presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showItems(List<Item> items) {
        mItemRecyclerViewAdapter.replaceData(items);
    }

    @Override
    public void showAddItem() {
        Intent intent = new Intent(getContext(), AddEditActivity.class);
        startActivity(intent);
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

    }

    @Override
    public void showNoItems() {

    }

    @Override
    public void showSuccessfullySavedMessage() {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}

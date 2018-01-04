package mydatabase.android.a13zulu.com.mydatabase.storage_list_screen;

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
import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;
import mydatabase.android.a13zulu.com.mydatabase.item_list_screen.ItemListActivity;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditActivity;
import mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen.StorageAddEditFragment;

/**
 * UI for the Storage Room List
 */

public class StorageListFragment extends Fragment implements StorageListContract.View{
    private static final String TAG = "StorageListFragment";

    private StorageRecyclerViewAdapter mAdapter;
    private StorageListContract.UserActionListener mPresenter;

    public StorageListFragment(){

    }

    public static StorageListFragment newInstance(){
        return new StorageListFragment();
    }

    /**
     *  On Click Listener for Storage room in recycler view.
     */
    StorageRecyclerViewAdapter.OnStorageClickListener mClickListener = new StorageRecyclerViewAdapter.OnStorageClickListener() {
        @Override
        public void onStorageClick(StorageRoom storageRoom) {
           mPresenter.openStorageRoomDetails(storageRoom);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new StorageRecyclerViewAdapter(new ArrayList<StorageRoom>(0), mClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mPresenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_storage_list, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.frag_storage_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = rootView.findViewById(R.id.frag_storage_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Add New Storage clicked");
                mPresenter.addNewStorageRoom();
            }
        });

        mPresenter.loadStorageRooms();

        return rootView;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showStorageRooms(List<StorageRoom> storageRooms) {
       mAdapter.replaceData(storageRooms);
    }

    @Override
    public void showAddStorageRoom() {
        Log.d(TAG, "showAddStorageRoom: called");
        Intent intent = new Intent(getContext(), StorageAddEditActivity.class);
        startActivityForResult(intent, StorageAddEditActivity.REQUEST_ADD_STORAGE_ROOM);
    }

    @Override
    public void showStorageRoomUI(long storageRoomId) {
        Intent intent = new Intent(getContext(), ItemListActivity.class);
        intent.putExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, storageRoomId);
        startActivity(intent);
    }

    @Override
    public void showLoadingStorageRoomsError() {

    }

    @Override
    public void showNoStorageRooms() {
        Toast.makeText(getContext(),"Press + to add new Storage", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccessfullySavedMessage() {
        Toast.makeText(getContext(), "Storage Room successfully saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(StorageListContract.UserActionListener presenter) {
        mPresenter = presenter;
    }
}

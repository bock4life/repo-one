package mydatabase.android.a13zulu.com.mydatabase.storage_list_screen;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.StorageRoom;

import static android.support.v4.util.Preconditions.checkNotNull;

public class StorageRecyclerViewAdapter
        extends RecyclerView.Adapter<StorageRecyclerViewAdapter.StorageViewHolder> {
    private static final String TAG = "StorageRecyclerAdapter";

    private List<StorageRoom> mStorageRooms;
    private OnStorageClickListener mListener;

    public interface OnStorageClickListener {
        void onStorageClick(StorageRoom storageRoom);
    }

    public StorageRecyclerViewAdapter(List<StorageRoom> storageRooms, OnStorageClickListener listener) {
        setList(storageRooms);
        mListener = listener;
    }

    private void setList(List<StorageRoom> storageRooms) {
        mStorageRooms = checkNotNull(storageRooms);
    }

    public void replaceData(List<StorageRoom> storageRooms) {
        setList(storageRooms);
        notifyDataSetChanged();
    }

    @Override
    public StorageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.storage_list_item, parent, false);

        return new StorageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StorageViewHolder holder, int position) {
        if (mStorageRooms != null) {
            final StorageRoom storage = mStorageRooms.get(position);

            holder.name.setText(storage.getName());
            holder.description.setText(storage.getDescription());
//            holder.numberOfItems.setText(storage.getItems().size()); //TODO fix resource exception
            holder.cardView.setCardBackgroundColor(storage.getBackgroundColor());
            //holder.lastAccess.setText(storage.getLastAccessed().toString());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Log.d(TAG, "onClick: called on storage:" + position);
                    mListener.onStorageClick(storage);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mStorageRooms.size();
    }

    static class StorageViewHolder extends RecyclerView.ViewHolder {

        TextView name = null;
        TextView description = null;
        TextView numberOfItems = null;
        TextView lastAccess = null;
        CardView cardView = null;

        public StorageViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.storage_list_item_name);
            this.description = itemView.findViewById(R.id.storage_list_item_description);
            this.numberOfItems = itemView.findViewById(R.id.storage_list_Item_qty);
            this.lastAccess = itemView.findViewById(R.id.storage_list_item_lastAccessDate);
            this.cardView = itemView.findViewById(R.id.storage_list_item_card_view);
        }
    }
}

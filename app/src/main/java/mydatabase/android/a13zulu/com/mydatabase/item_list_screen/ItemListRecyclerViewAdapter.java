package mydatabase.android.a13zulu.com.mydatabase.item_list_screen;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;


public class ItemListRecyclerViewAdapter
        extends RecyclerView.Adapter<ItemListRecyclerViewAdapter.ItemViewHolder> {
    private static final String TAG = "ItemListRecyclerAdapter";

    private List<Item> mItems;
    private OnItemClickListener mOnItemClickListener;


    public interface OnItemClickListener {
        void onItemClick(Item item);
    }


    public ItemListRecyclerViewAdapter(List<Item> items, OnItemClickListener onItemClickListener) {
        Log.d(TAG, "Constructor called");
        setList(items);
        mOnItemClickListener = onItemClickListener;
    }

    private void setList(List<Item> items) {
        mItems = items;
    }

    public void replaceData(List<Item> items) {
        setList(items);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if (mItems != null) {

            final Item item = mItems.get(position);
            holder.name.setText(item.getItemName());
            holder.description.setText(item.getItemDescription());
            holder.quantity.setText(String.valueOf(item.getItemQuantity()));
            holder.mCardView.setCardBackgroundColor(item.getItemStorageRoom().getBackgroundColor());

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: On Item Clicked");
                    mOnItemClickListener.onItemClick(item);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView name = null;
        TextView description = null;
        TextView quantity = null;
        CardView mCardView = null;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.list_item_name);
            this.description = itemView.findViewById(R.id.list_item_description);
            this.quantity = itemView.findViewById(R.id.list_item_quantity);
            this.mCardView = itemView.findViewById(R.id.list_item_card_view);
        }
    }
}

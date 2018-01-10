package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.Item;

public class OutOfStockItemAdapter
        extends RecyclerView.Adapter<OutOfStockItemAdapter.OutOfStockItemViewHolder> {

    private List<Item> mItemList;
    private OnItemClickListener mClickListener;

    public interface OnItemClickListener{
        void onStorageClick(long storageId);
        void onItemClick(long storageId, long itemId);
    }

    OutOfStockItemAdapter (List<Item> items, OnItemClickListener listener){
        setList(items);
        mClickListener = listener;
    }

    private void setList(List<Item> items){
        mItemList= items;
    }

    public void replaceData(List<Item> items){
        setList(items);
        notifyDataSetChanged();
    }

    @Override
    public OutOfStockItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.out_of_stock_item, parent, false);

        return new OutOfStockItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OutOfStockItemViewHolder holder, int position) {
        if(mItemList!=null){
            final Item item = mItemList.get(position);

            holder.storageName.setText(item.getItemStorageRoom().getName());
            holder.itemName.setText(item.getItemName());
            holder.remainingQuantity.setText(String.valueOf(item.getItemQuantity()));

            holder.storageName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onStorageClick(item.getItemStorageRoom().getId());
                }
            });
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(item.getItemStorageRoom().getId(), item.getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    static class OutOfStockItemViewHolder extends RecyclerView.ViewHolder {

        TextView storageName = null;
        TextView itemName = null;
        TextView remainingQuantity = null;

        public OutOfStockItemViewHolder(View itemView) {
            super(itemView);

            this.storageName = itemView.findViewById(R.id.outofstock_storage_name);
            this.itemName = itemView.findViewById(R.id.outofstock_item_name);
            this.remainingQuantity = itemView.findViewById(R.id.outofstock_remaining_qty);
        }
    }
}

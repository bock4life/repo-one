package mydatabase.android.a13zulu.com.mydatabase.transaction_list_screen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.Utils;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;


public class TransactionListRecyclerAdapter
        extends RecyclerView.Adapter<TransactionListRecyclerAdapter.TransactionListItemViewHolder>{
    private static final String TAG = "TransactionListRecycler";


    private List<ItemTransaction> mItemTransactions;
    private OnTransactionClickListener mClickListener;


    public interface OnTransactionClickListener{
        void onStorageClick(long storageId);
        void onItemClick(long storageRoomId, long itemId);
    }

    TransactionListRecyclerAdapter (List<ItemTransaction> itemTransactions, OnTransactionClickListener listener){
        setList(itemTransactions);
        mClickListener = listener;
    }


    private void setList(List<ItemTransaction> itemTransactions){
        mItemTransactions = itemTransactions;
    }

    public void replaceData(List<ItemTransaction> itemTransactions){
        setList(itemTransactions);
        notifyDataSetChanged();
    }

    @Override
    public TransactionListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);

        return new TransactionListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionListItemViewHolder holder, int position) {
        if(mItemTransactions != null){
            final ItemTransaction transaction = mItemTransactions.get(position);

            holder.storageName.setText(transaction.getTransactionItem().getItemStorageRoom().getName());
            holder.itemName.setText(Utils.fitStringIntoTextView(transaction.getTransactionItem().getItemName()));
            holder.date.setText(Utils.formatDate(transaction.getTransactionDate()));
            holder.amount.setText(String.valueOf(transaction.getQuantity()));
            holder.remaining.setText(String.valueOf(transaction.getRemainingQuantity()));

            holder.storageName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onStorageClick(transaction.getTransactionItem().getItemStorageRoom().getId());
                }
            });
            holder.itemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClick(transaction.getTransactionItem().getItemStorageRoom().getId(),
                            transaction.getTransactionItem().getId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemTransactions.size();
    }

    static class TransactionListItemViewHolder extends RecyclerView.ViewHolder{

        TextView storageName = null;
        TextView itemName = null;
        TextView date = null;
        TextView amount = null;
        TextView remaining = null;


        public TransactionListItemViewHolder(View itemView) {
            super(itemView);

            this.storageName = itemView.findViewById(R.id.transaction_list_item_storage);
            this.itemName = itemView.findViewById(R.id.transaction_list_item_item);
            this.date = itemView.findViewById(R.id.transaction_list_item_date);
            this.amount = itemView.findViewById(R.id.transaction_list_item_amount);
            this.remaining = itemView.findViewById(R.id.transaction_list_item_remaining);
        }
    }
}

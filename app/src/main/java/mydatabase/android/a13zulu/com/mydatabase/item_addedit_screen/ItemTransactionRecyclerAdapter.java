package mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.Utils;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;

public class ItemTransactionRecyclerAdapter extends RecyclerView.Adapter<ItemTransactionRecyclerAdapter.ItemTransactionHolder>{


    private List<ItemTransaction> mTransactions;

    public ItemTransactionRecyclerAdapter(List<ItemTransaction> transactions){
        setList(transactions);
    }

    private void setList(List<ItemTransaction> transactions){
        mTransactions = transactions;
    }

    public void replaceData(List<ItemTransaction> transactions){
        setList(transactions);
        notifyDataSetChanged();
    }

    @Override
    public ItemTransactionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_item_add_edit, parent, false);

        return new ItemTransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemTransactionHolder holder, int position) {

        if(mTransactions != null){
            final ItemTransaction transaction = mTransactions.get(position);

            holder.mDate.setText(Utils.formatDate(transaction.getTransactionDate()));
            holder.mAmount.setText(String.valueOf(transaction.getQuantity()));
            holder.mRemaining.setText(String.valueOf(transaction.getRemainingQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        return mTransactions.size();
    }

    static class ItemTransactionHolder extends RecyclerView.ViewHolder{

        TextView mDate = null;
        TextView mAmount = null;
        TextView mRemaining = null;


        public ItemTransactionHolder(View itemView) {
            super(itemView);

            this.mDate = itemView.findViewById(R.id.transaction_add_edit_date);
            this.mAmount = itemView.findViewById(R.id.transaction_add_edit_quantity);
            this.mRemaining = itemView.findViewById(R.id.transaction_add_edit_remaining_qty);
        }
    }
}

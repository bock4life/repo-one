package mydatabase.android.a13zulu.com.mydatabase.transaction_dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;

import static mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen.AddEditFragment.ARGUMENT_EDIT_ITEM_ID;

/**
 * UI for item transaction, inflated when user is clicked on Quantity field,
 * while Editing existing item in AddEditFragment.
 * Creates instance of TransactionPresenter
 */

public class TransactionFragment extends android.support.v4.app.DialogFragment implements TransactionContract.View{
    private static final String TAG = "TransactionFragment";
    public static final String TRANSACTION_FRAGMENT = "TRANSACTION_FRAGMENT";

    private Button plusButton;
    private Button minusButton;
    private TextView quantityView;
    private ImageButton saveButton;

    private long mItemId; // pass it as a bundle arguments when creating a dialog fragment

    private int transactionQuantity = 0;

    private TransactionPresenter mPresenter;

    private TransactionDialogCallbackContract mHost;

    public TransactionFragment() {
    }

    public static TransactionFragment newInstance(){
        return new TransactionFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_transaction, container, false);

        Bundle args = getArguments();
        mItemId = args.getLong(ARGUMENT_EDIT_ITEM_ID);

        mPresenter = new TransactionPresenter(mItemId, this, Injection.provideTransactionRepository(getContext()));//TODO check if functioning as intended

        plusButton = rootView.findViewById(R.id.frag_trans_plus_btn);
        minusButton = rootView.findViewById(R.id.frag_trans_minus_btn);
        quantityView = rootView.findViewById(R.id.frag_trans_qty_et);
        saveButton = rootView.findViewById(R.id.frag_trans_save_image_btn);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionQuantity += 1;
                quantityView.setText(String.valueOf(transactionQuantity));
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transactionQuantity -=1;
                quantityView.setText(String.valueOf(transactionQuantity));
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.performTransaction(mItemId, transactionQuantity);
                Log.d(TAG, "onClick: called, item ID: " + mItemId + " , amount:" + transactionQuantity);
            }
        });

        mHost = (TransactionDialogCallbackContract) getTargetFragment();


        return rootView;
    }

    @Override
    public void setPresenter(TransactionContract.UserActionListener presenter) {

    }

    @Override
    public void closeTransactionFragment() {
        mHost.updateUi();
        getDialog().dismiss();
    }
}

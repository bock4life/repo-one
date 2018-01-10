package mydatabase.android.a13zulu.com.mydatabase.item_addedit_screen;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import mydatabase.android.a13zulu.com.mydatabase.BaseFragment;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.data.ItemTransaction;
import mydatabase.android.a13zulu.com.mydatabase.transaction_dialog.TransactionDialogCallbackContract;
import mydatabase.android.a13zulu.com.mydatabase.transaction_dialog.TransactionFragment;

import static mydatabase.android.a13zulu.com.mydatabase.transaction_dialog.TransactionFragment.TRANSACTION_FRAGMENT;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditFragment extends BaseFragment implements AddEditContract.View, TransactionDialogCallbackContract {

    public static final String ARGUMENT_EDIT_ITEM_ID = "EDIT_ITEM_ID";
    private static final String TAG = "AddEditFragment";

    private AddEditContract.UserActionListener mPresenter;


    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private EditText mQuantityEditText;
    private FloatingActionButton mSaveFb;
    private ItemTransactionRecyclerAdapter mTransactionRecyclerAdapter;
    private RecyclerView mTransactionsRecyclerView;

    private boolean hasNewData = false;

    public AddEditFragment() {
    }

    //TODO disable Save button if item name or description fields are empty
    public static AddEditFragment newInstance() {
        return new AddEditFragment();
    }


    // Called from Activity
    @Override
    public void setPresenter(AddEditContract.UserActionListener presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransactionRecyclerAdapter = new ItemTransactionRecyclerAdapter(new ArrayList<ItemTransaction>(0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_edit, container, false);
        Log.d(TAG, "onCreateView: called");

        mNameEditText = rootView.findViewById(R.id.frag_add_edit_name_et);
        mDescriptionEditText = rootView.findViewById(R.id.frag_add_edit_description_et);
        mQuantityEditText = rootView.findViewById(R.id.frag_add_edit_quantity_et);
        mSaveFb = rootView.findViewById(R.id.frag_add_edit_save_fab);

        mTransactionsRecyclerView = rootView.findViewById(R.id.frag_add_edit_transactions_rv);
        mTransactionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mTransactionsRecyclerView.setAdapter(mTransactionRecyclerAdapter);

        mPresenter.loadTransactionList();

        mSaveFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: called");
                mPresenter.saveItem(mNameEditText.getText().toString(),
                        mDescriptionEditText.getText().toString(),
                        Integer.parseInt(mQuantityEditText.getText().toString()));
            }
        });

        mQuantityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: called");
                mPresenter.makeTransaction();
            }
        });

        // hiding recycler view when keyboard is opened
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);
                int screenHeight = rootView.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                Log.d(TAG, "keypadHeight = " + keypadHeight);

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    Log.d(TAG, "onGlobalLayout: keyboard opened");
                    mTransactionsRecyclerView.setVisibility(View.GONE);
                }
                else {
                    // keyboard is closed
                    Log.d(TAG, "onGlobalLayout: keyboard closed");
                    mTransactionsRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        return rootView;
    }

    @Override
    public void showEmptyItemNameError() {

    }

    @Override
    public void showEmptyItemDescriptionError() {

    }

    @Override
    public void showItemQuantityError() {

    }

    @Override
    public void showItemList() {
        Log.d(TAG, "showItemList: called");
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public void setName(String name) {
        mNameEditText.setText(name);
    }

    @Override
    public void setDescription(String description) {
        mDescriptionEditText.setText(description);
    }

    @Override
    public void setQuantity(int quantity) {
        mQuantityEditText.setText(String.valueOf(quantity));
    }

    @Override
    public void enableTransactionList() {
        mQuantityEditText.setFocusableInTouchMode(false);
        mQuantityEditText.setInputType(InputType.TYPE_NULL);
        //TODO create Text Views with titles for transaction list
    }

    @Override
    public void showTransactionList(List<ItemTransaction> transactionList) {
        mTransactionRecyclerAdapter.replaceData(transactionList);
    }

    @Override
    public void showNoTransactions() {

    }

    @Override
    public void showTransactionFragment(long itemId) {
        Log.d(TAG, "showTransactionFragment: called");
        TransactionFragment fragment = TransactionFragment.newInstance();
        Bundle args = new Bundle();
        args.putLong(ARGUMENT_EDIT_ITEM_ID, itemId);
        fragment.setArguments(args);
        fragment.setTargetFragment(this, 0);
        fragment.show(getFragmentManager(),TRANSACTION_FRAGMENT);
    }

    @Override
    public void updateUi() {

        hasNewData = true;
        mPresenter.populateItem();
        mPresenter.loadTransactionList();
    }


    @Override
    public void onBackPressed() {
        if (hasNewData){
            Log.d(TAG, "onBackPressed: called");
            getActivity().setResult(Activity.RESULT_OK);
        }
    }
}

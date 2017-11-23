package mydatabase.android.a13zulu.com.mydatabase.addedit_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.main_screen.MainActivity;
import mydatabase.android.a13zulu.com.mydatabase.transaction_screen.TransactionFragment;

import static android.support.v4.util.Preconditions.checkNotNull;
import static mydatabase.android.a13zulu.com.mydatabase.transaction_screen.TransactionFragment.TRANSACTION_FRAGMENT;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddEditFragment extends Fragment implements AddEditContract.View {

    public static final String ARGUMENT_EDIT_ITEM_ID = "EDIT_ITEM_ID";
    private static final String TAG = "AddEditFragment";

    private AddEditContract.UserActionListener mPresenter;


    private EditText mNameEditText;
    private EditText mDescriptionEditText;
    private EditText mQuantityEditText;
    private FloatingActionButton mSaveFb;

    public AddEditFragment() {
    }

    //TODO disable Save button if item name or description fields are empty
    public static AddEditFragment newInstance() {
        return new AddEditFragment();
    }


    // Called from Activity
    @Override
    public void setPresenter(AddEditContract.UserActionListener presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO Save button
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_edit, container, false);
        Log.d(TAG, "onCreateView: called");

        mNameEditText = rootView.findViewById(R.id.frag_add_edit_name_et);
        mDescriptionEditText = rootView.findViewById(R.id.frag_add_edit_description_et);
        mQuantityEditText = rootView.findViewById(R.id.frag_add_edit_quantity_et);
        mSaveFb = rootView.findViewById(R.id.frag_add_edit_save_fab);

        mSaveFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: called");
                mPresenter.saveItem(mNameEditText.getText().toString(),
                        mDescriptionEditText.getText().toString(),
                        Integer.parseInt(mQuantityEditText.getText().toString()));
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
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
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
        //mQuantityEditText.setFocusable(true);
        mQuantityEditText.setFocusableInTouchMode(false);
        mQuantityEditText.setInputType(InputType.TYPE_NULL);

        mQuantityEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: called");
               mPresenter.makeTransaction();
            }
        });
    }

    @Override
    public void showTransactionFragment(long itemId) {
        Log.d(TAG, "showTransactionFragment: called");
        TransactionFragment fragment = TransactionFragment.newInstance();
        Bundle args = new Bundle();
        args.putLong(ARGUMENT_EDIT_ITEM_ID, itemId);
        fragment.setArguments(args);
        fragment.show(getFragmentManager(),TRANSACTION_FRAGMENT);
    }
}

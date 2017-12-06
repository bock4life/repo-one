package mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen;


import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import mydatabase.android.a13zulu.com.mydatabase.R;


public class StorageAddEditFragment extends Fragment implements StorageAddEditContract.View{
    private static final String TAG = "StorageAddEditFragment";

    public static final String ARGUMENT_EDIT_STORAGE_ROOM_ID = "STORAGE_ROOM_ID";

    private StorageAddEditContract.UserActionListener mPresenter;

    private EditText mStorageName;
    private EditText mStorageDescription;
    private Button mSaveButton;
    private ConstraintLayout mLayout;
    private RadioGroup mRadioGroup;
    private int selectedBackgroundColor = 0;

    public StorageAddEditFragment(){

    }

    public static StorageAddEditFragment newInstance(){
        return new StorageAddEditFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_storage_add_edit, container, false);

        mStorageName = rootView.findViewById(R.id.frag_storage_addedit_name);
        mStorageDescription = rootView.findViewById(R.id.frag_storage_addedit_description);
        mSaveButton = rootView.findViewById(R.id.frag_storage_addedit_save_btn);
        mLayout = rootView.findViewById(R.id.frag_storage_addedit_layout);


        mRadioGroup = rootView.findViewById(R.id.frag_storage_addedit_colorRadioGroup);

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               RadioButton radioButton = rootView.findViewById(radioGroup.getCheckedRadioButtonId());
               mPresenter.getBackgroundColor(getBackgroundColor(radioButton));
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mPresenter.saveStorageRoom(mStorageName.getText().toString(), mStorageDescription.getText().toString(), selectedBackgroundColor);
            }
        });

        return rootView;
    }

    /**
     * @param view from which background color is getting retrieved
     * @return resource ID of the RadioButton background color
     * Tag must match the number of resource
     * Assigns value to selectedBackgroundColor class variable
     * TODO implement programmatic way of retrieving background color
     */
    private int getBackgroundColor(View view){
        int color = 0;
        Resources res = getResources();
        switch(view.getTag().toString()){
            case "1":
                color = res.getColor(R.color.color1);
                break;
            case "2":
                color = res.getColor(R.color.color2);
                break;
            case "3":
                color = res.getColor(R.color.color3);
                break;
            case "4":
                color = res.getColor(R.color.color4);
                break;
        }
        selectedBackgroundColor = color;
        return color;
    }

    @Override
    public void setPresenter(StorageAddEditContract.UserActionListener presenter) {
        mPresenter = presenter;
    }

    /**
     *  Navigating back to the StorageListActivity
     *  setting result code to RESULT_OK
     */
    @Override
    public void showStorageList() {
       getActivity().setResult(Activity.RESULT_OK);
       getActivity().finish();
    }

    @Override
    public void setStorageName(String storageName) {
        mStorageName.setText(storageName);
    }

    @Override
    public void setStorageDescription(String storageDescription) {
        mStorageDescription.setText(storageDescription);
    }

    @Override
    public void setBackground(int color) {
        mLayout.setBackgroundColor(color);
    }
}

package mydatabase.android.a13zulu.com.mydatabase.storage_add_edit_screen;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import mydatabase.android.a13zulu.com.mydatabase.Injection;
import mydatabase.android.a13zulu.com.mydatabase.R;
import mydatabase.android.a13zulu.com.mydatabase.Utils.ActivityUtils;

public class StorageAddEditActivity extends AppCompatActivity{
    private static final String TAG = "StorageAddEditActivity";
    public static final int REQUEST_ADD_STORAGE_ROOM = 2;
    public static final int REQUEST_EDIT_STORAGE_ROOM = 3;
    //TODO finish

    private StorageAddEditPresenter mStorageAddEditPresenter;
    private StorageAddEditFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_add_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mFragment = (StorageAddEditFragment) getSupportFragmentManager().findFragmentById(R.id.storage_addedit_activity_content_frame);

        String storageId = getIntent().getStringExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID);


        if(mFragment == null){
            mFragment = StorageAddEditFragment.newInstance();
            if(getIntent().hasExtra(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID)){
                actionBar.setTitle("Edit Storage");
                Bundle bundle = new Bundle();
                bundle.putString(StorageAddEditFragment.ARGUMENT_EDIT_STORAGE_ROOM_ID, storageId);
                mFragment.setArguments(bundle);
            }else{
                actionBar.setTitle("New Storage");
            }
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mFragment, R.id.storage_addedit_activity_content_frame);
        }

        mStorageAddEditPresenter = new StorageAddEditPresenter(Injection.provideStorageRoomsRepository(getApplicationContext()),
                mFragment, storageId);
        mFragment.setPresenter(mStorageAddEditPresenter);
    }
}

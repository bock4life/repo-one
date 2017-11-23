package mydatabase.android.a13zulu.com.mydatabase;

import android.app.Application;


public class App extends Application {
    public static final String TAG = "DaocompatExample";

    //BoxStore mBoxStore;
    //DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        //mBoxStore = MyObjectBox.builder().androidContext(getApplicationContext()).build(); //TODO confirm that context is correct
        //mDaoSession = new DaoSession(mBoxStore);

    }

}

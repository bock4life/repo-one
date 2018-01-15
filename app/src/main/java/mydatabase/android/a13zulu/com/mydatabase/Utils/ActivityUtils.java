package mydatabase.android.a13zulu.com.mydatabase.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import mydatabase.android.a13zulu.com.mydatabase.R;

/**
 * This provides methods to help Activities load their UI.
 */

public class ActivityUtils {

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}.
     * performed by the {@code fragmentManager}
     */

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment,
                                             int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    public static int getOutOfStockSharedPref(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        return prefs.getInt(context.getResources().getString(R.string.pref_out_of_stock_limit_key),
                Integer.parseInt(context.getResources().getString(R.string.pref_out_of_stock_limit_default)));
        //return 0;
    }
}

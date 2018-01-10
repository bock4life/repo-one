package mydatabase.android.a13zulu.com.mydatabase.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    // TODO create methods for fetching Shared Preferences
}

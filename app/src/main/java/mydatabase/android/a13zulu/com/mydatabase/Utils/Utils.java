package mydatabase.android.a13zulu.com.mydatabase.Utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static String formatDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String fitStringIntoTextView(String longString){

        StringBuilder sb = new StringBuilder(longString);
        int i = 0;
        int numberOfChars = 8;
        while((i = sb.indexOf(" ", i + numberOfChars)) != -1){
            sb.replace(i, i + 1, "\n");
        }
        return sb.toString();
    }
}

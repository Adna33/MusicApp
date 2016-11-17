package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Korisnik on 5.6.2016.
 */
public class InternetConectivity {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int dajStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String dajStatusString(Context context) {
        int st = InternetConectivity.dajStatus(context);
        String status = null;
        if (st == InternetConectivity.TYPE_WIFI) {
            status = "Uključen WIFI!";
        } else if (st == InternetConectivity.TYPE_MOBILE) {
            status = "Uključen mobilni internet!";
        } else if (st == InternetConectivity.TYPE_NOT_CONNECTED) {
            status = "Nema konekcije! ";
        }
        return status;
    }
}

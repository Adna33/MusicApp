package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Korisnik on 30.3.2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    public void onReceive(Context context, Intent intent) {

        if (isWifiConnected(context) || isMobileConnected(context))
        {
            Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
        }
        else Toast.makeText(context, "Disconnected", Toast.LENGTH_LONG).show();
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isMobileConnected(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        return networkInfo != null && networkInfo.isConnected();
    }

}

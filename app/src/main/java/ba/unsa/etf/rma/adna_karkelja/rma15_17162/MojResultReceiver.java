package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.os.Bundle;
import android.os.ResultReceiver;

/**
 * Created by Korisnik on 16.5.2016.
 */
public class MojResultReceiver extends ResultReceiver{
    private Receiver mReceiver;
    public MojResultReceiver(android.os.Handler handler) {
        super(handler);
    }
    public void setReceiver(Receiver receiver) {
        mReceiver = receiver;
    }
    /* Deklaracija interfejsa koji će se trebati implementirati */
    public interface Receiver {
        public void onReceiveResult(int resultCode, Bundle resultData);
    }
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (mReceiver != null) {
            mReceiver.onReceiveResult(resultCode, resultData);
        }
    }
}

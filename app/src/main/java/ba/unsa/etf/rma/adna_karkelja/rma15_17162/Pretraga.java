package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Korisnik on 6.6.2016.
 */
public class Pretraga implements Parcelable{
    public String nazivpretrage;
    private String vrijemepretrage;
    private String tippretrage;
    public Pretraga(String n, String v, String t) {
        super();
        nazivpretrage=n;
        vrijemepretrage=v;
        tippretrage=t;
    }
    protected Pretraga(Parcel p) {
        nazivpretrage = p.readString();
        vrijemepretrage = p.readString();
        tippretrage = p.readString();
    }
    public String getNaziv(){return nazivpretrage;}
    public String getTip() {
        return tippretrage;
    }
    public String getVrijeme() {
        return vrijemepretrage;
    }
    public static final Parcelable.Creator<Pretraga> CREATOR = new Parcelable.Creator<Pretraga>() {
        @Override
        public Pretraga createFromParcel(Parcel in) {
            return new Pretraga(in);
        }
        @Override
        public Pretraga[] newArray(int size) {
            return new Pretraga[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nazivpretrage);
        dest.writeString(vrijemepretrage);
        dest.writeString(tippretrage);
    }
}

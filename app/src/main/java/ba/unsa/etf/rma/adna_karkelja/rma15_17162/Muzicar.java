package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Korisnik on 9.3.2016.
 */

//NAPOMENA (ZADACA3) - u metodi getMuzicar sam zakomentarisala uslov da su im imena razlicita, tako da se u slicne muzicare dodaje
    //i sam muzicar, da ne bi liste bile prazne, jer sam samo za prvog dodala slicnog, da ne kopiram isto 20 puta
public class Muzicar implements Parcelable{
    public String ime;
    public String prezime;
    public String webStranica;
    public Zanrovi zanr;
    public String biografija;
    public ArrayList<String> pjesme= new ArrayList<String>();;
    public ArrayList<String> albumi = new ArrayList<String>();
    public Muzicar(String i, String p, String w, Zanrovi z, String b,ArrayList<String> pj)
    {
        ime=i;
        prezime=p;
        zanr=z;
        webStranica=w;
        biografija=b;
        pjesme=pj;
    }
    //NOVI KONSTRUKTOR - ZADAĆA 4-5
    public Muzicar(String i,  String z, String w)
    {
        ime=i;
        zanr=StringUEnum(z);
        webStranica=w;
        prezime="";

    }
    public Muzicar(){}

    public Muzicar(String i, String p, String w, String z, String b,ArrayList<String> pj)
    {
        ime=i;
        prezime=p;
        zanr=StringUEnum(z);
        webStranica=w;
        biografija=b;
        pjesme=pj;
    }
    public Zanrovi StringUEnum(String s)
    {
        if(s.equals("pop") || s.equals("Pop")) return Zanrovi.Pop;
        if(s.equals("rock") || s.equals("Rock")) return Zanrovi.Rock;
        if(s.equals("hipHop") || s.equals("HipHop")) return Zanrovi.HipHop;
        if(s.equals("metal") || s.equals("Metal")) return Zanrovi.Metal;
        if(s.equals("country") || s.equals("Country")) return Zanrovi.Country;
        if(s.equals("jazz") || s.equals("Jazz")) return Zanrovi.Jazz;
        if(s.equals("classical") || s.equals("Jazz")) return Zanrovi.Classical;
        else return Zanrovi.Ostalo;
    }
    protected Muzicar(Parcel in) {
        ime = in.readString();
        prezime=in.readString();
        zanr=Zanrovi.valueOf(in.readString());
        webStranica=in.readString();
        biografija=in.readString();
        pjesme=(ArrayList<String>)in.readSerializable();
    }
    public static final Creator<Muzicar> CREATOR = new Creator<Muzicar>() {
        @Override
        public Muzicar createFromParcel(Parcel in) {
            return new Muzicar(in);
        }
        @Override
        public Muzicar[] newArray(int size) {
            return new Muzicar[size];
        }
    };
    public String getIme() {
        return ime;
    }
    public void setIme(String ime) {
        this.ime = ime;
    }
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    public String getZanr()
    {
        return zanr.toString();
    }
    public void setZanr(String zanr) {

        this.zanr = StringUEnum(zanr);
    }
    public ArrayList<String> muzicaralbumi = new ArrayList<String>();
    public String getWww()
    {
        return webStranica;
    }
    public ArrayList<String> getAlbumi() {
        return albumi;
    }
    public void setWww(String www)
    {
        this.webStranica = www;
    }
    public ArrayList<Muzicar> getMuzicari(ArrayList<Muzicar> muzicari) {
        ArrayList<Muzicar> slMuzicari = new ArrayList<Muzicar>();
        for (Muzicar m : muzicari) {
            if(m.zanr==this.zanr /* && !m.ime.equals(this.ime)*/)
                slMuzicari.add(m);
        }
        return slMuzicari;
    }
    public ArrayList<String> muzicari=new ArrayList<String>();
    public ArrayList<String> muzicarSlicni=new ArrayList<String>();
    public ArrayList<String> getSlMuzicari() {
        return muzicari;
    }


    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ime);
        dest.writeString(prezime);
        dest.writeString(zanr.name());
        dest.writeString(webStranica);
        dest.writeString(biografija);
        dest.writeStringList(pjesme);

    }


    @Override
    public String toString()
    {return ime+" "+ prezime+"\n"+zanr.toString();}
    public String getName()
    {return ime+" "+ prezime;}
}

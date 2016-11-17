package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Korisnik on 5.6.2016.
 */
public class FragmentMuzicari extends Fragment implements MojResultReceiver.Receiver  {

    public static ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
    Muzicar muzicar;
    MyArrayAdapter adapter;
    OnItemClick oic;
    int tmp1 = 0;
    int tmp2 = 0;
    int tmp3 = 0;

    public MojResultReceiver.Receiver funkcija(){
        return FragmentMuzicari.this;
    }

    //Zadaća 6, odvojena lista koja je bila u fragment lista jer se na dugme prethodne pretrage mjenja
    //sa uspjesnim/neuspjesnim pretragama

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View iv = inflater.inflate(R.layout.fragment_muzicari, container, false);


        final ListView lista= (ListView)iv.findViewById(R.id.listView);


        if(getArguments().containsKey("Mlista")) {

            muzicari = getArguments().getParcelableArrayList("Mlista");
            MuzicarDBOpenHelper pom = new MuzicarDBOpenHelper(getActivity(), "tabela_muzicara", null, 1);
            SQLiteDatabase db = pom.getWritableDatabase();
            String[] koloneRezulat = new String[]{MuzicarDBOpenHelper.MUZICAR_ID, MuzicarDBOpenHelper.MUZICAR_IME, MuzicarDBOpenHelper.MUZICAR_ZANR, MuzicarDBOpenHelper.MUZICAR_PREZIME, MuzicarDBOpenHelper.MUZICAR_WEB};
            String where = null;
            String whereArgs[] = null;
            String groupBy = null;
            String having = null;
            String order = null;
            String[] koloneRezulat2 = new String[]{MuzicarDBOpenHelper.ALBUM_ID, MuzicarDBOpenHelper.ALBUM_NAZIV, MuzicarDBOpenHelper.ALBUM_MUZICAR_ID};
            String[] koloneRezulat3 = new String[]{MuzicarDBOpenHelper.PJESMA_ID, MuzicarDBOpenHelper.PJESMA_NAZIV, MuzicarDBOpenHelper.PJESMA_MUZICAR_ID};

            Cursor cursor = db.query(MuzicarDBOpenHelper.DATABASE_TABLE1,
                    koloneRezulat, where,
                    whereArgs, groupBy, having, order);

            Cursor cursor2 = db.query(MuzicarDBOpenHelper.DATABASE_TABLE2,
                    koloneRezulat2, null,
                    whereArgs, groupBy, having, order);

            Cursor cursor3 = db.query(MuzicarDBOpenHelper.DATABASE_TABLE3,
                    koloneRezulat3, null,
                    whereArgs, groupBy, having, order);


            InternetConectivity st = new InternetConectivity();
            if (cursor.moveToLast()) { //pocni od kraja kako bi posljednja pretraga bila na vrhu
                do {
                    Muzicar muz = new Muzicar();
                    tmp1 = cursor.getInt(0);
                    muz.setIme(cursor.getString(1));
                    muz.setPrezime(cursor.getString(3));
                    muz.setZanr(cursor.getString(2));
                    muz.setWww(cursor.getString(4));
                    ArrayList<String> pjesme = new ArrayList<>();
                    ArrayList<String> albums = new ArrayList<>();
                    muz.muzicarSlicni = new ArrayList<>();

                    if (cursor2.moveToFirst()) {
                        do {
                            tmp2 = cursor2.getInt(2);
                            if (tmp1 == tmp2) {
                                albums.add(cursor2.getString(1));
                            }
                        }
                        while (cursor2.moveToNext());
                    }
                    if (cursor3.moveToFirst()) {
                        do {
                            tmp3 = cursor3.getInt(2);
                            if (tmp1 == tmp3) {
                                pjesme.add(cursor3.getString(1));
                            }
                        }
                        while (cursor3.moveToNext());
                    }
                    muz.albumi = albums;
                    muz.pjesme = pjesme;
                    boolean flag = false;
                    for (Muzicar m : muzicari) {
                        if (m.ime.equals(muz.ime) && m.prezime.equals(muz.prezime))
                            flag = true;
                    }
                    if (flag == false && muzicari.size()<=4)
                        muzicari.add(muz);
                }
                while (cursor.moveToPrevious());
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        adapter = new MyArrayAdapter(getActivity(), muzicari, R.layout.element_liste);
        lista.setAdapter(adapter);
        try{
            oic = (OnItemClick)getActivity();
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString() +
                    "Treba implementirati OnItemClick");
        }
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                oic.onItemClicked(position);
            }
        });

        return iv;
    }
    public interface OnItemClick {
        public void onItemClicked(int pos);
    }
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        InternetConectivity stanje=new InternetConectivity();
        if(!(stanje.dajStatusString(getActivity()).equals("Nema konekcije!"))) {
            switch (resultCode) {

                case MyIntentService.STATUS_RUNNING:

                    Toast.makeText(getActivity(), "Pretrazivanje u toku", Toast.LENGTH_LONG).show();
                    break;
                case MyIntentService.STATUS_FINISHED:

                    ArrayList<Muzicar> rez = resultData.getParcelableArrayList("result");
                    muzicari.clear();
                    for (Muzicar m : rez)
                        muzicari.add(m);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Pretrazivanje gotovo", Toast.LENGTH_LONG).show();
                    break;
                case MyIntentService.STATUS_ERROR:

                    String error = resultData.getString(Intent.EXTRA_TEXT);
                    Toast.makeText(getActivity(), "Doslo je do greske!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }


}

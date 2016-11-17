package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Korisnik on 6.6.2016.
 */
public class FragmentPrethodnePretrage extends Fragment {
    final ArrayList<Pretraga> prethodni=new ArrayList<>();
    public static ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();

    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        View fr = inflater.inflate(R.layout.fragment_prethodne_pretrage, container, false);

        final ListView l = (ListView) fr.findViewById(R.id.listViewPrethodnePretrage);
        ArrayList<Pretraga> lista=new ArrayList<>();

        if (getArguments().containsKey("ListaPrethodnih")) {
            MuzicarDBOpenHelper pom = new MuzicarDBOpenHelper(getActivity(), "tabela_muzicara", null, 1);
            SQLiteDatabase db = pom.getWritableDatabase();
            String[] koloneRezulat = new String[]{MuzicarDBOpenHelper.PRETRAGA_ID, MuzicarDBOpenHelper.PRETRAGA_NAZIV, MuzicarDBOpenHelper.PRETRAGA_VRIJEME, MuzicarDBOpenHelper.PRETRAGA_TIP};
            String where = null;
            String whereArgs[] = null;
            String groupBy = null;
            String having = null;
            String order = null;
            ;
            Cursor cursor = db.query(MuzicarDBOpenHelper.DATABASE_TABLE4,
                    koloneRezulat, where,
                    whereArgs, groupBy, having, order);

            if (cursor.moveToLast()) {
                do {
                    prethodni.add(new Pretraga(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                }
                while (cursor.moveToPrevious() && prethodni.size()<5);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            //kreiran je novi adapter za prikaz pretraga u listview-u, kao i novi layout pretraga_item
            PrilagodjeniAdapter adapter=new PrilagodjeniAdapter(getActivity(), R.layout.element_prethodne_pretrage, prethodni);
            l.setAdapter(adapter);
        }

        l.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onItemClicked(position);
            }
        });

        return fr;
    }

    public void onItemClicked(int pos) {

        Pretraga pret=prethodni.get(pos);
        muzicari.clear();
        FragmentLista.pretrazeno=pret.getNaziv();
        FragmentLista.pretraga.callOnClick();

    }


}


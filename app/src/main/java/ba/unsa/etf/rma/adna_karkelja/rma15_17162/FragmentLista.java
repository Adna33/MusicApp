package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by ETF-LAB-1-11 on 4/18/2016.
 */
public class FragmentLista extends Fragment {

    private MyArrayAdapter aa;
    public static ArrayList<Muzicar> pom = new ArrayList<Muzicar>();
    //prethodna pretraga
    public static String pretrazeno;
    public static Button pretraga;
    Muzicar muzicar;
    public interface OnItemClick {
        public void onItemClicked(int pos);


    }
    private ArrayList<Muzicar> muzicari;
    private ListView lv;
    private OnItemClick oic;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View rview = inflater.inflate(R.layout.fragmentliste, container, false);


        if (getArguments().containsKey("Alista")) {

            muzicari = getArguments().getParcelableArrayList("Alista");
            Bundle arguments = new Bundle();
            arguments.putParcelableArrayList("Mlista", muzicari);
            final FragmentMuzicari fd = new FragmentMuzicari();
            fd.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.pocetnifragmentpromjena, fd)
                    .commit();
           // lv = (ListView) rview.findViewById(R.id.listView);

//Zakom
           /* aa = new MyArrayAdapter(
                    getActivity(), muzicari,
                    R.layout.element_liste
            );*/


//Povezujemo adapter na listview
            //lv.setAdapter(aa);


            /*lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    oic.onItemClicked(position);
                }
            });*/
            final Button dugme = (Button) rview.findViewById(R.id.button);
            final EditText tekst = (EditText) rview.findViewById(R.id.editText);


            dugme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //muzicari.clear();
                    //MyArrayAdapter adapter=(MyArrayAdapter) lv.getAdapter();
                    //adapter.notifyDataSetChanged();

                        EditText tekst = (EditText) rview.findViewById(R.id.editText);
                        String upit = tekst.getText().toString();
                        if(pretrazeno!=null) {
                            upit = pretrazeno;
                        }
                        pretrazeno=null;
                        muzicari.clear();
                        InternetConectivity e = new InternetConectivity();
                        MuzicarDBOpenHelper pom2 = new MuzicarDBOpenHelper(getActivity(), "tabela_muzicara", null, 1);
                        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+2:00"));
                        Date currentLocalTime = cal.getTime();
                        DateFormat date = new SimpleDateFormat("HH:mm a");
                        date.setTimeZone(TimeZone.getTimeZone("GMT+2:00"));

                        String localTime = date.format(currentLocalTime);
                        if (e.dajStatusString(getActivity()).equals("Nema konekcije!") && !(upit.isEmpty())) {
                            pom2.insertPretraga(upit, localTime, "Neuspjesna");
                            Toast.makeText(getActivity(), "Spasena pretraga!", Toast.LENGTH_LONG).show();
                        } else if (!(upit.isEmpty())) pom2.insertPretraga(upit, localTime, "Uspjesna");

                        muzicari = getArguments().getParcelableArrayList("Alista");
                        Bundle arguments = new Bundle();
                        arguments.putParcelableArrayList("Mlista", muzicari);
                        final FragmentMuzicari fd = new FragmentMuzicari();
                        fd.setArguments(arguments);
                        getFragmentManager().beginTransaction()
                                .replace(R.id.pocetnifragmentpromjena, fd)
                                .commit();
                        Intent intent = new Intent(Intent.ACTION_SYNC, null, getActivity(), MyIntentService.class);
                        MojResultReceiver mReceiver = new MojResultReceiver(new Handler());
                        mReceiver.setReceiver(fd.funkcija());
                        intent.putExtra("ime",upit);
                        intent.putExtra("receiver", mReceiver);
                        getActivity().startService(intent);
                        tekst.setText("");

                    }



            });
            /*Intent shareText = getActivity().getIntent();
            if (shareText.getAction() == Intent.ACTION_SEND) {
                String text = shareText.getStringExtra(Intent.EXTRA_TEXT);
                EditText edtekst = (EditText) rview.findViewById(R.id.editText);
                edtekst.setText(text);
            }*/
            Button prethodne = (Button) rview.findViewById(R.id.prethodna);
            prethodne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArrayList<Pretraga> lista = new ArrayList<Pretraga>();
                    Bundle arguments = new Bundle();
                    arguments.putParcelableArrayList("ListaPrethodnih", lista);
                    final FragmentPrethodnePretrage fd = new FragmentPrethodnePretrage();
                    fd.setArguments(arguments);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.pocetnifragmentpromjena, fd)
                            .commit();


                }
            });


        }
        return rview;

    }
/*
    @Override
    public void onDone(Muzicar m) {
        muzicari.add(m);
        //MyArrayAdapter adapter = (MyArrayAdapter) lv.getAdapter();

        //tekst.setText(muzicar.getName());
        aa.notifyDataSetChanged();
    }*/
    /*@Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case MyIntentService.STATUS_RUNNING:
 *//* Ovdje ide kod koji obavještava korisnika da je poziv upućen *//*
                break;
            case MyIntentService.STATUS_FINISHED:
 *//* Dohvatanje rezultata i update UI *//*
               // String[] results = resultData.getStringArray("result");
                ArrayList<Muzicar> results = resultData.getParcelableArrayList("result");
                for (int i = 0; i < results.size(); i++)
                    muzicari.add(results.get(i));
                aa.notifyDataSetChanged();
                for (int i = 0; i < muzicari.size(); i++) {
                    for (int j = 0; j < muzicari.size(); j++) {
                        if (i != j) {
                            if (muzicari.get(i).getZanr().matches(muzicari.get(j).getZanr())) {
                                muzicari.get(i).muzicari.add(muzicari.get(j).getName());
                                muzicari.get(i).muzicarSlicni.add(muzicari.get(j).webStranica);
                            }
                        }
                    }

                }
                aa.notifyDataSetChanged();
                break;
            case MyIntentService.STATUS_ERROR:
 *//* Slučaj kada je došlo do greške *//*
                String error = resultData.getString(Intent.EXTRA_TEXT);
                Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
                break;
        }
    }
*/

}

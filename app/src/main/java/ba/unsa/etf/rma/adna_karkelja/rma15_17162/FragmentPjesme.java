package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Korisnik on 30.4.2016.
 */
public class FragmentPjesme extends Fragment {
    private Muzicar muzicar;

    public interface OnSongClick {
        public void onSongClicked(int pos);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentpjesme, containter, false);
        //zadaca 4 zadatak 2
        ListView lv = (ListView) v.findViewById(R.id.toppjesme);
        final Muzicar muzicar = getArguments().getParcelable("muzicar_pjesme");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), R.layout.pjesma, R.id.imePjesme, muzicar.pjesme);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent ytIntent = new Intent(Intent.ACTION_SEARCH);
                ytIntent.setPackage("com.google.android.youtube");
                ytIntent.putExtra("query", muzicar.getIme() + " " + muzicar.pjesme.get(position));
                if (ytIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(ytIntent);
                }else {
                    Toast.makeText(getActivity(), "Nemate youtube app!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*if(getArguments().containsKey("muzicar")) {
            muzicar = getArguments().getParcelable("muzicar");
            List<String> listapjesama = new ArrayList<>(muzicar.pjesme);
            ListView lv_songs = (ListView) v.findViewById(R.id.toppjesme);
            lv_songs.setDivider(null);
            lv_songs.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.pjesma, R.id.imePjesme, listapjesama));

            lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent ytIntent = new Intent(Intent.ACTION_SEARCH);
                    ytIntent.setPackage("com.google.android.youtube");
                    ytIntent.putExtra("query", parent.getItemAtPosition(position).toString() + " " + muzicar.getIme() + " " + muzicar.prezime);
                    ytIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (ytIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(ytIntent);
                    } else {
                        Toast.makeText(getActivity(), "Nemate youtube app!", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


        return v;
    }
}

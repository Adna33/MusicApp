package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.annotation.TargetApi;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ETF-LAB-1-11 on 4/18/2016.
 */
public class FragmentDetalji extends Fragment {

    Muzicar muzicar;
    private ArrayList<Muzicar>SLmuzicari=new ArrayList<Muzicar>();


    @SuppressLint("NewApi")
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<Muzicar> muzicari = new ArrayList<Muzicar>();
        View iv= inflater.inflate(R.layout.fragmentdetalji, container, false);

        if(getArguments()!=null && getArguments().containsKey("muzicar") /*&& getArguments().containsKey("muzicari")*/){
            muzicar=getArguments().getParcelable("muzicar");
            //muzicari = getArguments().getParcelableArrayList("muzicari");

            final TextView ime = (TextView) iv.findViewById(R.id.dime);
            //final TextView prezime= (TextView) iv.findViewById(R.id.dprezime);
            final TextView web= (TextView) iv.findViewById(R.id.dweb);
            final TextView zanr= (TextView) iv.findViewById(R.id.dzanr);
            //final TextView bio= (TextView) iv.findViewById(R.id.dbio);

            //final ListView toppjesme = (ListView) iv.findViewById(R.id.toppjesme);

            ime.setText(muzicar.ime);
            //prezime.setText(muzicar.prezime);
            web.setText(muzicar.webStranica);
            //bio.setText(muzicar.biografija);
            zanr.setText(muzicar.zanr.toString());

            /*ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, muzicar.pjesme);
            toppjesme.setAdapter(ad);*/

            web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = muzicar.webStranica;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            //ShareButton
            final ImageButton shareMuzicara= (ImageButton) iv.findViewById(R.id.share);
            shareMuzicara.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i3 = new Intent(Intent.ACTION_SEND);
                    i3.putExtra(Intent.EXTRA_TEXT, muzicar.ime);
                    i3.setType("text/plain");
                    startActivity(i3);
                }
            });
            Bundle args = new Bundle();
            //args.putParcelable("muzicar", muzicar);

            Bundle argsmuzicari = new Bundle();
            //argsmuzicari.putParcelableArrayList("muzicari", muzicari);

            argsmuzicari.putParcelable("muzicar_albumi", muzicar);
            Bundle argsmuzicari2 = new Bundle();
            argsmuzicari2.putParcelable("muzicar_pjesme", muzicar);
            Bundle argsmuzicari3 = new Bundle();
            argsmuzicari3.putParcelable("muzicar_slicni", muzicar);
            final FragmentPjesme topPjesmeFragment = new FragmentPjesme ();
            topPjesmeFragment.setArguments(argsmuzicari2);

            final FragmentSlicniMuzicari fragmentslMuzicari = new FragmentSlicniMuzicari();
            fragmentslMuzicari.setArguments(argsmuzicari3);

            final FragmentAlbumi fragmentAlbumi = new FragmentAlbumi();
            fragmentAlbumi.setArguments(argsmuzicari);

            getChildFragmentManager().beginTransaction()
                    .replace(R.id.slicni_muzicari_fragment, topPjesmeFragment) .commit();


            Button topPjesmeButton = (Button)iv.findViewById(R.id.button2);
            topPjesmeButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.slicni_muzicari_fragment, topPjesmeFragment).commit();
                }
            });
            Button slicniMuzicariButton = (Button)iv.findViewById(R.id.button3);
            slicniMuzicariButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {

                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.slicni_muzicari_fragment, fragmentslMuzicari).commit();
                }
            });
            Button topAlbumiButton = (Button)iv.findViewById(R.id.button4);
            topAlbumiButton.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.slicni_muzicari_fragment, fragmentAlbumi).commit();
                }
            });
        }
        return iv;
    }

}

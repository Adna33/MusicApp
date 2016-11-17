package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Korisnik on 30.4.2016.
 */
public class FragmentSlicniMuzicari extends Fragment {

    public FragmentSlicniMuzicari() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        final Muzicar muzicar;
        ArrayList<Muzicar> muzicari=new ArrayList<Muzicar>();
        View v = inflater.inflate(R.layout.fragmentslicnimuzicari, containter, false);
        final ListView lv = (ListView) v.findViewById(R.id.slicni_muzicari);
        muzicar = getArguments().getParcelable("muzicar_slicni");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), R.layout.element_muzicara, R.id.slmuzicar, muzicar.getSlMuzicari());
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(muzicar.muzicarSlicni.get(position)));
                startActivity(i);
            }
        });


        return v;
    }
}

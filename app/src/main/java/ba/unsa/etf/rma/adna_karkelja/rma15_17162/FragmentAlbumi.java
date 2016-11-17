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

/**
 * Created by Korisnik on 21.5.2016.
 */
public class FragmentAlbumi extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentalbumi, containter, false);
        ListView lv = (ListView) v.findViewById(R.id.topalbumi);
        final Muzicar muzicar = getArguments().getParcelable("muzicar_albumi");
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), R.layout.element_albuma, R.id.albumdescription, muzicar.getAlbumi());
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(muzicar.muzicaralbumi.get(position)));
                startActivity(i);
            }
        });

        return v;
    }
}

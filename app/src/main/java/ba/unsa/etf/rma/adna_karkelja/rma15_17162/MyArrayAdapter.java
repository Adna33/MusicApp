package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Korisnik on 9.3.2016.
 */
public class MyArrayAdapter extends ArrayAdapter<Muzicar> {
    //int resource;
    private  int res;
    int id;
    Integer[] iconid = {
            R.drawable.pop,
            R.drawable.rock,
            R.drawable.hiphop,
            R.drawable.metal,
            R.drawable.country,
            R.drawable.jazz,
            R.drawable.classical,
            R.drawable.slika1

    };
    /*public MyArrayAdapter(Context context, int _resource, List<Muzicar> items) {
        super(context, _resource, items);
        resource = _resource;
    }*/
    public MyArrayAdapter(Context context, ArrayList<Muzicar> items,int resource) {
        super(context,0, items);
        this.res=resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Muzicar m = getItem(position);
        LinearLayout newView;

        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater)getContext().
                    getSystemService(inflater);
            li.inflate(res, newView, true);
        } else {
            newView = (LinearLayout)convertView;
        }
        ImageView ikona= (ImageView) newView.findViewById(R.id.icon);
        TextView naziv= (TextView) newView.findViewById(R.id.Itemname);
        TextView opis= (TextView) newView.findViewById(R.id.Itemdescription);
        naziv.setText(m.ime+" "+m.prezime);
        opis.setText(m.zanr.toString());
        if(m.zanr==Zanrovi.Pop)
        {id=0;}
        else if(m.zanr==Zanrovi.Rock)
        {id=1;}
        else if(m.zanr==Zanrovi.HipHop)
        {id=2;}
        else if(m.zanr==Zanrovi.Metal)
        {id=3;}
        else if(m.zanr==Zanrovi.Country)
        {id=4;}
        else if(m.zanr==Zanrovi.Jazz)
        {id=5;}
        else if(m.zanr==Zanrovi.Classical)
        {id=6;}
        else if(m.zanr==Zanrovi.Ostalo){id=7;}
        else
        {id=7;}
        ikona.setImageResource(iconid[id]);

        return newView;

    }
}



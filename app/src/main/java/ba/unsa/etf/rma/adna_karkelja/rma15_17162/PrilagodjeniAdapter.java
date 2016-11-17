package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Korisnik on 6.6.2016.
 */
public class PrilagodjeniAdapter  extends ArrayAdapter {
    //Novi adapter, prilagođen za potrebe Zadaće 6
    Context context;
    int layoutResourceId;
    ArrayList<Pretraga> pretrage=null;
    KlasaPretrage podaci = null;

    public PrilagodjeniAdapter(Context context, int layoutResourceId, ArrayList<Pretraga> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.pretrage = data;
    }
    static class KlasaPretrage
    {
        TextView NazivPretrage;
        TextView TipPretrage;
        TextView VrijemePretrage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            podaci = new KlasaPretrage();
            podaci.NazivPretrage = (TextView)row.findViewById(R.id.txtTitlePR);
            podaci.TipPretrage = (TextView)row.findViewById(R.id.txtTitleTIP);
            podaci.VrijemePretrage = (TextView)row.findViewById(R.id.txtTitleVR);


            row.setTag(podaci);
        }
        else
        {
            podaci = (KlasaPretrage)row.getTag();
        }

        Pretraga pretraga = pretrage.get(position);
        podaci.NazivPretrage.setText(" "+pretraga.getNaziv());
        podaci.TipPretrage.setText("  " + pretraga.getTip());
        podaci.VrijemePretrage.setText("  -  "+pretraga.getVrijeme());
        RelativeLayout l=(RelativeLayout)row.findViewById(R.id.prpretraga);
        if(pretraga.getTip().equals("Uspjesna")) l.setBackgroundColor(Color.GREEN);
        if(pretraga.getTip().equals("Neuspjesna")) l.setBackgroundColor(Color.RED);
        return row;
    }

}

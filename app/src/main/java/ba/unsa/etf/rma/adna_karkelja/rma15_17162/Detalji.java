package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Detalji extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.activity_detalji);

        final TextView imeMuzicara= (TextView) findViewById(R.id.dime);
        final TextView prezimeMuzicara= (TextView) findViewById(R.id.dprezime);
        final TextView webMuzicara= (TextView) findViewById(R.id.dweb);
        final TextView zanrMuzicara= (TextView) findViewById(R.id.dzanr);
        //final TextView bioMuzicara= (TextView) findViewById(R.id.dbio);
       // final ListView toppjesme = (ListView) findViewById(R.id.toppjesme);
        Intent i = getIntent();
        imeMuzicara.setText(i.getStringExtra("imeAutora"));
        prezimeMuzicara.setText(i.getStringExtra("prezimeAutora"));
        zanrMuzicara.setText(i.getStringExtra("zanrAutora"));
        webMuzicara.setText(i.getStringExtra("webAutora"));
       // bioMuzicara.setText(i.getStringExtra("bioAutora"));
        final String name= i.getStringExtra("imeAutora");
      //  final String biography=i.getStringExtra("bioAutora");
        final String webpage=i.getStringExtra("webAutora");

       *//* ArrayList<String> toplista = new ArrayList<String>(getIntent().getStringArrayListExtra("listaAutora"));
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,toplista);
        toppjesme.setAdapter(adapter);*//*

       webMuzicara.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String url = webpage;
               Intent i = new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(url));
               startActivity(i);
           }
       });


        *//*toppjesme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i2 = new Intent(Intent.ACTION_SEARCH);
                i2.setPackage("com.google.android.youtube");
                i2.putExtra("query", " " + name.toString() + " " + parent.getItemAtPosition(position).toString());
                i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (i2.resolveActivity(getPackageManager()) != null)
                    startActivity(i2);
                else
                    Toast.makeText(getApplicationContext(), "Greska", Toast.LENGTH_SHORT).show();
            }
        });*//*
        final ImageButton shareMuzicara= (ImageButton) findViewById(R.id.share);
        shareMuzicara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3= new Intent(Intent.ACTION_SEND);
                i3.putExtra(Intent.EXTRA_TEXT, name);
                i3.setType("text/plain");
                startActivity(i3);
            }
        });
       // final Button Top5Pjesama = (Top5Pjesama) findViewById(R.id.button2);
*/



    }


}

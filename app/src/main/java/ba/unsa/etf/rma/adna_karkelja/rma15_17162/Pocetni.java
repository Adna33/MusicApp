package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class Pocetni extends Activity implements FragmentMuzicari.OnItemClick {
    Boolean siriL;
    /*ArrayList<String> toplista = new ArrayList<String>() {{
        add("Love will tear us apart");
        add("Atmosphere");
        add("Disorder");
        add("The eternal");
        add("Shadowplay");
    }};
    ArrayList<String> toplista8 = new ArrayList<String>() {{
        add("How soon is now");
        add("Asleep");
        add("Bigmouth strikes again");
        add("This charming man");
        add("Stop me");
    }};
    ArrayList<String> toplista2 = new ArrayList<String>() {{
        add("To know him is to love him");
        add("Valery");
        add("Rehab");
        add("Stronger than me");
        add("Tears dry on their own");
    }};
    ArrayList<String> toplista3 = new ArrayList<String>() {{
        add("La vie en rose");
        add("What a Wonderful World");
        add("Hello,Dolly!");
        add("When the saints go marching in");
        add("Cheek to cheek");
    }};
    ArrayList<String> toplista4 = new ArrayList<String>() {{
        add("Jolene");
        add("I will always love you");
        add("9 to 5");
        add("Coat of many colors");
        add("He's everything");
    }};
    ArrayList<String> toplista5 = new ArrayList<String>() {{
        add("Window seat");
        add("On & on");
        add("Phone Down");
        add("Bag lady");
        add("Next Lifetime");
    }};
    ArrayList<String> toplista6 = new ArrayList<String>() {{
        add("Romeo and Juliet");
        add("1812 Overture");
        add("Swan Lake");
        add("Serenade for Strings");
        add("Manfred Symphony");
    }};
    ArrayList<String> toplista7 = new ArrayList<String>() {{
        add("Killing in the name");
        add("Calm like a bomb");
        add("Bulls on parade");
        add("Bullet in the head");
        add("Know your enemy");
    }};

    Muzicar prvi=new Muzicar("Ian","Curtis","https://en.wikipedia.org/wiki/Ian_Curtis",Zanrovi.Rock,"He is best known as the lead singer and lyricist of the post-punk band Joy Division",toplista);
    Muzicar osmi=new Muzicar("Steven P.","Morrisey","https://en.wikipedia.org/wiki/The_Smiths",Zanrovi.Rock,"He is best known as the lead singer and lyricist of the post-punk band The Smiths",toplista8);
    Muzicar drugi=new Muzicar("Amy","Winehouse","http://www.amywinehouse.com/",Zanrovi.Pop," The BBC has called her the pre-eminent vocal talent of her generation.",toplista2);
    Muzicar treci=new Muzicar("Louis","Armstrong","https://www.louisarmstronghouse.org/",Zanrovi.Jazz,"One of the pivotal and most influential figures in jazz music.",toplista3);
    Muzicar cetvrti=new Muzicar("Dolly","Parton","http://www.dollyparton.com/",Zanrovi.Country,"Parton is the most honored female country performer of all time. She has composed over 3,000 songs.",toplista4);
    Muzicar peti=new Muzicar("Erykah","Badu","http://www.erykah-badu.com/",Zanrovi.HipHop,"American singer-songwriter, record producer, activist, and actress.",toplista5);
    Muzicar sesti=new Muzicar("Peter","Tchaikovsky","https://en.wikipedia.org/wiki/Pyotr_Ilyich_Tchaikovsky",Zanrovi.Classical,"Tchaikovsky was honored in 1884, by Emperor Alexander III, and awarded a lifetime pension.",toplista6);
    Muzicar sedmi=new Muzicar("Zack","de la Rocha","https://www.ratm.com/",Zanrovi.Metal,"American musician, poet, rapper, and activist best known as the vocalist and lyricist of rap metal band Rage Against the Machine",toplista7);
  */  final ArrayList<Muzicar> lista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetni);
/*        final Button dugme = (Button) findViewById(R.id.button);
        final ListView lv = (ListView) findViewById(R.id.listView);
        final TextView tekst= (TextView) findViewById(R.id.editText);*/

        /*lista.add(prvi);
        lista.add(osmi);
        lista.add(drugi);
        lista.add(treci);
        lista.add(cetvrti);
        lista.add(peti);
        lista.add(sesti);
        lista.add(sedmi);*/


        siriL=false;
        FragmentManager fm = getFragmentManager(); //dohvatanje FragmentManager-a
        FrameLayout ldetalji = (FrameLayout)findViewById(R.id.detalji_muzicar);
        if(ldetalji!=null){//slucaj layouta za široke ekrane
            siriL=true;
            FragmentDetalji fd;
            fd = (FragmentDetalji)fm.findFragmentById(R.id.detalji_muzicar);
            //provjerimo da li je fragment detalji već kreiran
            if(fd==null) {
                //kreiramo novi fragment FragmentDetalji ukoliko već nije kreiran
                fd = new FragmentDetalji();
                fm.beginTransaction().replace(R.id.detalji_muzicar, fd).commit();
            }
        }
//Dodjeljivanje fragmenta FragmentLista
        FragmentLista fl = (FragmentLista)fm.findFragmentByTag("Lista");
//provjerimo da li je već kreiran navedeni fragment
        if(fl==null){
            //ukoliko nije, kreiramo
            fl = new FragmentLista();
            Bundle argumenti=new Bundle();
//unosi su ranije incializirana lista muzičara
//na način kako ste radili na projektnom zadatku 1
            argumenti.putParcelableArrayList("Alista",lista);
            fl.setArguments(argumenti);
            fm.beginTransaction().replace(R.id.muzicari_lista, fl,"Lista").commit();
        }else{
            //slučaj kada mjenjamo orjentaciju uređaja
            //iz portrait (uspravna) u landscape (vodoravna)
            //a u aktivnosti je bio otvoren fragment FragmentDetalji
            //tada je potrebno skinuti FragmentDetalji sa steka
            //kako ne bi bio dodan na mjesto fragmenta FragmentLista
            fm.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
    private IntentFilter filter = new IntentFilter("CONNECTIVITY_CHANGE");
    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, filter);
    }
    @Override
    public void onPause() {
        unregisterReceiver(receiver);
        super.onPause();
    }
    @Override
    public void onItemClicked(int pos) {
        //Priprema novog fragmenta FragmentDetalji
        Bundle arguments = new Bundle();
       // arguments.putParcelableArrayList("muzicari", lista);
        arguments.putParcelable("muzicar", lista.get(pos));
        FragmentDetalji fd = new FragmentDetalji();
        Muzicar m=lista.get(pos);
        fd.setArguments(arguments);
        boolean flag=false;
        MuzicarDBOpenHelper pom = new MuzicarDBOpenHelper(this, "tabela_muzicara", null, 1);
        SQLiteDatabase db = pom.getWritableDatabase();
        String[] koloneRezulat = new String[]{ MuzicarDBOpenHelper.MUZICAR_ID, MuzicarDBOpenHelper.MUZICAR_IME, MuzicarDBOpenHelper.MUZICAR_ZANR, MuzicarDBOpenHelper.MUZICAR_PREZIME, MuzicarDBOpenHelper.MUZICAR_WEB};
        Cursor cursor = db.query(MuzicarDBOpenHelper.DATABASE_TABLE1,
                koloneRezulat, null,
                null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(m.ime) && cursor.getString(3).equals(m.prezime)){
                    flag=true;
                }
            }
            while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
        if(flag==false)pom.insertMuzicar(m.ime, m.prezime, m.getZanr(), m.webStranica, m.pjesme, m.albumi);

        if(siriL){

            //Slučaj za ekrane sa širom dijagonalom
            getFragmentManager().beginTransaction()
                    .replace(R.id.detalji_muzicar, fd)
                    .commit();
        }
        else{

            //Slučaj za ekrane sa početno zadanom širinom
            getFragmentManager().beginTransaction()
                    .replace(R.id.muzicari_lista, fd)
                    .addToBackStack(null)
                    .commit();
            //Primjetite liniju .addToBackStack(null)
        }
    }
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }


}

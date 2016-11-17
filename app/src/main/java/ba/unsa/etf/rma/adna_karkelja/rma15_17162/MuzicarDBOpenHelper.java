package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Korisnik on 5.6.2016.
 */
public class MuzicarDBOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mojaBaza.db";
    public static final String DATABASE_TABLE1 = "Muzicari";
    public static final String DATABASE_TABLE2 = "Pjesme";
    public static final String DATABASE_TABLE3 = "Albumi";
    public static final String DATABASE_TABLE4 = "Pretrage";
    public static final int DATABASE_VERSION = 1;
    public static final String MUZICAR_ID ="muzicarID";
    public static final String MUZICAR_IME ="ime";
    public static final String MUZICAR_PREZIME ="prezime";
    public static final String MUZICAR_ZANR ="zanr";
    public static final String MUZICAR_WEB ="web";
    public static final String ALBUM_ID ="albumID";
    public static final String ALBUM_NAZIV ="naziv";
    public static final String ALBUM_MUZICAR_ID ="muzicar_id";
    public static final String PJESMA_ID ="pjesmaID";
    public static final String PJESMA_NAZIV ="naziv";
    public static final String PJESMA_MUZICAR_ID ="muzicar_id";
    public static final String PRETRAGA_ID="pretragaID";
    public static final String PRETRAGA_NAZIV ="naziv";
    public static final String PRETRAGA_VRIJEME ="vrijeme";
    public static final String PRETRAGA_TIP ="tip";


// SQL upit za kreiranje baze

    //TABELA MUZICARI
    private static final String DATABASE_CREATE1 = "create table " +
            DATABASE_TABLE1 + " (" + MUZICAR_ID +
            " integer primary key autoincrement, " +
            MUZICAR_IME + " text not null, " + MUZICAR_PREZIME + " text not null, " +
            MUZICAR_ZANR + " text not null, "+MUZICAR_WEB+" text not null);";
    //TABELA ALBUMI
    private static final String DATABASE_CREATE2 = "create table " +
            DATABASE_TABLE2 + " (" + ALBUM_ID +
            " integer primary key autoincrement, " +
            ALBUM_NAZIV + " text not null, " + ALBUM_MUZICAR_ID +" integer not null);";
    //TABELA PJESME
    private static final String DATABASE_CREATE3 = "create table " +
            DATABASE_TABLE3 + " (" + PJESMA_ID +
            " integer primary key autoincrement, " +
            PJESMA_NAZIV + " text not null, " + PJESMA_MUZICAR_ID +" integer not null);";
    //TABELA PRETRAGE
    private static final String DATABASE_CREATE4 = "create table " +
            DATABASE_TABLE4 + " (" + PRETRAGA_ID +
            " integer primary key autoincrement, " +
            PRETRAGA_NAZIV + " text not null, "+
            PRETRAGA_VRIJEME + " text not null, " +
            PRETRAGA_TIP + " text not null);";

    public MuzicarDBOpenHelper(Context context, String name,
                               SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Poziva se kada ne postoji baza
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE1);
        db.execSQL(DATABASE_CREATE2);
        db.execSQL(DATABASE_CREATE3);
        db.execSQL(DATABASE_CREATE4);

    }

    // Poziva se kada se ne poklapaju verzije baze na disku i trenutne baze
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Brisanje stare verzije
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE1);
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE2);
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE3);
        db.execSQL("DROP TABLE IF IT EXISTS" + DATABASE_TABLE4);
        // Kreiranje nove
        onCreate(db);
    }

    //FUNKCIJA ZA UNOS NOVE PRETRAGE U BAZU
    public void insertPretraga(String naz, String vr, String t){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+DATABASE_TABLE4+" ("+PRETRAGA_NAZIV+","+PRETRAGA_VRIJEME+","+PRETRAGA_TIP+") VALUES ('"+naz+"' , '"+vr+"', '"+t+"');");
    }

    //FUNKCIJA ZA UNOS NOVOG MUZIČARA U BAZU
    public void insertMuzicar(String ime, String prezime, String zanr, String web, ArrayList<String> pjesme, ArrayList<String>albumi)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO "+DATABASE_TABLE1+" ("+MUZICAR_IME+","+MUZICAR_ZANR+","+MUZICAR_PREZIME+","+MUZICAR_WEB+") VALUES ('"+ime+"', '"+zanr+"' ,'"+prezime+"', '"+web+"');");
        int id=0;

        //PROVJERA DA LI VEC POSTOJI MUZICAR U BAZI
        String[] koloneRezulat = new String[]{ MuzicarDBOpenHelper.MUZICAR_ID, MuzicarDBOpenHelper.MUZICAR_IME, MuzicarDBOpenHelper.MUZICAR_ZANR, MuzicarDBOpenHelper.MUZICAR_PREZIME, MuzicarDBOpenHelper.MUZICAR_WEB};
        Cursor cursor = db.query(MuzicarDBOpenHelper.DATABASE_TABLE1,
                koloneRezulat, null,
                null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(ime) && cursor.getString(3).equals(prezime)){
                    id=cursor.getInt(0);
                }
            }
            while(cursor.moveToNext());
        }
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }


        for (String s: albumi) {
            s=s.replace("'", " ");
            db.execSQL("INSERT INTO "+DATABASE_TABLE2+" ("+ALBUM_NAZIV+","+ALBUM_MUZICAR_ID+") VALUES ('"+s+"' , "+id+");");
        }

        for (String s: pjesme) {
            s=s.replace("'", " ");
            db.execSQL("INSERT INTO "+DATABASE_TABLE3+" ("+PJESMA_NAZIV+","+PJESMA_MUZICAR_ID+") VALUES ('"+s+"' , "+id+");");
        }
    }
}

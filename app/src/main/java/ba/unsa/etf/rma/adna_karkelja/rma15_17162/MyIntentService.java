package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Korisnik on 16.5.2016.
 */
public class MyIntentService extends IntentService {
    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    public MyIntentService() {
        super(null);
    }

    public MyIntentService(String name) {
        super(name);
        // Sav posao koji treba da obavi konstruktor treba da se
        // nalazi ovdje
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Akcije koje se trebaju obaviti pri kreiranju servisa
    }
    Muzicar m;
    ArrayList<Muzicar> rez=new ArrayList<>();
    @Override
    protected void onHandleIntent(Intent intent) {
        // Kod koji se nalazi ovdje će se izvršavati u posebnoj niti
        // Ovdje treba da se nalazi funkcionalnost servisa koja je
        // vremenski zahtjevna

        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        Bundle bundle = new Bundle();

        /* Update UI: Početak taska */
        receiver.send(STATUS_RUNNING, Bundle.EMPTY);


        String query = null;
        query=intent.getStringExtra("ime");

        String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
        try {
        /* Proslijedi rezultate nazad u pozivatelja */
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONObject artists = jo.getJSONObject("artists");
            JSONArray items = artists.getJSONArray("items");
            String genres="pop";
            //Samo prvih 5 rezultata ukoliko ih ima vise
            //for (int i = 0; i < items.length(); i++) {
            for (int i = 0; i < items.length() && i <= 5; i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String artist_ID = artist.getString("id");
                //String genres="pop";
                JSONArray niz = artist.getJSONArray("genres");
                JSONObject webs = artist.getJSONObject("external_urls");
                String www =webs.getString("spotify");
                // genres="pop";
                if (niz.length()!=0) genres=niz.getString(0);
                else genres="ostalo";


                //Ovdje trebate dodati kreiranje objekta Muzičara i dodavanje u listu
                //Ovo uradite na sličan način kako ste radili i kada ste hardkodirali podatke
                // samo što sada koristite stvarne podatke
                m= new Muzicar(name,genres,www);
                DobaviAlbume(artist_ID);
                DobaviPjesme(artist_ID);
                rez.add(m);
                //rez.add(new Muzicar(name, "", "webpage", "Zanr", "biografija", new ArrayList<>(Arrays.asList("pjesma1", "pjesma2", "pjesma3"))));


            }
            bundle.putParcelableArrayList("result", rez);
            receiver.send(STATUS_FINISHED, bundle);
        }
        catch (ClassCastException e) {
        /* Vrati obavijest da je došlo do izuzetka, e – izuzetak */
            bundle.putString(Intent.EXTRA_TEXT, e.toString());
            receiver.send(STATUS_ERROR, bundle);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return sb.toString();
    }
    Void DobaviPjesme(String... params)
    {
        try {
            String  query = URLEncoder.encode(params[0], "utf-8");
            URL url = null;
            String url1 = "https://api.spotify.com/v1/artists/" + query + "/top-tracks?country=US";
            try {
                url = new URL(url1);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("tracks");
            for (int i = 0; i < items.length() && i<5; i++) {
                JSONObject song = items.getJSONObject(i);
                String name = song.getString("name");
                m.pjesme.add(name);

            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    Void DobaviAlbume(String... params)
    {
        try {
            String  query = URLEncoder.encode(params[0], "utf-8");

            URL url = null;
            String url1 = "https://api.spotify.com/v1/artists/" + query + "/albums?market=us&limit=5";
            try {
                url = new URL(url1);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONArray items = jo.getJSONArray("items");


            for (int i = 0; i < items.length(); i++) {
                JSONObject album = items.getJSONObject(i);
                String name = album.getString("name");
                JSONObject webs = album.getJSONObject("external_urls");
                String web =webs.getString("spotify");
                m.muzicaralbumi.add(web);
                m.albumi.add(name);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
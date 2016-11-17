package ba.unsa.etf.rma.adna_karkelja.rma15_17162;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by Korisnik on 9.5.2016.
 */
public class SearchArtist extends AsyncTask<String, Integer, Void> {

    public interface OnMuzicarSearchDone{
        public  void onDone (ArrayList<Muzicar> rez);
    }
    Muzicar muz;

    ArrayList<Muzicar> rez=new ArrayList<>();
    private OnMuzicarSearchDone pozivatelj;
    public SearchArtist(OnMuzicarSearchDone p) { pozivatelj = p; }

    @Override
    protected Void doInBackground(String... params) {
        String query = null;
        try {
            query = URLEncoder.encode(params[0], "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url1 = "https://api.spotify.com/v1/search?q=" + query + "&type=artist";
        try {
            URL url = new URL(url1);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String rezultat = convertStreamToString(in);
            JSONObject jo = new JSONObject(rezultat);
            JSONObject artists = jo.getJSONObject("artists");
            JSONArray items = artists.getJSONArray("items");
            //Samo prvih 5 rezultata
            //for (int i = 0; i < items.length(); i++) {
            for (int i = 0; i < items.length() && i<=5; i++) {
                JSONObject artist = items.getJSONObject(i);
                String name = artist.getString("name");
                String artist_ID = artist.getString("id");
                JSONArray niz = artist.getJSONArray("genres");
                JSONObject webs = artist.getJSONObject("external_urls");
                String www =webs.getString("spotify");
                String genres="pop";
                if (niz.length()!=0)genres=niz.getString(0);
                //Ovdje trebate dodati kreiranje objekta Muzičara i dodavanje u listu
                //Ovo uradite na sličan način kako ste radili i kada ste hardkodirali podatke
                // samo što sada koristite stvarne podatke
                muz=new Muzicar(name,genres,www);
                DobaviAlbume(artist_ID);
                DobaviPjesme(artist_ID);
                rez.add(muz);
               // rez.add( new Muzicar(name,"", "webpage", Zanrovi.Pop, "biografija",new ArrayList<>(Arrays.asList("pjesma1", "pjesma2", "pjesma3"))));


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    } protected  void onPostExecute (Void aVoid)
    {
        super.onPostExecute(aVoid);
        pozivatelj.onDone(rez);
    }
    //funkcija koja će pretvoriti InputStream u string.
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
                muz.pjesme.add(name);

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
                muz.muzicaralbumi.add(web);
                muz.albumi.add(name);
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

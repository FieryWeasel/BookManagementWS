package com.lp.bookmanager.tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by iem on 12/12/14.
 */
public class JsonLoader {

    public static InputStream loadFile(String urlString){
        InputStream inuputStream = null;
        try {
            URL url = new URL(urlString);

            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //and connect!
            urlConnection.connect();

            //this will be used in reading the data from the internet
            inuputStream = urlConnection.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inuputStream != null)
            return inuputStream;
        else
            return null;
    }

    public static String loadJson(String method, String urlString) throws IOException {
        String result = null;
        BufferedReader reader = null;
        try {

            URL url = new URL(urlString);

            Log.d("JsonLoader", url.toString());
            Log.d("JsonLoader", "opening connection");
            //create the new connection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            //set up some things on the connection
            urlConnection.setRequestMethod(method);
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(15*1000);
            //and connect!

            Log.d("JsonLoader", "Connecting");
            try{
                urlConnection.connect();
            }catch (IOException e){
                e.printStackTrace();
            }
            Log.d("JsonLoader", "Connected");

            //this will be used in reading the data from the internet
            Log.d("JsonLoader", "getInputStream");

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
                Log.d("JsonLoader", line);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                reader.close();
            }
        }
        if(result != null)
            return result;
        else
            return "Error";
    }

}

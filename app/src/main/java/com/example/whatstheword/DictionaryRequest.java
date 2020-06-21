package com.example.whatstheword;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DictionaryRequest extends AsyncTask<String,Integer,String>
{
    final String app_id = "094a1a62";
    final String app_key = "e523ceccaa32bcedd31422132ee3482c";
    String myUrl;
    Context context;
    TextView defBox;
    EditText enterWord;

    DictionaryRequest(Context context)
    {
        this.context = context;
    }

    DictionaryRequest(Context context, TextView defBox)
    {
        this.context = context;
        this.defBox = defBox;
    }

    @Override
    protected String doInBackground(String... params)
    {
        myUrl = params[0];
        try {
            URL url = new URL(myUrl);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        String definition;

        //Toast values for unsuccessful search
        final CharSequence text = "Unable to find word";
        final int duration = Toast.LENGTH_SHORT;

        //TODO: Create JSON objects to be able to read and parse responses from Oxford API
        try
        {
            //Go through the JSON tree to get to definitions
            JSONObject js = new JSONObject(s);
            JSONArray results = js.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray laArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = laArray.getJSONObject(0);
            JSONArray e = entries.getJSONArray("entries");

            JSONObject js2 = e.getJSONObject(0);
            JSONArray sensesArray = js2.getJSONArray("senses");

            JSONObject js3 = sensesArray.getJSONObject(0);
            JSONArray definitions = js3.getJSONArray("definitions");

            definition = definitions.optString(0);
            defBox.setText(definition);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(context,text,duration).show();
        }
    }
}
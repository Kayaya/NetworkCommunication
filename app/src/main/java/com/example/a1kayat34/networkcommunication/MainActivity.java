package com.example.a1kayat34.networkcommunication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity implements View.OnClickListener {

    class SongAsyncTask extends AsyncTask<String, Void, String> {
        private static final int RESPONSE_OK = 200;

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection conn = null;
            try {
                //Before the change
                //URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=" + strings[0]);
                URL url = new URL(strings[0]+ "?artist="+strings[1]+"&format=json");
                conn = (HttpURLConnection) url.openConnection();


                InputStream in = conn.getInputStream();
                //if connection response code OK
                if (conn.getResponseCode() == RESPONSE_OK) {
                    //start reading into string
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String json = "";
                    String Currentline = null;

                    String result = "";


                    while ((Currentline = br.readLine()) != null)
                        json += Currentline;
                        try {
                            JSONArray jsonArr = new JSONArray(json);
                            String song, artist, month;
                            String year, chart, ID, quantity;

                            //TextView tv = (TextView) findViewById(R.id.results);


                            for (int i=0; i<jsonArr.length(); i++){
                                JSONObject currObject = jsonArr.getJSONObject(i);
                                song = currObject.getString("song");
                                artist = currObject.getString("artist");
                                year = currObject.getString("year");
                                month = currObject.getString("month");
                                chart = currObject.getString("chart");
                                ID = currObject.getString("ID");
                                quantity = currObject.getString("quantity");

                                result += "ID:= " + ID + " Song= "+ song + " Artist = " + artist + " Year= " + year + " Month = " + month +" Chart = " + chart + " Quantity = " + quantity + "\n";

                            }


                        }
                        catch (JSONException e){
                            return e.toString();

                        }



                    return result;
                } else
                    return "HTTP ERROR: " + conn.getResponseCode();


            } catch (IOException e) {
                return "Error" + e.toString();
            }

        }
        @Override
        public void onPostExecute(String result) {
            TextView et1 = (TextView)findViewById(R.id.results);
            et1.setText(result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = "";
        //Button
        Button go = (Button) findViewById(R.id.go);
        go.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        /*MyTask t = new MyTask();
        t.execute();*/

        EditText urlET = (EditText)findViewById(R.id.url);

        String url = urlET.getText().toString();

        EditText artistEditText = (EditText) findViewById(R.id.search);
        String artist = artistEditText.getText().toString();

        SongAsyncTask task = new SongAsyncTask();
        task.execute(url, artist);
    }
}

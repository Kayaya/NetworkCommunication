package com.example.a1kayat34.networkcommunication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
                URL url = new URL(strings[0]+ "?artist="+strings[1]);
                conn = (HttpURLConnection) url.openConnection();


                InputStream in = conn.getInputStream();
                //if connection response code OK
                if (conn.getResponseCode() == RESPONSE_OK) {
                    //start reading into string
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = null;
                    String Currentline = null;
                    while ((Currentline = br.readLine()) != null)
                        result += Currentline;
                    return result;
                } else
                    return "HTTP ERROR: " + conn.getResponseCode();


            } catch (IOException e) {
                return "Error" + e.toString();
            }

            //return null;
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

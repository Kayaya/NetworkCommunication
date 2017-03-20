package com.example.a1kayat34.networkcommunication;

import android.app.Activity;
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

public class MainActivity extends Activity implements View.OnClickListener{

    class MyTask extends AsyncTask<Void, Void, String>{
        public String doInBackground(Void... unused){
            HttpURLConnection conn = null;
            try{
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=Oasis");
                conn = (HttpURLConnection) url.openConnection();

                //EditText = (EditText) findViewById(R.id.search);

                InputStream in = conn.getInputStream();
                if(conn.getResponseCode() == 200)
                {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while((line = br.readLine()) !=null)
                        result += line;
                    return result;
                }
                else
                    return "HTTP ERROR: " + conn.getResponseCode();

            }
            catch (IOException e){
                return e.toString();
            }
            finally
            {
                if(conn!=null)
                    conn.disconnect();
            }

        }
        public void onPostExecute(String result)
        {
            TextView et1 = (TextView) findViewById(R.id.results);
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
        MyTask t = new MyTask();
        t.execute();
    }
}

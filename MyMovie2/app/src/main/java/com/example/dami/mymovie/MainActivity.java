package com.example.dami.mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    TextView tvActor, tvDirec, tvPlot, tvReleased, tvGenre = null;
    EditText etTitle = null;
    ImageView isPoster = null;
    Button btnSearch = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTitle = (EditText) findViewById(R.id.etTitle);
        isPoster = (ImageView) findViewById(R.id.isPoster);
        btnSearch = (Button) findViewById(R.id.btnSearch);

        tvDirec = (TextView) findViewById(R.id.tvDirec);
        tvReleased = (TextView) findViewById(R.id.tvReleased);
        tvGenre = (TextView) findViewById(R.id.tvGenre);
        tvActor = (TextView) findViewById(R.id.tvActor);
        tvPlot = (TextView) findViewById(R.id.tvPlot);

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(etTitle.getText() != null && !etTitle.getText().toString().isEmpty()){
                    MySearchTask st = new MySearchTask();
                    st.execute(etTitle.getText().toString());
                } else{
                    Toast.makeText(getApplicationContext(),"타이틀을 입력하시오.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class MySearchTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        public JSONObject doInBackground(String... values) {
            try{
                String params = "http://www.omdbapi.com/?apikey=a07bf0e6&t="+values[0];
                URL url = new URL(params);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                //conn.getOutputStream().write(params.getBytes("UTF-8"));

                JSONObject jo = null;
                String line = null;
                StringBuilder sb = new StringBuilder();
                int responseCode = conn.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK) {
                    // 읽어들이기
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); // 저장
                    // 한 줄 씩 읽을 수 있음
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    jo = new JSONObject(sb.toString());
                    return jo;
                }
            } catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject jo) {
           try {
               tvReleased.setText((jo.get("Released")).toString());
               tvGenre.setText((jo.get("Genre")).toString());
               tvDirec.setText((jo.get("Director")).toString());
               tvActor.setText((jo.get("Actors")).toString());
               tvPlot.setText((jo.get("Plot")).toString());

               ImageTask it = new ImageTask();
               it.execute((jo.get("Poster")).toString());
           } catch (Exception e){
               e.printStackTrace();
           }
            super.onPostExecute(jo);
        }
    }

    class ImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected void onPostExecute(Bitmap bm) {
            isPoster.setImageBitmap(bm);
            super.onPostExecute(bm);
        }

        @Override
        protected Bitmap doInBackground(String... values) {
           try {
               URL imgUrl = new URL(values[0]);
               InputStream is = imgUrl.openStream();
               Bitmap bm = BitmapFactory.decodeStream(is);
               return bm;
           } catch(Exception e){
               e.printStackTrace();
           }
            return null;
        }
    }
}

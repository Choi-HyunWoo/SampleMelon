package com.hw.corcow.samplemelon;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Song> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        listView = (ListView)findViewById(R.id.listView);
        mAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);

        Button btn = (Button) findViewById(R.id.btn_melon);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MelonTask().execute(1, 50);
            }
        });
    }

    public static final String MELON_URL = "http://apis.skplanetx.com/melon/charts/realtime?count=%s&page=%s&version=1";        // count, page (Query parameter)를 %s로
    class MelonTask extends AsyncTask<Integer, Integer, String> {       // page, counter, progress(X), ...
        @Override
        protected String doInBackground(Integer... params) {
            int page = params[0];
            int count = params[1];
            String urlText = String.format(MELON_URL, count, page);
            try {
                URL url = new URL(urlText);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();      // 세팅
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("appKey", "78285bcd-90e2-30d7-89b7-0a14594673d6");
                int code = conn.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {                                // 연결되었다면
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line=br.readLine()) != null) {
                        sb.append(line).append("\n\r");
                    }
                    return sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        /* 데이터를 얻어오고 나면 Parsing을 ... */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {

                // Normal Parsing
//                Toast.makeText(MainActivity.this, "data : " + s , Toast.LENGTH_SHORT).show();
//                try {
//                    JSONObject obj = new JSONObject(s);
                  /** 결과값을 얻기 위해 Parsing 결과를 구조화한 MelonResult class를 만들었음. **/
//                    MelonResult result = new MelonResult();
                /** OOP 하자 >> 객체 본인이 Parse 데이터를 가져올 수 있도록 우린 JSONObject만 MelonResult에게 넘겨주면 됨
                 *  객체가 받은 JSONObject를 통해 parse할 수 있도록 parsing method를 정의한 JSONParsing Interface를 만들자.
                 *  각각의 Parsing할 데이터 클래스에 만든 interface를 implements하고, parsing method를 Override하면서 Parsing내용을 추가하자.
                 */
//                    result.parsing(obj);
//                    for (Song songList : result.melon.songs.songList) {
//                        mAdapter.add(songList);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                // GSON library Parsing
                Gson gson = new Gson();         // Gson 라이브러리 사용!
                MelonResult result = gson.fromJson(s, MelonResult.class);
                for (Song song : result.melon.songs.song) {
                    mAdapter.add(song);
                }
            } else {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

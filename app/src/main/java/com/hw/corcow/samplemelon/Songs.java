package com.hw.corcow.samplemelon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-19.
 */
public class Songs  implements JSONParsing  {
    List<Song> song;

    @Override
    public void parsing(JSONObject jobject)  throws JSONException {
        song = new ArrayList<Song>();
        JSONArray array = jobject.getJSONArray("songList");
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsong = array.getJSONObject(i);
            Song s = new Song();
            s.parsing(jsong);
            song.add(s);
        }
    }
}

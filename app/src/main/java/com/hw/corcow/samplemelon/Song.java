package com.hw.corcow.samplemelon;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tacademy on 2015-10-19.
 */
public class Song implements JSONParsing {
    int songId;
    String songName;
    int albumId;
    String albumName;
    int currentRank;

    @Override
    public void parsing(JSONObject jobject) throws JSONException {
        songId = jobject.getInt("songId");
        songName = jobject.getString("songName");
        albumId = jobject.getInt("albumId");
        albumName = jobject.getString("albumName");
        currentRank = jobject.getInt("currentRank");
    }

    @Override
    public String toString() {
        return "(" + currentRank +") " + songName ;
    }
}

package com.hw.corcow.samplemelon;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tacademy on 2015-10-19.
 */
public class MelonResult implements JSONParsing {
    // Parsing 한 데이터를 넣을 클래스를 정의 (클래스 이름은 상관없지만 멤버변수 이름은 같게 만들 것 >> GSON 라이브러리 사용을 위해)
    Melon melon;

    @Override
    public void parsing(JSONObject jobject)  throws JSONException {
        melon = new Melon();
        JSONObject jmelon = jobject.getJSONObject("melon");
        melon.parsing(jmelon);
    }
}

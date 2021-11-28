package com.example.mobiledevtest;


import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HandlerTest extends TestCase {

    public void testHttpServiceCall() {
        String input = MainActivity.URL;
        boolean output;
        boolean expect = true;

        Handler handler = new Handler();
        output = testJson(handler.httpServiceCall(input));

        assertEquals(expect,output);
    }

    private boolean testJson(String test){
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;

    }

}
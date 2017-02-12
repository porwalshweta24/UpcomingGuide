package com.android.upcomingguide.Utility;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 */

public class JsonDataParser {

    public static <T> T getInternalParser(String response, Type type){

        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}

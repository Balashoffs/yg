package org.example.utils;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    public static String toJson(Object object, Class<?> main, Class<?>... subs) {
        Type type = TypeToken.getParameterized(main, subs).getType();
        return new Gson().toJson(object, type);
    }

    public static <T> T fromJson(String json, Class<?> main, Class<?>... subs) {
        Type type = TypeToken.getParameterized(main, subs).getType();
        return new Gson().fromJson(json, type);
    }
}
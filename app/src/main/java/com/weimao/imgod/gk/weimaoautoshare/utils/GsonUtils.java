package com.weimao.imgod.gk.weimaoautoshare.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * gson 工具类
 */
public class GsonUtils {
    private volatile static Gson gson;

    private GsonUtils() {
    }

    public static Gson getGson() {
        if (null == gson) { //检查
            synchronized (GsonUtils.class) {
                if (null == gson) { //又检查一次
                    gson = new Gson();
                }
            }
        }
        return gson;
    }

    /**
     * 将对象转成json字符串
     *
     * @param obj
     * @return
     */
    public static String gsonToString(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * json字符串转成实体类
     *
     * @param json
     * @param token new TypeToken<T>() {}
     * @return
     */
    public static <T> T stringToGson(String json, TypeToken<T> token) {
        return new Gson().fromJson(json, token.getType());
    }

}

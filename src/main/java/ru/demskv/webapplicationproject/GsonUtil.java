
package ru.demskv.webapplicationproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.google.gson.Gson;


public class GsonUtil {
    private static ObjectMapper gson;
    public static ObjectMapper getInstance(){
        if(gson == null){
            gson = new ObjectMapper();
            gson.registerModule(new JavaTimeModule());
            gson.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return gson;
    }
}

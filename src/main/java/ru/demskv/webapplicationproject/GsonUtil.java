
package ru.demskv.webapplicationproject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static String tojson(Object o){
        try {
            return getInstance().writeValueAsString(o);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(GsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}

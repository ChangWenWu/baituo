package tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Gson_tool {

   public static String CreateGsonString(Object value){
	   Gson gson = new Gson();
	   String str= gson.toJson(value);
	   return str;
   }
   public static <T> List<T> GetMessage(String jsonString,Class<T> cls){	
		 List<T> list = new ArrayList<T>();
	     try {
			Gson gson = new Gson();
			list = gson.fromJson(jsonString, new TypeToken<List<T>>(){}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}	 
		 return list;
	 }
   public static <K,T> Map<K,T> GetMessage(String jsonString,Class<K> cls,Class<T> cls1){	
		Map<K,T> map =  new HashMap<K, T>();
	     try {
			Gson gson = new Gson();
			map = gson.fromJson(jsonString, new TypeToken<Map<K,T>>(){}.getType());
		} catch (Exception e) {
			// TODO: handle exception
		}	 
		 return map;
	 }
 
}

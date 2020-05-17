package com.example.setup;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSON {
    
    public static JSONObject createJSONObject(JSONArray historyEntries){
        
        JSONObject requestJson = new JSONObject();
        requestJson.put("history", historyEntries);
        
        return requestJson;
    }
    
}

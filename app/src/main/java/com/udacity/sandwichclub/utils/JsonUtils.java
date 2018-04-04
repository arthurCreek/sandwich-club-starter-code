package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = sandwichObject.getJSONObject("name");
            String mainNameString = nameObject.getString("mainName");
            JSONArray alsoKnownAsArray = nameObject.optJSONArray("alsoKnownAs");
            int arrayLength = alsoKnownAsArray.length();
            List<String> alsoKnownAslist = new ArrayList<String>();
            if (arrayLength != 0){
                for(int i = 0; i < arrayLength; i++){
                    alsoKnownAslist.add(alsoKnownAsArray.getString(i));
                }
            }
            Log.v("JsonUtils.this", alsoKnownAslist.toString());

            String placeOfOriginString = sandwichObject.optString("placeOfOrigin");
            if (placeOfOriginString.equals("")){
                placeOfOriginString = "Null";
            }

            String descriptionString = sandwichObject.optString("description");

            String imageString = sandwichObject.optString("image");

            JSONArray ingredientArray = sandwichObject.getJSONArray("ingredients");
            int ingredientArrayLength = ingredientArray.length();
            List<String> ingredientList = new ArrayList<String>();
            if (ingredientArrayLength != 0){
                for (int j = 0; j < ingredientArrayLength; j++){
                    ingredientList.add(ingredientArray.getString(j));
                }
            }

            return new Sandwich(mainNameString, alsoKnownAslist, placeOfOriginString, descriptionString, imageString, ingredientList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

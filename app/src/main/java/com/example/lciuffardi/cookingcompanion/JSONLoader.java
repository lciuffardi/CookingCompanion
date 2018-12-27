package com.example.lciuffardi.cookingcompanion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Luigi Ciuffardi on 12/26/2018.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */

public class JSONLoader extends AppCompatActivity {

    /** loadRecipesJSON - Loads data from recipe JSON file into a string to be parsed.
     *
     * @param context
     * @return
     */
    public static String loadRecipesJSON(Context context){
        String json = null;
        try {
            InputStream input = context.getAssets().open("json/RecipeDataLookUp.json");
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}

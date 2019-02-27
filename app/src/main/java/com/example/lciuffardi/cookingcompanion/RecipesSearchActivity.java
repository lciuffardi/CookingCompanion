package com.example.lciuffardi.cookingcompanion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Luigi Ciuffardi on 10/1/2017.
 * Last updated by Luigi Ciuffardi on 2/26/2019.
 */

public class RecipesSearchActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = RecipesSearchActivity.class.getName();
    private boolean inCategory = false;
    private ListView listView;
    private Map<String, String> recipeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categorySelection();

        Button surpriseMeButton = (Button) findViewById(R.id.surprise_me_button);

        surpriseMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomRecipe(recipeMap);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_layout_menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                return true;
            case R.id.action_back:
                inCategory = false;
                categorySelection();
                return true;
            case R.id.surprise_me_button:
                randomRecipe(recipeMap);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (inCategory) {
            menu.findItem(R.id.action_back).setVisible(true);
        } else {
            menu.findItem(R.id.action_back).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    protected void categorySelection(){
        Bundle extras = getIntent().getExtras();
        String selection = extras.getString("selection");

        inCategory = false;

        invalidateOptionsMenu();
        ActionBar actionbar = this.getSupportActionBar();
        actionbar.setTitle(selection);

        setContentView(R.layout.recipe_list);

        this.listView = (ListView) findViewById(R.id.recipes_listView);
        recipeMap = populateRecipeMap(findRecipeList(selection));
        showRecipes(recipeMap);

    }

    /** findRecipeList - Finds and loads the recipes for the category selected by the user into
     *                   a JSONArray.
     *
     * @param selection
     */
    private JSONArray findRecipeList(String selection){
        JSONObject recipesJSON = null;
        JSONArray listArr = null;
        try {
            Log.d(TAG, "Loading " + selection + " Recipes...");
            recipesJSON = new JSONObject(JSONLoader.loadRecipesJSON(getApplicationContext()));
            listArr = recipesJSON.optJSONArray(selection);
            Log.d(TAG, selection + " Recipes have been loaded...");
        } catch(JSONException e){
            Log.d(TAG, "Error loading " + selection + " Recipes...");
            e.printStackTrace();
        }
        return listArr;
    }

    /** populateRecipeMap - Populates a map containing recipe names their corresponding URLs for the
     *                      category selected by the user.
     *
     * @param listArr
     * @return recipeMap
     */
    protected static Map<String, String> populateRecipeMap(JSONArray listArr){
        Map<String, String> recipeMap = new HashMap<>();
        try {
            for(int i = 0; i < listArr.length(); i++){
                JSONObject listObj = listArr.getJSONObject(i);
                recipeMap.put(listObj.getString("name"), listObj.getString("url"));
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeMap;
    }

    /** showRecipes - Shows the recipes for a category in a list when selected by user.
     *
     * @param recipeMap
     */
    private void showRecipes(final Map<String, String> recipeMap) {
        List<String> recipeList = new ArrayList<>(recipeMap.keySet());

        ArrayAdapter<String> recipeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, recipeList);

        listView.setAdapter(recipeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?>adapter,View v, int position, long id){
                Object o = listView.getItemAtPosition(position);
                openRecipe(o);
            }
        });
    }

    /** randomRecipe - Surprise Me functionality; Selects a random recipe using random number
     *                  generator.
     *
     * @param recipeMap
     */
    private void randomRecipe(final Map<String, String> recipeMap){
        List<String> recipeList = new ArrayList<>(recipeMap.keySet());
        Random r = new Random();

        Object o = listView.getItemAtPosition(r.nextInt(recipeList.size()));

        openRecipe(o);
    }

    /** openRecipe - Opens the selected recipe in WebView Activity.
     *
     * @param recipeObj
     */
    private void openRecipe(Object recipeObj){
        Log.d(TAG, "Opening Recipe: " + recipeObj.toString());
        String url = recipeMap.get(recipeObj);

        Intent intent = new Intent(
                getApplicationContext(),
                RecipeDetails.class);
        intent.putExtra("url", url);
        intent.putExtra("name", recipeObj.toString());
        startActivity(intent);
    }
}

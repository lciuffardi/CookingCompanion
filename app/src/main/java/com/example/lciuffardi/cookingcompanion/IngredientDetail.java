package com.example.lciuffardi.cookingcompanion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Luigi Ciuffardi on 9/30/2017.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */

public class IngredientDetail extends AppCompatActivity {

    long ingredientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showViewIngredientsDetails();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_ingredients_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_modify:
                Intent intent = new Intent(
                        getApplicationContext(),
                        ModifyIngredientData.class);
                intent.putExtra("id", ingredientID);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_delete:
                deleteIngredient();
                return true;
            case R.id.action_close:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** showViewIngredientsDetails - Displays Ingredient data to user.
     *
     */
    public void showViewIngredientsDetails(){
        setContentView(R.layout.ingredients_data);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ingredientID = extras.getLong("id");
            IngredientsDatabaseManager dbMgr = new IngredientsDatabaseManager(this);
            Ingredient ingredient = dbMgr.getIngredient(ingredientID);
            if (ingredient != null) {
                ((TextView) findViewById(R.id.ingredient_name_data_textView))
                        .setText(ingredient.getName());
                ((TextView) findViewById(R.id.ingredient_quantity_number_data_textView))
                        .setText(ingredient.getQuantityNumber());
                ((TextView) findViewById(R.id.ingredient_quantity_measurement_data_textView))
                        .setText(ingredient.getQuantityUnitOfMeasurement());
                ((TextView) findViewById(R.id.ingredient_expiration_data_textView))
                        .setText(ingredient.getExpiration());
                ((TextView) findViewById(R.id.ingredient_special_notes_data_textView))
                        .setText(ingredient.getSpecialNotes());
            } else {
                Log.d("db", "pet null");
            }
        }
    }

    /** deleteIngredient - Removes Ingredient from Ingredient Database
     *
     */
    private void deleteIngredient() {
        IngredientsDatabaseManager dbMgr = new IngredientsDatabaseManager(this);
        Ingredient ingredient = dbMgr.getIngredient(ingredientID);
        new AlertDialog.Builder(this)
                .setTitle("Delete " + ingredient.getName() + "?")
                .setMessage(
                        "Are you sure?")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int whichButton) {
                                IngredientsDatabaseManager dbMgr =
                                        new IngredientsDatabaseManager(
                                                getApplicationContext());
                                dbMgr.deleteIngredient(ingredientID);
                                dialog.dismiss();
                                finish();
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog,
                                    int which) {
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();


    }
}

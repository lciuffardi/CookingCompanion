package com.example.lciuffardi.cookingcompanion;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Luigi Ciuffardi on 10/1/2017.
 * Last updated by Luigi Ciuffardi on 12/27/2018.
 */

public class ModifyIngredientData extends AppCompatActivity implements View.OnClickListener{
    long ingredientID;
    private EditText expirationEntry;
    private DatePickerDialog datePicker;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredients_input_data);

        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        expirationEntry = (EditText) findViewById(R.id.ingredient_expiration_editText);
        expirationEntry.setInputType(InputType.TYPE_NULL);
        expirationEntry.requestFocus();

        setDateTimeField();

        Bundle extras = getIntent().getExtras();
        ingredientID = extras.getLong("id");

        IngredientsDatabaseManager dbMgr = new IngredientsDatabaseManager(this);
        Ingredient ingredient = dbMgr.getIngredient(ingredientID);
        ((EditText) findViewById(R.id.ingredient_name_editText))
                .setText(ingredient.getName());
        ((EditText) findViewById(R.id.ingredient_quantity_number_editText))
                .setText(ingredient.getQuantityNumber());
        ((EditText) findViewById(R.id.ingredient_quantity_measurement_editText))
                .setText(ingredient.getQuantityUnitOfMeasurement());
        ((EditText) findViewById(R.id.ingredient_expiration_editText))
                .setText(ingredient.getExpiration());
        ((EditText) findViewById(R.id.ingredient_special_notes_editText))
                .setText(ingredient.getSpecialNotes());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cancel:
                finish();
                return true;
            case R.id.action_finish:
                modifyIngredient();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modify_ingredient_data, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if(view == expirationEntry) {
            datePicker.show();
        }
    }

    /** modifyIngredient - Obtains entered data and updates ingredients data in database.
     *
     */
    private void modifyIngredient() {
        IngredientsDatabaseManager dbMgr = new IngredientsDatabaseManager(this);
        String name = ((TextView) findViewById(
                R.id.ingredient_name_editText)).getText().toString();
        String quantityNumber = ((TextView) findViewById(
                R.id.ingredient_quantity_number_editText)).getText().toString();
        String quantityUnitOfMeasurement = ((TextView) findViewById(
                R.id.ingredient_quantity_measurement_editText)).getText().toString();
        String expiration = ((TextView) findViewById(
                R.id.ingredient_expiration_editText)).getText().toString();
        String specialNotes = ((TextView) findViewById(
                R.id.ingredient_special_notes_editText)).getText().toString();
        Ingredient ingredient = new Ingredient(name, quantityNumber,
                quantityUnitOfMeasurement, expiration, specialNotes);
        ingredient.setId(ingredientID);
        dbMgr.updateIngredient(ingredient);
        finish();
    }

    /** setDateTimeField - Sets expiration date for ingredient.
     *
     */
    private void setDateTimeField() {
        expirationEntry.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                expirationEntry.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}
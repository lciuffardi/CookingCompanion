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
 * Created by Luigi Ciuffardi on 9/30/2017.
 */

public class AddIngredientsActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText expirationEntry;
    private DatePickerDialog datePicker;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showIngredientInputDataLayout();
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        expirationEntry = (EditText) findViewById(R.id.ingredient_expiration_editText);
        expirationEntry.setInputType(InputType.TYPE_NULL);
        expirationEntry.requestFocus();

        setDateTimeField();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_ingredient_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                addIngredient();
                return true;
            case R.id.action_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addIngredient() {
        IngredientsDatabaseManager ingDbMgr = new IngredientsDatabaseManager(this);
        String name = ((TextView) findViewById(
                R.id.ingredient_name_editText)).getText().toString();
        String quantityNumber = ((TextView) findViewById(
                R.id.ingredient_quantity_number_editText)).getText().toString();
        String quantityMeasurement = ((TextView) findViewById(
                R.id.ingredient_quantity_measurement_editText)).getText().toString();
        String expiration = ((TextView) findViewById(
                R.id.ingredient_expiration_editText)).getText().toString();
        String specialNotes = ((TextView) findViewById(
                R.id.ingredient_special_notes_editText)).getText().toString();
        Ingredient ingredient = new Ingredient(name, quantityNumber, quantityMeasurement,
                expiration, specialNotes);
        ingDbMgr.addIngredient(ingredient);
        finish();
    }

    public void showIngredientInputDataLayout() {
        setContentView(R.layout.ingredients_input_data);

    }

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

    @Override
    public void onClick(View view) {
        if(view == expirationEntry) {
            datePicker.show();
        }
    }
}
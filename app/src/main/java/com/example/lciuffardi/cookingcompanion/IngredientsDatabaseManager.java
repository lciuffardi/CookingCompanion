package com.example.lciuffardi.cookingcompanion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luigi Ciuffardi on 9/30/2017.
 * Last updated by Luigi Ciuffardi on 2/26/2019.
 */

public class IngredientsDatabaseManager extends SQLiteOpenHelper{
    private static final String TAG = IngredientsDatabaseManager.class.getName();
    public static final String TABLE_NAME = "ingredients";
    public static final String ID_FIELD = "_id";
    public static final String NAME = "name";
    public static final String QUANTITY_NUMBER = "quantity_number";
    public static final String QUANTITY_UNIT_OF_MEASUREMENT = "quantity_unit_of_measurement";
    public static final String EXPIRATION = "expiration";
    public static final String SPECIAL_NOTES = "special_notes";

    public IngredientsDatabaseManager(Context context){
        super(context, "ingredient_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try {
            Log.d(TAG, "Creating Ingredients Database...");
            String sql = "CREATE TABLE " + TABLE_NAME
                    + " (" + ID_FIELD + " INTEGER, "
                    + NAME + " TEXT, "
                    + QUANTITY_NUMBER + " INTEGER, "
                    + QUANTITY_UNIT_OF_MEASUREMENT + " TEXT, "
                    + EXPIRATION + " TEXT, "
                    + SPECIAL_NOTES + " TEXT, "
                    + " PRIMARY KEY (" + ID_FIELD + "));";
            db.execSQL(sql);
            Log.d(TAG, "Ingredients Database has been created...");
        }catch(Exception ex){
            Log.e(TAG, "Error Creating Ingredients Database...");
            ex.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2){
        try {
            Log.d(TAG, "Updating Ingredients Database...");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
            Log.d(TAG, "Ingredients Database has been updated...");
        }catch(Exception ex){
            Log.e(TAG, "Error updating Ingredients Database...");
            ex.printStackTrace();
        }
    }

    public void addIngredient(Ingredient ingredient) {
       try {
           Log.d(TAG, "Adding " + ingredient.getName() + " to Ingredient DB...");
           SQLiteDatabase db = this.getWritableDatabase();
           ContentValues values = new ContentValues();
           values.put(NAME, ingredient.getName());
           values.put(QUANTITY_NUMBER, ingredient.getQuantityNumber());
           values.put(QUANTITY_UNIT_OF_MEASUREMENT, ingredient.getQuantityUnitOfMeasurement());
           values.put(EXPIRATION, ingredient.getExpiration());
           values.put(SPECIAL_NOTES, ingredient.getSpecialNotes());
           long id = db.insert(TABLE_NAME, null, values);
           ingredient.setId(id);
           db.close();
           Log.d(TAG, ingredient.getName() + " was successfully stored in the Ingredient DB...");
       }catch(Exception ex){
           Log.e(TAG, "Error adding " + ingredient.getName() + " to Ingredient DB...");
           ex.printStackTrace();
       }
    }

    public Ingredient getIngredient(long id){
        try {
            Log.d(TAG, "Retrieving Ingredient from Ingredient Database...");
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_NAME, new String[]{ID_FIELD, NAME, QUANTITY_NUMBER,
                            QUANTITY_UNIT_OF_MEASUREMENT, EXPIRATION, SPECIAL_NOTES}, ID_FIELD + "=?",
                    new String[]{String.valueOf(id)}, null,
                    null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                Ingredient ingredient = new Ingredient(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5));
                ingredient.setId(cursor.getLong(0));
                Log.d(TAG, ingredient.getName() + " has been retrieved from the Ingredient Database...");
                return ingredient;
            }
            return null;
        }catch(Exception ex){
            Log.e(TAG, "Error retrieving Ingredient from Ingredient Database...");
            ex.printStackTrace();
            return null;
        }
    }

    public Cursor getIngredientCursor(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections={ID_FIELD, NAME, QUANTITY_NUMBER, QUANTITY_UNIT_OF_MEASUREMENT,
                EXPIRATION, SPECIAL_NOTES};
        String orderBy = NAME + "," +
                EXPIRATION;
        cursor = db.query(TABLE_NAME, projections, null,
                null, null, null, orderBy);
        return cursor;
    }

    public Cursor getExpiredIngredientCursor(SQLiteDatabase db) {
        Cursor cursor;

        String[] projections={ID_FIELD, NAME, QUANTITY_NUMBER, QUANTITY_UNIT_OF_MEASUREMENT,
                EXPIRATION, SPECIAL_NOTES};
        String orderBy = EXPIRATION + "," +
                NAME;
        cursor = db.query(TABLE_NAME, projections, EXPIRATION,
                null, null, null, orderBy);
        return cursor;
    }

    public int updateIngredient(Ingredient ingredient){
        try {
            Log.d(TAG, "Updating " + ingredient.getName() + " in Ingredient Database...");
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(NAME, ingredient.getName());
            values.put(QUANTITY_NUMBER, ingredient.getQuantityNumber());
            values.put(QUANTITY_UNIT_OF_MEASUREMENT, ingredient.getQuantityUnitOfMeasurement());
            values.put(EXPIRATION, ingredient.getExpiration());
            values.put(SPECIAL_NOTES, ingredient.getSpecialNotes());

            Log.d(TAG, ingredient.getName() + " has been updated in the Ingredient Database...");
            return db.update(TABLE_NAME, values, ID_FIELD + " = ?",
                    new String[]{String.valueOf(ingredient.getId())});
        }catch(Exception ex){
            Log.e(TAG, "Error updating " + ingredient.getName() + " in Ingredient Database...");
            ex.printStackTrace();
            return 0;
        }
    }

    public void deleteIngredient(long id) {
        try {
            Log.d(TAG, "Deleting selected ingredient from Ingredient Database...");
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, ID_FIELD + " = ?",
                    new String[]{String.valueOf(id)});
            Log.d(TAG, "Selected Ingredient has been deleted from Ingredient Database...");
            db.close();
        }catch(Exception ex){
            Log.e(TAG, "Error deleting Ingredient from Ingredient Database...");
            ex.printStackTrace();
        }
    }
}

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
 */

public class IngredientsDatabaseManager extends SQLiteOpenHelper{
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
        Log.d("db", "onCreate");
        String sql = "CREATE TABLE " + TABLE_NAME
                + " (" + ID_FIELD + " INTEGER, "
                + NAME + " TEXT, "
                + QUANTITY_NUMBER + " INTEGER, "
                + QUANTITY_UNIT_OF_MEASUREMENT + " TEXT, "
                + EXPIRATION + " TEXT, "
                + SPECIAL_NOTES + " TEXT, "
                +  " PRIMARY KEY (" + ID_FIELD + "));";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2){
        Log.d("db", "onUpdate");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        Log.d("db", "addIngredient");
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
        return ingredient;
    }

    Ingredient getIngredient(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {ID_FIELD, NAME, QUANTITY_NUMBER,
                        QUANTITY_UNIT_OF_MEASUREMENT, EXPIRATION, SPECIAL_NOTES}, ID_FIELD + "=?",
                new String[] { String.valueOf(id) }, null,
        null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            Ingredient ingredient = new Ingredient(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            ingredient.setId(cursor.getLong(0));
            return ingredient;
        }
        return null;
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<Ingredient>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while(cursor.moveToNext()){
            Ingredient ingredient = new Ingredient();
            ingredient.setId(Integer.parseInt(cursor.getString(0)));
            ingredient.setName(cursor.getString(1));
            ingredient.setQuantityNumber(cursor.getString(2));
            ingredient.setQuantityUnitOfMeasurement(cursor.getString(3));
            ingredient.setExpiration(cursor.getString(4));
            ingredient.setSpecialNotes(cursor.getString(5));
            ingredients.add(ingredient);
        }
        return ingredients;
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
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NAME, ingredient.getName());
        values.put(QUANTITY_NUMBER, ingredient.getQuantityNumber());
        values.put(QUANTITY_UNIT_OF_MEASUREMENT, ingredient.getQuantityUnitOfMeasurement());
        values.put(EXPIRATION, ingredient.getExpiration());
        values.put(SPECIAL_NOTES, ingredient.getSpecialNotes());

        return db.update(TABLE_NAME, values, ID_FIELD + " = ?",
                new String[] { String.valueOf(ingredient.getId())});
    }

    public void deleteIngredient(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID_FIELD + " = ?",
                new String[] { String.valueOf(id)});
        db.close();
    }
}

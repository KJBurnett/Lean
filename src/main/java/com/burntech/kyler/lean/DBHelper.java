package com.burntech.kyler.lean;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Kyler on 5/14/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    // DATABASE NAME.
    public static final String  DATABASE_NAME = "Weight.db";

    // TABLE 1 - weights:
    public static final String TABLE_NAME   = "weights";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_DATETIME = "datetime";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME +
                        "(weight text, " +
                        "datetime text primary key)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertWeight(String weight, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_WEIGHT, weight);
        contentValues.put(COLUMN_DATETIME, datetime);

        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        return true;
    }

    public ArrayList<Weight> getWeights() {
        ArrayList<Weight> weights = new ArrayList<Weight>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.query(TABLE_NAME, null, COLUMN_WEIGHT + "=? and " + COLUMN_DATETIME + "=?", new String[] {weight, datetime}, null, null, null);
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            String weight = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_WEIGHT));
            String datetime = cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATETIME));
            Weight newWeight = new Weight(weight, datetime);
            weights.add(newWeight);
            cursor.moveToNext();
        }
        return weights;
    }

    public boolean weightsEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = 0;
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            count++;
            cursor.moveToNext();
        }
        if (count > 0)
            return false;
        else return true;
    }
}

package com.ttmaps.maps;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emilychou on 10/30/17.
 */

    public class DBHandler extends SQLiteOpenHelper {

        // Database Version
        private static final int DATABASE_VERSION = 1;
        // Database Name
        private static final String DATABASE_NAME = "poiInfo";
        // Contacts table name
        private static final String TABLE_POIS = "POIS";
        // Shops Table Columns names
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final int KEY_SH_DIST = 0;

        public DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_POIS + "(" + KEY_NAME + "TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
// Creating tables again
            onCreate(db);
        }

    public void addPOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poi.getName()); // POI Name
// Inserting Row
        db.insert(TABLE_POIS, null, values);
        db.close(); // Closing database connection
    }

    public POI getPOI(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POIS, new String[]{KEY_ID,
                KEY_NAME}, KEY_ID + "=?",
        new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        POI contact = new POI(cursor.getString(0));
// return shop
        return contact;
    }

    public List<POI> getAllPOIs() {
        List<POI> poiList = new ArrayList<POI>();
// Select All Query
        String selectQuery = "SELECT * FROM" + TABLE_POIS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                POI poi = new POI(cursor.getString(1));
                poi.setId(cursor.getString(0));
                poi.setDistance(Integer.parseInt(cursor.getString(2)));
// Adding contact to list
                poiList.add(poi);
            } while (cursor.moveToNext());
        }

// return contact list
        return poiList;
    }

    // Getting pois Count
    public int getPOIsCount() {
        String countQuery = "SELECT * FROM" + TABLE_POIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    // Updating a poi
    public int updatePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poi.getName());
        values.put(KEY_ID, poi.getId());

// updating row
        return db.update(TABLE_POIS, values, KEY_ID + "=?",
        new String[]{String.valueOf(poi.getId())});
    }

    // Deleting a poi
    public void deletePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POIS, KEY_ID + "=?",
        new String[] { String.valueOf(poi.getId()) });
        db.close();
    }

}


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

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "poiInfo";
        private static final String TABLE_POIS = "POIS";
        private static final String KEY_ID = "id";
        private static final String KEY_NAME = "name";
        private static final int KEY_SH_DIST = 0;

        public DBHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_POIS + "(" + KEY_ID + "INTEGER PRIMARY KEY," + KEY_NAME + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
            onCreate(db);
        }

    public void addPOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poi.getName()); // POI Name
        db.insert(TABLE_POIS, null, values);
        db.close();
    }

    public POI getPOI(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_POIS, new String[]{KEY_ID,
//                KEY_NAME}, KEY_ID + "=?",
//        new String[]{String.valueOf(id)}, null, null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        POI contact = new POI(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
//        return contact;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from " + TABLE_POIS +  " where id=" + id + "", null );
        POI contact = new POI(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return contact;
    }

    public List<POI> getAllPOIs() {
        List<POI> poiList = new ArrayList<POI>();
        String selectQuery = "SELECT * FROM " + TABLE_POIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

//        if (cursor.moveToFirst()) {
//            do {
//                POI poi = new POI();
//                poi.setId(Integer.parseInt(cursor.getString(0)));
//                //poi.setDistance(Integer.parseInt(cursor.getString(2)));
//                poiList.add(poi);
//            } while (cursor.moveToNext());
//        }
        while(cursor.isAfterLast() == false){
            POI poi = new POI(Integer.parseInt(cursor.getString(0)), (cursor.getString(1)));
            poiList.add(poi);
            cursor.moveToNext();
        }
        return poiList;
    }

    public int getPOIsCount() {
        String countQuery = "SELECT * FROM" + TABLE_POIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poi.getName());
        values.put(KEY_ID, poi.getId());
        return db.update(TABLE_POIS, values, KEY_ID + " =?",
        new String[]{String.valueOf(poi.getId())});
    }

    public void deletePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POIS, KEY_ID + "=?",
        new String[] { String.valueOf(poi.getId()) });
        db.close();
    }

}


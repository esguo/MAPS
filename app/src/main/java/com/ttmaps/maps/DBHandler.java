package com.ttmaps.maps;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by emilychou on 10/30/17.
 */

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "poiInfo";
    private static final String TABLE_POIS = "POIS";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private HashMap<Integer, POI> poilist;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        poilist = new HashMap<Integer, POI>();
    }
    
    /* create the initial database */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating: ","Creating..");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POIS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT NOT NULL UNIQUE" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    /* drops table when database version is changed */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
        onCreate(db);
    }

    /* add POI to the POI table */
    public void addPOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, poi.getId());
        values.put(KEY_NAME, poi.getName());
        db.insertWithOnConflict(TABLE_POIS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    /* get a POI by id from the POI table */
    public POI getPOI(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POIS, new String[]{KEY_ID,
                        KEY_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        POI contact = new POI(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return contact;
    }

    /* get a POI by name from the POI table */
    public POI getPOIByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POIS, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        POI contact = new POI(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
        return contact;
    }

    /* get a list of all POIs in the POI table */
    public List<POI> getAllPOIs() {
        List<POI> poiList = new ArrayList<POI>();
        String selectQuery = "SELECT * FROM " + TABLE_POIS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            POI poi = new POI(Integer.parseInt(cursor.getString(0)), (cursor.getString(1)));
            poiList.add(poi);
            cursor.moveToNext();
        }
        return poiList;
    }

    /* get the number of POIs currently in the table */
    public int getPOIsCount() {
        String countQuery = "SELECT * FROM " + TABLE_POIS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int c = cursor.getCount();
        cursor.close();
        return c;
    }

    /* function to update a POI's fields (will edit in the future depending on
       which fields we decide are constant and which are not */
    /*public int updatePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, poi.getName());
        values.put(KEY_ID, poi.getId());
        return db.update(TABLE_POIS, values, KEY_ID + " =?",
        new String[]{String.valueOf(poi.getId())});
    }*/

    /* delete a POI from the POI table */
    public void deletePOI(POI poi) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POIS, KEY_ID + "=?",
        new String[] { String.valueOf(poi.getId()) });
        db.close();
    }

    public void updateDb(){
        String[] POI = {"Warren", "Muir", "Revelle", "Sixth", "Marshall", "Elenor Roosevelt College", "Center", "PC",
            "Pepper Canyon", "Ledden", "Galbraith", "York", "Cognitive Science Building", "Solis", "Rady"};

        for(int i = 0; i < POI.length; i++){
            POI poi = new POI(i, POI[i]);
            addPOI(poi);
            poilist.put(poi.getId(), poi);
        }
    }
    public void createPair(int pointA, int pointB, String name, int weight){
        POI a = poilist.get(pointA);
        POI b = poilist.get(pointB);

        Edge e = new Edge(name, weight);
        a.addNeighbor(b, e);
        b.addNeighbor(a, e);


        /*deletePOI(getPOIByName(pointA));
        deletePOI(getPOIByName(pointB));

        addPOI(a);
        addPOI(b);*/
    }

    public List<POI> getPOIs() {
        List<POI> poiList = new ArrayList<POI>();
        for (POI p: poilist.values()){
            poiList.add(p);
        }
        return poiList;
    }
}


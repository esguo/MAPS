package com.ttmaps.maps;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/** Database and included datasets to select POI and their respective edges
 */
class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 22;
    private static final String DATABASE_NAME = "poiInfo";
    private static final String TABLE_POIS = "POIS";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_RATING = "totalRating";
    private static final String KEY_RATING_COUNT = "ratingCount";
    private static final String KEY_AVG_RATING = "avgRating";
    private static final String KEY_RATING_COM = "ratingCom";
    private HashMap<String, POI> poilist;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        poilist = new HashMap<>();
    }
    
    /* create the initial database */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating: ","Creating..");
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_POIS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT NOT NULL UNIQUE, " + KEY_RATING + " INTEGER, " + KEY_RATING_COUNT + " INTEGER, " + KEY_AVG_RATING + " INTEGER, " + KEY_RATING_COM +  " TEXT NOT NULL" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    
    /* drops table when database version is changed */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POIS);
        onCreate(db);
    }

    /* add POI to the POI table */
    public void addPOI(POI poi, int rating, int count, int avg_rating, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, poi.getId());
        values.put(KEY_NAME, poi.getName());
        values.put(KEY_RATING, rating);
        values.put(KEY_RATING_COUNT, count);
        values.put(KEY_AVG_RATING, avg_rating);
        values.put(KEY_RATING_COM, comment);
        db.insertWithOnConflict(TABLE_POIS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        db.close();
    }

    /* get a POI by id from the POI table */
    public String getPOI(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String rowString = "Table with row " + id + " is: ";
        Cursor row = db.rawQuery("SELECT * FROM " + TABLE_POIS + " WHERE " + KEY_ID + " = " + id, null);
        if (row.moveToFirst() ){
            String[] columnNames = row.getColumnNames();
            do {
                for (String name: columnNames) {
                    rowString += String.format("%s: %s\n", name,
                            row.getString(row.getColumnIndex(name)));
                }
                rowString += "\n";

            } while (row.moveToNext());
        }

        return rowString;
    }

    /* get a POI by name from the POI table */
    public POI getPOIByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POIS, new String[]{KEY_ID,
                        KEY_NAME}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new POI(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
    }

    /* get a list of all POIs in the POI table */
    public String getAllPOIs() {
        SQLiteDatabase db = this.getReadableDatabase();
        String rowString = "All rows is: ";
        Cursor row = db.rawQuery("SELECT * FROM " + TABLE_POIS, null);
        if (row.moveToFirst() ){
            String[] columnNames = row.getColumnNames();
            do {
                for (String name: columnNames) {
                    rowString += String.format("%s: %s\n", name,
                            row.getString(row.getColumnIndex(name)));
                }
                rowString += "\n";

            } while (row.moveToNext());
        }

        return rowString;
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


    public int getRating(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT " + KEY_RATING + " FROM " + TABLE_POIS + " WHERE id = " + id);
        Cursor cursor = db.rawQuery(query, null);
        int rating = 0;
        if(cursor != null & cursor.getCount() > 0){
            cursor.moveToFirst();
            rating = cursor.getInt(cursor.getColumnIndex(KEY_RATING));
            cursor.close();
        }
        return rating;
    }

    public int getRatingCount(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT " + KEY_RATING_COUNT + " FROM " + TABLE_POIS + " WHERE id = " + id);
        Cursor cursor = db.rawQuery(query, null);
        int rating = 0;
        if(cursor != null & cursor.getCount() > 0){
            cursor.moveToFirst();
            rating = cursor.getInt(cursor.getColumnIndex(KEY_RATING_COUNT));
            cursor.close();
        }
        return rating;
    }

    public int getAvgRating(int id){
        if(getRatingCount(id) == 0){
            return 0;
        }
        return getRating(id)/getRatingCount(id);
    }

    public String getRatingCom(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = ("SELECT " + KEY_RATING_COM + " FROM " + TABLE_POIS + " WHERE id = " + id);
        Cursor cursor = db.rawQuery(query, null);
        String comment = "";
        if(cursor != null & cursor.getCount() > 0){
            cursor.moveToFirst();
            comment = cursor.getString(cursor.getColumnIndex(KEY_RATING_COM));
            cursor.close();
        }
        return comment;
    }


    /* delete a POI from the POI table */
    public void deleteRow(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POIS, KEY_ID + "=?",
        new String[] { String.valueOf(id) });
        db.close();
    }

    public int updatePOI(int id, String name, int newRating, String newComment) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.rawQuery("UPDATE " + TABLE_POIS + " SET " + KEY_RATING + " = " + (newRating + getRating(id)) + " WHERE id = " + id, null);
//        return newRating + getRating(id);
        POI poi = getPOIByName(name);
        int prevRating = getRating(poi.getId());
        int prevCount = getRatingCount(poi.getId());
        String prevComment = getRatingCom(poi.getId());
        deleteRow(poi.getId());
        addPOI(poi, prevRating + newRating, prevCount + 1, (prevRating + newRating)/(prevCount + 1), ("\n" + newComment + prevComment));
        return newRating + getRating(poi.getId());
    }


    public void updateDb(){
        String[] POI = {"warren", "muir", "revelle", "sixth", "marshall", "elenor roosevelt college", "center", "pc",
            "pepper canyon", "ledden", "galbraith", "york", "cognitive science building", "solis", "rady"};

        for(int i = 0; i < POI.length; i++){
            POI poi = new POI(i, POI[i]);
            addPOI(poi, 0, 0, 0, "");
        }
    }

    /* creates POI based on id, name, populates hash table with the appropriate POI */
    public void populateHash(String [] data){
        int id = Integer.parseInt(data[0]);
        String POIName = data[1];
        String img_file = "";
        try{
            img_file = data[9];
        }
            catch (Exception e){
        }

        POI poi = new POI(id, POIName, img_file);

        boolean [] filters = new boolean[7];

        for(int i = 0; i < filters.length; i++)
            filters[i] = Boolean.parseBoolean(data[i+2]);


        if(filters[0]) poi.setIsAdmin();
        if(filters[1]) poi.setIsClassroom();
        if(filters[2]) poi.setIsFood();
        if(filters[3]) poi.setIsParking();
        if(filters[4]) poi.setIsRec();
        if(filters[5]) poi.setIsResHall();
        if(filters[6]) poi.setIsStudyArea();

        try{
            poi.setLatLng(Double.parseDouble(data[10]), Double.parseDouble(data[11]));
        }
        catch (Exception e){
            poi.setLatLng(0,0);
        }


        poilist.put(poi.getName(), poi);
        POI dbpoi = new POI(id, POIName.toLowerCase());
        addPOI(dbpoi, 0, 0, 0, "");
    }

    public void createPair(String pointA, String pointB, String name, int weight){
        POI a = poilist.get(pointA);
        POI b = poilist.get(pointB);

        Edge e = new Edge(name, weight);
        a.addNeighbor(b, e);
        b.addNeighbor(a, e);
    }

    public List<POI> getPOIs() {
        List<POI> poiList = new ArrayList<>();
        for (POI p: poilist.values()){
            poiList.add(p);
        }
        return poiList;
    }
}


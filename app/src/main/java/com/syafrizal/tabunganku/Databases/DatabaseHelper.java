package com.syafrizal.tabunganku.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Tabungan.db";
    private static final int DATABASE_VERSION = 1;
    static final String TABLE_NAME = "TABLE_TABUNGAN";

    static final String COLUMN_JUDUL = "JUDUL";
    static final String COLUMN_JUMLAH = "JUMLAH";
    static final String COLUMN_TIPE = "TIPE";

    SQLiteDatabase db = getWritableDatabase();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , JUDUL TEXT, JUMLAH INTEGER ,TIPE TEXT)";
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(SQL_DELETE_TABLE);
    }

    public boolean insertData(String judul , int jumlah , String tipe){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_JUDUL,judul);
        contentValues.put(COLUMN_JUMLAH,jumlah);
        contentValues.put(COLUMN_TIPE,tipe);

        Long result = db.insert(TABLE_NAME,null,contentValues);
        if (result == -1){
            return false;
        }else {
            return true;
        }

    }

    public Cursor getData(){
        Cursor data = db.rawQuery("SELECT * FROM "+ TABLE_NAME , null);
        return data;
    }

    public boolean deleteData(int id){
        return db.delete(TABLE_NAME, "ID" + "=" + id, null) > 0;
    }

    public boolean updateData(int id ,String judul , int jumlah , String tipe){
        ContentValues args = new ContentValues();
        args.put(COLUMN_JUDUL, judul);
        args.put(COLUMN_JUMLAH, jumlah);
        args.put(COLUMN_TIPE,tipe);
        return db.update(TABLE_NAME, args, "ID" + "=" + id, null) > 0;
    }


}

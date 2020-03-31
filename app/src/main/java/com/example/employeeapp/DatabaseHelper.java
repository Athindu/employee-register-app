package com.example.employeeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "empDB.db";
    public static final String TABLE_NAME = "empTable";
    public static final String COL_1 = "empID";
    public static final String COL_2 = "empName";
    public static final String COL_3 = "empAddress";
    public static final String COL_4 = "empAge";
    public static final String COL_5 = "empPosition";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(empID TEXT PRIMARY KEY , empName TEXT, empAddress TEXT , empAge TEXT , empPosition TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertData(String empID,String empName, String empAddress, String empAge, String empPosition){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,empID);
        contentValues.put(COL_2,empName);
        contentValues.put(COL_3,empAddress);
        contentValues.put(COL_4,empAge);
        contentValues.put(COL_5,empPosition);
        long result = database.insert(TABLE_NAME,null,contentValues);
        System.out.println(result);
        if (result==-1)
            return false;
        else
            return true;
    }

    public Cursor retrieveData(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        return cursor;
    }


    public Boolean updateData(String empID,String empName, String empAddress, String empAge, String empPosition ){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,empID);
        contentValues.put(COL_2,empName);
        contentValues.put(COL_3,empAddress);
        contentValues.put(COL_4,empAge);
        contentValues.put(COL_5,empPosition);
        long upResult = database.update(TABLE_NAME,contentValues,"empID = ?",new  String[] {empID});
        System.out.println("up- " + upResult);
        if (upResult==0)
            return false;
        else
            return true;
    }


    public Integer deleteData(String eID){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME,"empID = ?", new String[] {eID});
    }
}

package com.rdc.agenciadeviajes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BBDD_Helper extends SQLiteOpenHelper {

    private static final String TABLA_VIAJES = "CREATE TABLE viajes" +
           "(_id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, correo TEXT, contraseña TEXT, lugar TEXT, cantidad TEXT)";


    public BBDD_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "bd_viajes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(TABLA_VIAJES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS viajes");
        onCreate(db);
    }
}

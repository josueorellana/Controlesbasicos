package com.example.controlesbasicos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String dbname = "db_Productos";
    private Context mContext;

    public DBHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Usuario(codigoUsuario integer primary key autoincrement, nombre text, edad text, telefono text, correo text, password text )");
        db.execSQL("INSERT INTO Usuario(correo, password) VALUES('admin','admin')");

        // Alerta de creación exitosa
        Toast.makeText(mContext, "Base de datos y usuario administrador creados correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        onCreate(db);
    }
}

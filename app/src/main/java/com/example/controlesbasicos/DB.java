package com.example.fmetzli;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_Productos";
    private Context mContext;
    private static final int v = 1;
    private static final String SQldb = "CREATE TABLE Usuario(codogoUsuario integer primary key autoincrement, nombre text, edad text, telefono text, correo text, password text )";

    public DB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public String administrar_produtos(String[] datos) {
        try {
            ContentValues valoresProducto = new ContentValues();
            String[] whereArgs = {String.valueOf(datos[0])};
            String whereClause = "codigoUsuario = ?";
            String miTabla = "Usuario";
            if (datos.length > 1) {
                valoresProducto.put("nombre", datos[1]);
                valoresProducto.put("edad", datos[2]);
                valoresProducto.put("telefono", datos[3]);
                valoresProducto.put("correo", datos[4]);
                valoresProducto.put("password", datos[5]);
            }// Alerta de creación exitosa
            this.getWritableDatabase().insert(miTabla,null,valoresProducto);
            Toast.makeText(mContext, "cuenta creada con exito", Toast.LENGTH_SHORT).show();
            return "ok";
        } catch (Exception e) {
            return "Error20: " + e.getMessage();
        }
    }

    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Usuario ORDER BY nombre", null);
        return cursor;

    }

}

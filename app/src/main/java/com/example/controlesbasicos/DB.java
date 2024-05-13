package com.ugb.controlesbasicos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static final String dbname = "db_Productos";
    private static final int v=1;
    private static final String SQldb = "CREATE TABLE Productos(id text,rev text, idProducto text,Codigo text, descripcion text, marca text, presentacion text, precio text,costo text,stock text,foto text, estado text, porcentaje text)";
    public DB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, dbname, factory, v);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQldb);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        //para hacer la actualizacion de la BD
    }
    public String administrar_Productos(String accion, String[] datos) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            if (accion.equals("nuevo")) {
                db.execSQL("INSERT INTO Productos(id,rev,idProducto,Codigo,descripcion,marca,presentacion,precio,costo,stock,foto, estado, porcentaje) VALUES ('" + datos[0] + "', '" + datos[1] + "', '" +
                        datos[2] + "', '" + datos[3] + "','" + datos[4] + "','" + datos[5] + "','" + datos[6] + "','" + datos[7] + "', '" + datos[8] + "','" + datos[9] +"','"+ datos[10]+"','"+ datos[11] +"','"+ datos[12] +"')");
            } else if (accion.equals("modificar")) {
                db.execSQL("UPDATE Productos SET id='" + datos[0] + "',rev='" + datos[1] + "', codigo='" + datos[3] + "',descripcion='" + datos[4] + "',marca='" +
                        datos[5] + "',presentacion='" + datos[6] + "',precio='" + datos[7] + "', costo='" + datos[8] + "',stock='" + datos[9] + "',foto='" + datos[10] + "',estado='" + datos[11] + "',porcentaje='" + datos[12] + "' WHERE idProducto='" + datos[2] + "'");
            } else if (accion.equals("eliminar")) {
                db.execSQL("UPDATE Productos SET estado='0' WHERE idProducto='" + datos[0] + "'");
            }
            return "ok";
        } catch (Exception e) {
            return "Error2: " + e.getMessage();
        }
    }
    public Cursor consultar_Productos(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Productos WHERE estado ='1' ORDER BY codigo", null);
        return cursor;

    }
}

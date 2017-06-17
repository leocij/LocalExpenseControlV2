package com.lemelo.localexpensecontrolv2.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by leoci on 13/06/2017.
 */

public class FabricaConexao extends SQLiteOpenHelper{
    public FabricaConexao(Context context) {
        super(context, "banco.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table entrada (id integer primary key autoincrement, data date, descricao text, valor decimal(18,2))");
        db.execSQL("create table saida (id integer primary key autoincrement, data date, descricao text, valor decimal(18,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table entrada");
        db.execSQL("drop table saida");
        onCreate(db);
    }
}

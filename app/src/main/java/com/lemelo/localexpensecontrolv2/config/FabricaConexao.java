package com.lemelo.localexpensecontrolv2.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by leoci on 13/06/2017.
 */

public class FabricaConexao extends SQLiteOpenHelper{
    public FabricaConexao(Context context) {
        super(context, "banco.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists entrada (id integer primary key autoincrement, data date, descricao text, valor decimal(18,2))");
        db.execSQL("create table if not exists saida (id integer primary key autoincrement, data date, descricao text, valor decimal(18,2))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("Passei no onUpgrade! ");
        System.out.println("oldVersion: " + oldVersion);
        System.out.println("newVersion: " + newVersion);
        //db.execSQL("drop table entrada");
        //db.execSQL("drop table saida");
        onCreate(db);
    }
}

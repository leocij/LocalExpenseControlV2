package com.lemelo.localexpensecontrolv2;

/*
 * Created by leoci on 13/06/2017.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntradaDao {
    private SQLiteDatabase db;

    public EntradaDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(Entrada entrada){
        String sql = "insert into entrada (data,descricao,valor) values (?,?,?)";
        Object bindArgs[] = new Object[]{
                entrada.getData(), entrada.getDescricao(), entrada.getValor()
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Entrada entrada){
        String sql = "delete from entrada where id = ?";
        Object bindArgs[] = new Object[]{
                entrada.getId()
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Integer id){
        String sql = "delete from entrada where id = ?";
        Object bindArgs[] = new Object[]{
                id
        };
        db.execSQL(sql, bindArgs);
    }

    public void update(Entrada entrada){
        String sql = "update Entrada set data = ?, descricao = ?, valor = ? where id = ?";
        Object bindArgs[] = new Object[]{
                entrada.getData(), entrada.getDescricao(), entrada.getValor(), entrada.getId()
        };
        db.execSQL(sql, bindArgs);
    }

    public List<Entrada> listAll() throws ParseException {
        List<Entrada> entradas = new ArrayList<Entrada>();
        Cursor cursor = db.query("Entrada", new String[]{"id","data","descricao","valor"},null,null,null,null,"id");
        while (cursor != null && cursor.moveToNext()){
            Entrada entrada = new Entrada();
            entrada.setId(cursor.getInt(0));
            String str = cursor.getString(1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d = dateFormat.parse(str);
            java.sql.Date dataSql = new java.sql.Date(d.getTime());
            entrada.setData(dataSql);
            entrada.setDescricao(cursor.getString(2));
            entrada.setValor(new BigDecimal(cursor.getDouble(3)));
            entradas.add(entrada);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return entradas;
    }

    public Entrada getById(Integer id) throws ParseException {
        Entrada entrada = null;
        Cursor cursor = db.query("Entrada", new String[]{"id","data","descricao","valor"},"id="+id,null,null,null,null);
        if(cursor != null && cursor.moveToNext()){
            entrada = new Entrada();
            entrada.setId(cursor.getInt(0));
            String str = cursor.getString(1);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            d = dateFormat.parse(str);
            entrada.setData((java.sql.Date) d);
            entrada.setDescricao(cursor.getString(2));
            entrada.setValor(new BigDecimal(cursor.getDouble(3)));
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return entrada;
    }
}

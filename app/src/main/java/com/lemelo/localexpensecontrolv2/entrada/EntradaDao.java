package com.lemelo.localexpensecontrolv2.entrada;

/*
 * Created by leoci on 13/06/2017.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lemelo.localexpensecontrolv2.entrada.Entrada;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntradaDao {
    private SQLiteDatabase db;

    public EntradaDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(Entrada entrada) throws ParseException {
        String sql = "insert into entrada (data,descricao,valor) values (?,?,?)";
        String dataStr = entrada.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, entrada.getDescricao(), new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(entrada.getValor()))))
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

    public void update(Entrada entrada) throws ParseException {

        String sql = "update entrada set data = ?, descricao = ?, valor = ? where id = ?";
        String dataStr = entrada.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        java.util.Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, entrada.getDescricao(), new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(entrada.getValor())))) , entrada.getId()
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
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateUtil = sdf1.parse(str);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            entrada.setData(sdf2.format(dateUtil));
            entrada.setDescricao(cursor.getString(2));
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            entrada.setValor(nf.format(cursor.getDouble(3)));
            entradas.add(entrada);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return entradas;
    }
}

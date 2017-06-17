package com.lemelo.localexpensecontrolv2.saida;

/*
 * Created by leoci on 13/06/2017.
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SaidaDao {
    private SQLiteDatabase db;

    public SaidaDao(SQLiteDatabase db){
        this.db = db;
    }

    public void insert(Saida saida) throws ParseException {
        String sql = "insert into saida (data,descricao,valor) values (?,?,?)";
        String dataStr = saida.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, saida.getDescricao(), new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(saida.getValor()))))
        };
        db.execSQL(sql, bindArgs);
    }

    public void delete(Integer id){
        String sql = "delete from saida where id = ?";
        Object bindArgs[] = new Object[]{
                id
        };
        db.execSQL(sql, bindArgs);
    }

    public void update(Saida saida) throws ParseException {

        String sql = "update saida set data = ?, descricao = ?, valor = ? where id = ?";
        String dataStr = saida.getData();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date d = sdf1.parse(dataStr);
        java.sql.Date dataSql = new java.sql.Date(d.getTime());
        Object bindArgs[] = new Object[]{
                dataSql, saida.getDescricao(), new BigDecimal(Long.parseLong(String.valueOf(NumberFormat.getCurrencyInstance().parse(saida.getValor())))) , saida.getId()
        };
        db.execSQL(sql, bindArgs);
    }

    public List<Saida> listAll() throws ParseException {
        List<Saida> saidas = new ArrayList<Saida>();
        Cursor cursor = db.query("Saida", new String[]{"id","data","descricao","valor"},null,null,null,null,"id");
        while (cursor != null && cursor.moveToNext()){
            Saida saida = new Saida();
            saida.setId(cursor.getInt(0));
            String str = cursor.getString(1);
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            Date dateUtil = sdf1.parse(str);
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            saida.setData(sdf2.format(dateUtil));
            saida.setDescricao(cursor.getString(2));
            NumberFormat nf = NumberFormat.getCurrencyInstance();
            saida.setValor(nf.format(cursor.getDouble(3)));
            saidas.add(saida);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        return saidas;
    }
}

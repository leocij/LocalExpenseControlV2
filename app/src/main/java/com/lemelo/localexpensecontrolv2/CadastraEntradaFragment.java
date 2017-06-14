package com.lemelo.localexpensecontrolv2;

import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/*
 * Created by leoci on 13/06/2017.
 */

public class CadastraEntradaFragment extends Fragment{

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cadastra_entrada, container, false);
        getActivity().setTitle("Entrada de Valores");
        ScrollView loginScrollView = (ScrollView) view.findViewById(R.id.scrollViewCadastraEntrada);
        loginScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);
                return false;
            }
        });

        final EditText txtEntradaData = (EditText) view.findViewById(R.id.txtData);
        final EditText txtEntradaDescricao = (EditText) view.findViewById(R.id.txtDescricao);
        final EditText txtEntradaValor = (EditText) view.findViewById(R.id.txtValor);

        final SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        txtEntradaData.setText(data.format(Calendar.getInstance().getTime()));

        final Button btnControleSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnControleSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;

            @Override
            public void onClick(View v) {
                // Fecha Teclado
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d = sdf.parse(txtEntradaData.getText().toString());
                    java.sql.Date dataSql = new java.sql.Date(d.getTime());
                    String descricao = txtEntradaDescricao.getText().toString();
                    String valorStr = txtEntradaValor.getText().toString();
                    if(valorStr.equals("")){
                        valorStr = "0.0";
                    }
                    BigDecimal valor = new BigDecimal(valorStr);

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    EntradaDao entradaDao = new EntradaDao(db);

                    Entrada entrada = new Entrada();
                    entrada.setData(dataSql);
                    entrada.setDescricao(descricao);
                    entrada.setValor(valor);

                    entradaDao.insert(entrada);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                } catch (ParseException e) {
                    e.printStackTrace();
                } finally {
                    if (db != null && db.isOpen()) {
                        db.close();
                    }
                }
            }
        });

        return view;
    }
}

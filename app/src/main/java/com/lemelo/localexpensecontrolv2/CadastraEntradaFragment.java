package com.lemelo.localexpensecontrolv2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

                //close keyboard
                new MyKeyboard().hideKeyboard(getActivity(), v);
                //Toast.makeText(getContext(), "Passei aqui - Dentro do Scroll!", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        final EditText txtEntradaData = (EditText) view.findViewById(R.id.txtData);
        final EditText txtEntradaDescricao = (EditText) view.findViewById(R.id.txtDescricao);
        final EditText txtEntradaValor = (EditText) view.findViewById(R.id.txtValor);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //final DateFormat data = DateFormat.getDateInstance();
        txtEntradaData.setText(sdf1.format(Calendar.getInstance().getTime()));

        txtEntradaDescricao.requestFocus();
        //open keyboard
        new MyKeyboard().showKeyboard(getActivity());

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        txtEntradaValor.setText(nf.format(0));
        txtEntradaValor.addTextChangedListener(new MoneyTextWatcher(txtEntradaValor));

        final Button btnControleSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnControleSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;

            @Override
            public void onClick(View v) {
                try {
                    String dataStr = txtEntradaData.getText().toString();
                    String descricao = txtEntradaDescricao.getText().toString();
                    String valorStr = txtEntradaValor.getText().toString();
                    if(valorStr.equals("")){
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        valorStr = nf.format(0);
                    }
                    //BigDecimal valor = new BigDecimal(valorStr);

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    EntradaDao entradaDao = new EntradaDao(db);

                    Entrada entrada = new Entrada();
                    entrada.setData(dataStr);
                    entrada.setDescricao(descricao);
                    entrada.setValor(valorStr);

                    entradaDao.insert(entrada);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    //LIMPA O FORMULARIO
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    txtEntradaData.setText(sdf1.format(Calendar.getInstance().getTime()));

                    //Toast.makeText(getContext(), "Passei aqui - Abre teclado!", Toast.LENGTH_LONG).show();
                    //close keyboard
                    new MyKeyboard().hideKeyboard(getActivity(), v);
                    //open keyboard
                    new MyKeyboard().showKeyboard(getActivity());
                    txtEntradaDescricao.setText("");
                    txtEntradaDescricao.requestFocus();

                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    txtEntradaValor.setText(nf.format(0));
                    txtEntradaValor.addTextChangedListener(new MoneyTextWatcher(txtEntradaValor));

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

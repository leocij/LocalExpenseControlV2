package com.lemelo.localexpensecontrolv2.saida;

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

import com.lemelo.localexpensecontrolv2.R;
import com.lemelo.localexpensecontrolv2.config.FabricaConexao;
import com.lemelo.localexpensecontrolv2.config.MoneyTextWatcher;
import com.lemelo.localexpensecontrolv2.config.MyKeyboard;
import com.lemelo.localexpensecontrolv2.saida.Saida;
import com.lemelo.localexpensecontrolv2.saida.SaidaDao;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/*
 * Created by leoci on 13/06/2017.
 */

public class CadastraSaidaFragment extends Fragment {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_cadastra_saida, container, false);
        getActivity().setTitle("Saida de Valores");
        ScrollView loginScrollView = (ScrollView) view.findViewById(R.id.scrollViewCadastraSaida);
        loginScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //close keyboard
                new MyKeyboard().hideKeyboard(getActivity(), v);
                //Toast.makeText(getContext(), "Passei aqui - Dentro do Scroll!", Toast.LENGTH_LONG).show();
                return false;
            }
        });

        final EditText txtSaidaData = (EditText) view.findViewById(R.id.txtData);
        final EditText txtSaidaDescricao = (EditText) view.findViewById(R.id.txtDescricao);
        final EditText txtSaidaValor = (EditText) view.findViewById(R.id.txtValor);

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        //final DateFormat data = DateFormat.getDateInstance();
        txtSaidaData.setText(sdf1.format(Calendar.getInstance().getTime()));

        txtSaidaDescricao.requestFocus();
        //open keyboard
        new MyKeyboard().showKeyboard(getActivity());

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        txtSaidaValor.setText(nf.format(0));
        txtSaidaValor.addTextChangedListener(new MoneyTextWatcher(txtSaidaValor));

        final Button btnControleSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnControleSalvar.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db;

            @Override
            public void onClick(View v) {
                try {
                    String dataStr = txtSaidaData.getText().toString();
                    String descricao = txtSaidaDescricao.getText().toString();
                    String valorStr = txtSaidaValor.getText().toString();
                    if (valorStr.equals("")) {
                        NumberFormat nf = NumberFormat.getCurrencyInstance();
                        valorStr = nf.format(0);
                    }
                    //BigDecimal valor = new BigDecimal(valorStr);

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    SaidaDao saidaDao = new SaidaDao(db);

                    Saida saida = new Saida();
                    saida.setData(dataStr);
                    saida.setDescricao(descricao);
                    saida.setValor(valorStr);

                    saidaDao.insert(saida);

                    Toast.makeText(getContext(), "Registro Salvo!", Toast.LENGTH_LONG).show();

                    //LIMPA O FORMULARIO
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    txtSaidaData.setText(sdf1.format(Calendar.getInstance().getTime()));

                    //Toast.makeText(getContext(), "Passei aqui - Abre teclado!", Toast.LENGTH_LONG).show();
                    //close keyboard
                    new MyKeyboard().hideKeyboard(getActivity(), v);
                    //open keyboard
                    new MyKeyboard().showKeyboard(getActivity());
                    txtSaidaDescricao.setText("");
                    txtSaidaDescricao.requestFocus();

                    NumberFormat nf = NumberFormat.getCurrencyInstance();
                    txtSaidaValor.setText(nf.format(0));
                    txtSaidaValor.addTextChangedListener(new MoneyTextWatcher(txtSaidaValor));

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

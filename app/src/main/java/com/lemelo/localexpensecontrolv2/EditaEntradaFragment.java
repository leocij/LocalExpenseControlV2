package com.lemelo.localexpensecontrolv2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.text.SimpleDateFormat;

import static android.content.Context.INPUT_METHOD_SERVICE;

/*
 * Created by leoci on 14/06/2017.
 */

public class EditaEntradaFragment extends Fragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_edita_entrada, container, false);
        getActivity().setTitle("Editando um item da entrada!");

        ScrollView scrollViewEditaEntrada = (ScrollView) view.findViewById(R.id.scrollViewEditaEntrada);
        scrollViewEditaEntrada.setOnTouchListener(new View.OnTouchListener() {
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

        final EditText txtData = (EditText) view.findViewById(R.id.txtData);
        txtData.setText(getArguments().getString("data"));
        final EditText txtDescricao = (EditText) view.findViewById(R.id.txtDescricao);
        txtDescricao.setText(getArguments().getString("descricao"));
        final EditText txtValor = (EditText) view.findViewById(R.id.txtValor);
        txtValor.setText(getArguments().getString("valor"));

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fecha Teclado
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( INPUT_METHOD_SERVICE);
                //imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.setFocusableInTouchMode(true);
                v.requestFocus();
                v.setFocusableInTouchMode(false);

                SQLiteDatabase db = null;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date d = sdf.parse(txtData.getText().toString());
                    java.sql.Date dataSql = new java.sql.Date(d.getTime());
                    String descricao = txtDescricao.getText().toString();
                    String valorStr = txtValor.getText().toString();
                    if(valorStr.equals("")){
                        valorStr = "0.0";
                    }
                    BigDecimal valor = new BigDecimal(valorStr);

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    EntradaDao entradaDao = new EntradaDao(db);

                    Entrada entrada = new Entrada();
                    entrada.setId(Integer.parseInt(getArguments().getString("id")));
                    entrada.setData(dataSql);
                    entrada.setDescricao(descricao);
                    entrada.setValor(valor);

                    entradaDao.update(entrada);

                    Toast.makeText(getContext(), "Registro Editado!", Toast.LENGTH_LONG).show();

                    EntradaFragment fragment = new EntradaFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_content, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
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

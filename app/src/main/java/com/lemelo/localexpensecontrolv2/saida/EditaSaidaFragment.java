package com.lemelo.localexpensecontrolv2.saida;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.lemelo.localexpensecontrolv2.saida.SaidaFragment;

import java.text.ParseException;

/*
 * Created by leoci on 14/06/2017.
 */

public class EditaSaidaFragment extends Fragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_edita_saida, container, false);
        getActivity().setTitle("Editando um item da saida!");

        ScrollView scrollViewEditaSaida = (ScrollView) view.findViewById(R.id.scrollViewEditaSaida);
        scrollViewEditaSaida.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //fecha teclado
                new MyKeyboard().hideKeyboard(getActivity(), v);
                return false;
            }
        });

        final EditText txtData = (EditText) view.findViewById(R.id.txtData);
        txtData.setText(getArguments().getString("data"));
        final EditText txtDescricao = (EditText) view.findViewById(R.id.txtDescricao);
        txtDescricao.setText(getArguments().getString("descricao"));
        final EditText txtValor = (EditText) view.findViewById(R.id.txtValor);
        txtValor.setText(getArguments().getString("valor"));
        txtValor.addTextChangedListener(new MoneyTextWatcher(txtValor));

        final Button btnSalvar = (Button) view.findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fecha Teclado
                new MyKeyboard().hideKeyboard(getActivity(), v);
                SQLiteDatabase db = null;

                try {
                    String dataStr = txtData.getText().toString();
                    String descricao = txtDescricao.getText().toString();
                    String valorStr = txtValor.getText().toString();
                    if(valorStr.equals("")){
                        valorStr = "0.0";
                    }
                    //BigDecimal valor = new BigDecimal(valorStr);

                    FabricaConexao fabrica = new FabricaConexao(getContext());
                    db = fabrica.getWritableDatabase();
                    SaidaDao saidaDao = new SaidaDao(db);

                    Saida saida = new Saida();
                    saida.setId(Integer.parseInt(getArguments().getString("id")));
                    saida.setData(dataStr);
                    saida.setDescricao(descricao);
                    saida.setValor(valorStr);

                    saidaDao.update(saida);

                    Toast.makeText(getContext(), "Registro Editado!", Toast.LENGTH_LONG).show();

                    SaidaFragment fragment = new SaidaFragment();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_content, fragment);
                    //ft.addToBackStack(null);
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

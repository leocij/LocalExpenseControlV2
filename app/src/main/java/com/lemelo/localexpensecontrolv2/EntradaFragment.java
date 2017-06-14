package com.lemelo.localexpensecontrolv2;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

/**
 * Created by leoci on 13/06/2017.
 */

public class EntradaFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_entrada, container, false);
        getActivity().setTitle("Todas entradas de valores");
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.entradaFab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CadastraEntradaFragment cadastra = new CadastraEntradaFragment();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, cadastra);
                ft.commit();
            }
        });

        try {
            imprime(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    private void imprime(View view) throws ParseException {
        SQLiteDatabase db = null;

        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        EntradaDao entradaDao = new EntradaDao(db);
        List<Entrada> list = entradaDao.listAll();
        ArrayAdapter<Entrada> arrayAdapter = new ArrayAdapter<Entrada>(getActivity(), android.R.layout.simple_list_item_1, list);
        ListView lvImprimeEntradas = (ListView) view.findViewById(R.id.lvImprimeEntradas);
        lvImprimeEntradas.setAdapter(arrayAdapter);

    }
}

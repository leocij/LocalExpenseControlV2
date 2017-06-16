package com.lemelo.localexpensecontrolv2;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.util.List;

/*
 * Created by leoci on 13/06/2017.
 */

public class EntradaFragment extends Fragment{
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_entrada, container, false);
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
        // Fecha Teclado

        SQLiteDatabase db;

        FabricaConexao fabrica = new FabricaConexao(getContext());
        db = fabrica.getWritableDatabase();
        EntradaDao entradaDao = new EntradaDao(db);
        List<Entrada> list = entradaDao.listAll();
        ArrayAdapter<Entrada> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, list);
        ListView lvImprimeEntradas = (ListView) view.findViewById(R.id.lvImprimeEntradas);
        lvImprimeEntradas.setAdapter(arrayAdapter);

        lvImprimeEntradas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Entrada selecionado = (Entrada) parent.getItemAtPosition(position);
                trataSelecionado(selecionado);
            }
        });

    }

    private void trataSelecionado(final Entrada selecionado) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getActivity());
        dialogo.setTitle("Editar / Apagar?");
        dialogo.setMessage(selecionado.toString());

        dialogo.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Bundle bundle = new Bundle();
                bundle.putString("id", selecionado.getId().toString());
                bundle.putString("data", selecionado.getData());
                bundle.putString("descricao", selecionado.getDescricao());
                bundle.putString("valor", selecionado.getValor());

                EditaEntradaFragment edita = new EditaEntradaFragment();
                edita.setArguments(bundle);
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_content, edita);
                ft.commit();
            }
        });

        dialogo.setPositiveButton("Apagar", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AlertDialog.Builder dialogDel = new AlertDialog.Builder(getActivity());
                dialogDel.setTitle("Deseja realmente apagar?");
                dialogDel.setMessage(selecionado.toString());

                dialogDel.setNegativeButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogDel, int which) {
                        SQLiteDatabase db;
                        Integer id = selecionado.getId();
                        FabricaConexao fabrica = new FabricaConexao(getContext());
                        db = fabrica.getWritableDatabase();
                        EntradaDao entradaDao = new EntradaDao(db);
                        entradaDao.delete(id);
                        try {
                            imprime(view);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

                dialogDel.setPositiveButton("Não", null);
                dialogDel.show();
            }
        });

        dialogo.setNeutralButton("Cancelar", null);
        dialogo.show();
    }
}

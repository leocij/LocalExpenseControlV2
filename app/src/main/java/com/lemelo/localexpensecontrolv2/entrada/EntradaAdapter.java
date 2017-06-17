package com.lemelo.localexpensecontrolv2.entrada;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemelo.localexpensecontrolv2.R;
import com.lemelo.localexpensecontrolv2.entrada.Entrada;

import java.util.List;

/**
 * Created by leoci on 16/06/2017.
 */

public class EntradaAdapter extends BaseAdapter{

    private final List<Entrada> entradas;
    private final Activity act;

    public EntradaAdapter(List<Entrada> entradas, Activity act) {
        this.entradas = entradas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return entradas.size();
    }

    @Override
    public Object getItem(int position) {
        return entradas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.meu_list_view, parent, false);
        Entrada entrada = entradas.get(position);

        TextView textView1 = (TextView) view.findViewById(R.id.meu_list_view_textView1);
        textView1.setText(entrada.toString());
        return view;
    }
}

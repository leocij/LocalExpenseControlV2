package com.lemelo.localexpensecontrolv2.saida;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lemelo.localexpensecontrolv2.R;

import java.util.List;

/*
 * Created by leoci on 16/06/2017.
 */

public class SaidaAdapter extends BaseAdapter{

    private final List<Saida> saidas;
    private final Activity act;

    public SaidaAdapter(List<Saida> saidas, Activity act) {
        this.saidas = saidas;
        this.act = act;
    }

    @Override
    public int getCount() {
        return saidas.size();
    }

    @Override
    public Object getItem(int position) {
        return saidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater()
                .inflate(R.layout.meu_list_view, parent, false);
        Saida saida = saidas.get(position);

        TextView textView1 = (TextView) view.findViewById(R.id.meu_list_view_textView1);
        textView1.setText(saida.toString());
        return view;
    }
}

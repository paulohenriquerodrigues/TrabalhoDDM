package br.udesc.acheaqui.adapter;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import android.content.Context;
import java.util.List;

import br.udesc.acheaqui.model.Servico;

public class ServicoAdapter extends BaseAdapter {

    private Context context;
    private final List<Servico> servicos;

    public ServicoAdapter(Context context, List<Servico> servicos) {
        super();
        this.context = context;
        this.servicos = servicos;
    }


    @Override
    public int getCount() {
        return servicos.size();
    }

    @Override
    public Object getItem(int i) {
        return servicos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView t = new TextView(context);
        Servico s = servicos.get(i);
        t.setText(s.getDescricao());
        t.setTextSize(35);
        t.setTextColor(Color.rgb(0,0,0));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            t.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
        return t;
    }
}

package com.organizze.jmdevelopers.organizze.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.organizze.jmdevelopers.organizze.Model.Movimentacao;
import com.organizze.jmdevelopers.organizze.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MovimentacaoAdapter extends RecyclerView.Adapter<MovimentacaoAdapter.MyViewHolder> {
    Context context;
    ArrayList<Movimentacao> lista;

    public MovimentacaoAdapter(Context context, ArrayList<Movimentacao> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_movimentacao, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Movimentacao m = lista.get(i);
        myViewHolder.titulo.setText(m.getDescricao());
        myViewHolder.categoria.setText(m.getCategoria());
        myViewHolder.valor.setText(String.valueOf(m.getValor()));
        myViewHolder.valor.setTextColor(context.getResources().getColor(R.color.coloraccentreceita));

/// verifica se é desesas se for ele mda a cor e acrescenta o -
        if (m.getTipo().equals("d")) {
            myViewHolder.valor.setTextColor(context.getResources().getColor(R.color.colorAccent));
            myViewHolder.valor.setText("-" + m.getValor());
        }

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titulo, valor, categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.textAdapterTitulo);
            valor = itemView.findViewById(R.id.textAdapterValor);
            categoria = itemView.findViewById(R.id.textAdapterCategoria);
        }
    }


}

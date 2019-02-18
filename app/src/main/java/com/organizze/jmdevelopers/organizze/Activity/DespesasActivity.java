package com.organizze.jmdevelopers.organizze.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.organizze.jmdevelopers.organizze.Helper.DateUtil;
import com.organizze.jmdevelopers.organizze.Model.Movimentacao;
import com.organizze.jmdevelopers.organizze.R;

public class DespesasActivity extends AppCompatActivity {
    private EditText campodata,campocategoria,campodescricao,campovalor;
    private Movimentacao movimentacao;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);
        campodata=findViewById(R.id.campodata);
        campodescricao=findViewById(R.id.campodescricao);
        campovalor=findViewById(R.id.campovalor);
        campocategoria=findViewById(R.id.camposalario);
        // colocando uma data atual
        campodata.setText(DateUtil.dataatual());
        button=findViewById(R.id.botaosalvardespesas);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvardespesa();
            }
        });


    }
    public void salvardespesa(){
        movimentacao=new Movimentacao();
        // para pegar um valor double
        movimentacao.setValor(Double.parseDouble(campovalor.getText().toString()));
        movimentacao.setCategoria(campocategoria.getText().toString());
        movimentacao.setDescricao(campodescricao.getText().toString());
        movimentacao.setData(campodata.getText().toString());
        movimentacao.setTipo("d");
        movimentacao.salvar();
    }
}

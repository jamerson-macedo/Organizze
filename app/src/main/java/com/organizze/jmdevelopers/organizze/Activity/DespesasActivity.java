package com.organizze.jmdevelopers.organizze.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.organizze.jmdevelopers.organizze.Helper.DateUtil;
import com.organizze.jmdevelopers.organizze.R;

public class DespesasActivity extends AppCompatActivity {
    private EditText campodata,camposalario,campodescricao,campovalor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);
        campodata=findViewById(R.id.campodata);
        camposalario=findViewById(R.id.camposalario);
        campodescricao=findViewById(R.id.campodescricao);
        campovalor=findViewById(R.id.campovalor);
        // colocando uma data atual
        campodata.setText(DateUtil.dataatual());

    }
}

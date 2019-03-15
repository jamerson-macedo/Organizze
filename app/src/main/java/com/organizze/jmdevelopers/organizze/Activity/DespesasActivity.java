package com.organizze.jmdevelopers.organizze.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;
import com.organizze.jmdevelopers.organizze.Helper.DateUtil;
import com.organizze.jmdevelopers.organizze.Model.Movimentacao;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;

public class DespesasActivity extends AppCompatActivity {
    private EditText campodata, campocategoria, campodescricao, campovalor;
    private Movimentacao movimentacao;
    private FloatingActionButton button;
    private DatabaseReference firebase = ConfigFirebase.getdatabase();
    private FirebaseAuth firebaseAuth = ConfigFirebase.getFirebaseAutenticacao();
    // valor recuperado do firebase
    private Double despesatotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);
        campodata = findViewById(R.id.campodata);
        campodescricao = findViewById(R.id.campodescricao);
        campovalor = findViewById(R.id.campovalor);
        campocategoria = findViewById(R.id.camposalario);
        // colocando uma data atual
        campodata.setText(DateUtil.dataatual());


        recuperardespesas();

        button = findViewById(R.id.botaosalvardespesas);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvardespesa();
            }
        });


    }

    public void salvardespesa() {
        // verificações


        movimentacao = new Movimentacao();
        // para pegar um valor double
        if (validarcampos()) {
            String data = campodata.getText().toString();
            Double valorecuperado = Double.parseDouble(campovalor.getText().toString());
            movimentacao.setValor(valorecuperado);
            movimentacao.setCategoria(campocategoria.getText().toString());
            movimentacao.setDescricao(campodescricao.getText().toString());
            movimentacao.setData(data);
            movimentacao.setTipo("d");

            Log.i("despesatotal", String.valueOf(valorecuperado));
            Double despesaatualizada = despesatotal + valorecuperado;
            Log.i("despesatota", String.valueOf(despesatotal));


            atualizardespesas(despesaatualizada);
            movimentacao.salvar(data);
        }

    }

    public boolean validarcampos() {
        String categoria = campocategoria.getText().toString();
        String valor = campovalor.getText().toString();
        String descricao = campodescricao.getText().toString();
        String data = campodata.getText().toString();
        if (!categoria.isEmpty()) {
            if (!valor.isEmpty()) {
                if (!descricao.isEmpty()) {
                    if (!data.isEmpty()) {


                        Toast.makeText(DespesasActivity.this, "Salvo com Sucesso !", Toast.LENGTH_LONG).show();
                        return true;
                    }

                } else {

                    Toast.makeText(DespesasActivity.this, "Preencha o campo descrição", Toast.LENGTH_LONG).show();
                    return false;

                }

            } else {
                Toast.makeText(DespesasActivity.this, "Preencha o campo valor", Toast.LENGTH_LONG).show();

                return false;
            }


        } else {
            Toast.makeText(DespesasActivity.this, "Preencha o campo categoria", Toast.LENGTH_LONG).show();
            return false;

        }
        return true;
    }

    public void recuperardespesas() {
        /// acessando as despesas para somar e gravar
        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        DatabaseReference usuarioref = firebase.child("Usuarios").child(emailconvertido);
        /// ja estamos no nó de emails agora adicionar um ouvinte
        // para recuperar atributos do firebase

        usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("DATABASE", String.valueOf(dataSnapshot));

                // retorna os dados de usuario
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        despesatotal = user.getDespesatotal();
                    }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void atualizardespesas(Double despesaatualizada) {
        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        DatabaseReference usuarioref = firebase.child("Usuarios").child(emailconvertido);
        // metodo para atualizar
        usuarioref.child("despesatotal").setValue(despesaatualizada);


    }

    public Boolean verificanull(Double aDouble) {
        if (aDouble == null) {
            return true;
        } else {
            return false;
        }

    }
}

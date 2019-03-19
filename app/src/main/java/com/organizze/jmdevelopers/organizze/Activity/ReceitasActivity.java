package com.organizze.jmdevelopers.organizze.Activity;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;
import com.organizze.jmdevelopers.organizze.Helper.DateUtil;
import com.organizze.jmdevelopers.organizze.Model.Movimentacao;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;

import org.xml.sax.Parser;

public class ReceitasActivity extends AppCompatActivity {
    private EditText campodata, campocategoria, campodescricao, campovalor;
    private Movimentacao movimentacao;
    private FloatingActionButton button;
    private Double receitatotal;
    private DatabaseReference databaseReference = ConfigFirebase.getdatabase();
    private FirebaseAuth firebaseAuth = ConfigFirebase.getFirebaseAutenticacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receitas);
        campodata = findViewById(R.id.Rdata);
        campocategoria = findViewById(R.id.Rtipo);
        campodescricao = findViewById(R.id.Rdescricao);
        campovalor = findViewById(R.id.Rvalor);
        button = findViewById(R.id.RfloatingActionButton);
        recuperarreceitas();
        campodata.setText(DateUtil.dataatual());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarreceita();
            }
        });

    }

    private void salvarreceita() {
        // movimentacao
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
            movimentacao.setTipo("r");


            Double receitaatualizada = receitatotal + valorecuperado;


            atualizarreceita(receitaatualizada);
            movimentacao.salvar(data);
            finish();
        }
    }

    private void recuperarreceitas() {
        String idsuario = firebaseAuth.getCurrentUser().getEmail();
        String emailconvertido = Base64Custom.codificar(idsuario);
        DatabaseReference usuarioref = databaseReference.child("Usuarios").child(emailconvertido);
        /// ja estamos no nó de emails agora adicionar um ouvinte
        // para recuperar atributos do firebase

        usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                // retorna os dados de usuario
                Usuario user = dataSnapshot.getValue(Usuario.class);
                receitatotal = user.getReceitatotal();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private boolean validarcampos() {
        String categoria = campocategoria.getText().toString();
        String valor = campovalor.getText().toString();
        String descricao = campodescricao.getText().toString();
        String data = campodata.getText().toString();
        if (!categoria.isEmpty()) {
            if (!valor.isEmpty()) {
                if (!descricao.isEmpty()) {
                    if (!data.isEmpty()) {


                        Toast.makeText(ReceitasActivity.this, "Salvo com Sucesso !", Toast.LENGTH_LONG).show();
                        return true;
                    }

                } else {

                    Toast.makeText(ReceitasActivity.this, "Preencha o campo descrição", Toast.LENGTH_LONG).show();
                    return false;

                }

            } else {
                Toast.makeText(ReceitasActivity.this, "Preencha o campo valor", Toast.LENGTH_LONG).show();

                return false;
            }


        } else {
            Toast.makeText(ReceitasActivity.this, "Preencha o campo categoria", Toast.LENGTH_LONG).show();
            return false;

        }
        return true;


    }
    public void atualizarreceita(Double receitaatualizada){
        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        DatabaseReference usuarioref = databaseReference.child("Usuarios").child(emailconvertido);
        // metodo para atualizar
        usuarioref.child("receitatotal").setValue(receitaatualizada);

    }

}

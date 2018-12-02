package com.organizze.jmdevelopers.organizze.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;

public class LoginActivity extends AppCompatActivity {
    private EditText email, senha;
    private Button botao;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email_login);
        senha = findViewById(R.id.senha_login);
        botao = findViewById(R.id.botao_login);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _email = email.getText().toString();
                String _senha = senha.getText().toString();
                if (!_email.isEmpty()) {
                    if (!_senha.isEmpty()) {
                        usuario = new Usuario();
                        usuario.setEmail(_email);
                        usuario.setSenha(_senha);
                        validarlogin();

                    } else {
                        Toast.makeText(LoginActivity.this, "Preencha o campo da senha", Toast.LENGTH_LONG).show();

                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Preencha o campo Email", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    private void validarlogin() {
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Usuario logado com Sucesso", Toast.LENGTH_LONG).show();
                    abrirprincipal();
                }else {
                    String erro="";
                    try{
                        // significa lançar
                       throw  task.getException();

                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro="Email e senha não corresponde ao usuario cadastrado";
                    }catch (FirebaseAuthInvalidUserException e ){
                        erro="Usuario não cadastrado";
                    }catch (Exception e ){
                        e.printStackTrace();
                        erro="erro ao fazer login "+ e.getMessage();
                    }
                    Toast.makeText(LoginActivity.this,erro,Toast.LENGTH_LONG).show();

                }

            }
        });

    }
    public void abrirprincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
    }

}

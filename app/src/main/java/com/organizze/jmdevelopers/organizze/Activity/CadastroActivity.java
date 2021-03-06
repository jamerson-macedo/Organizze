package com.organizze.jmdevelopers.organizze.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;

import java.nio.file.FileAlreadyExistsException;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, email, senha;
    private Button botao;
    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome = findViewById(R.id.nome_cadastro);
        email = findViewById(R.id.email_cadastro);
        senha = findViewById(R.id.senha_cadastro);
        botao = findViewById(R.id.botao_cadastro);
        progressDialog=new ProgressDialog(this);
        getSupportActionBar().setTitle(("Cadastro"));



        // verificar se tudo foi difitado
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _nome = nome.getText().toString();
                String _email = email.getText().toString();
                String _senha = senha.getText().toString();
                if (!_nome.isEmpty()) {
                    if (!_email.isEmpty()) {
                        if (!_senha.isEmpty()) {
                            progressDialog.setTitle("Criando conta ");
                            progressDialog.setMessage("Aguarde um momento");
                            progressDialog.setCanceledOnTouchOutside(false);
                            // aquiele chama e quando pega o dismiss ai ele para
                            progressDialog.show();
                            usuario = new Usuario();
                            usuario.setNome(_nome);
                            usuario.setEmail(_email);
                            usuario.setSenha(_senha);

                            cadastrarusuario();

                        } else {

                            Toast.makeText(CadastroActivity.this, "Preencha o campo senha", Toast.LENGTH_LONG).show();
                        }


                    } else {

                        Toast.makeText(CadastroActivity.this, "Preencha o campo Email", Toast.LENGTH_LONG).show();
                    }

                } else {

                    Toast.makeText(CadastroActivity.this, "Preencha o campo nome", Toast.LENGTH_LONG).show();
                }
            }

            private void cadastrarusuario() {
                autenticacao = ConfigFirebase.getFirebaseAutenticacao();

                autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    String idusuario = Base64Custom.codificar(usuario.getEmail());
                                    usuario.setIdusuario(idusuario);
                                    // salva no firebase
                                    usuario.salvar();
                                    startActivity(new Intent(CadastroActivity.this,PrincipalActivity.class));
                                } else {
                                    String erro = "";
                                    try {
                                        // tratando execao
                                        throw task.getException();

                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        progressDialog.dismiss();
                                        erro = "Digite uma senha forte !";

                                    } catch (FirebaseAuthUserCollisionException e) {
                                        progressDialog.dismiss();

                                        erro = "o usuario ja esta cadastrado";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        progressDialog.dismiss();
                                        erro = "Digite um Email valido";
                                    } catch (Exception e) {
                                        progressDialog.dismiss();
                                        erro = "erro ao cadastrar usuario " + e.getMessage();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(CadastroActivity.this, erro, Toast.LENGTH_LONG).show();
                                }

                            }
                        });
            }
        });

    }

    public void abrirprincipal() {

        startActivity(new Intent(this, PrincipalActivity.class));
    }

}

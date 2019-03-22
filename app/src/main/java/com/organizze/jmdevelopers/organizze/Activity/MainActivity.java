package com.organizze.jmdevelopers.organizze.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.organizze.jmdevelopers.organizze.Activity.CadastroActivity;
import com.organizze.jmdevelopers.organizze.Activity.LoginActivity;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.R;

public class MainActivity extends IntroActivity {
// vamos ver se ja tem usuario cadastreado
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        // primeiro
        // ocultar botoes


        setButtonBackVisible(false);
        setButtonNextVisible(false);
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_1).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_2).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_3).build());
        // esses metodos faz com que quando chegar no 4 slider voce nem va e nem volte
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_4).build());
        addSlide(new FragmentSlide.Builder().background(android.R.color.white).fragment(R.layout.intro_cadastro).canGoForward(false).build());


    }
    public void btlogin(View view){
        startActivity(new Intent(this,LoginActivity.class));


    }
    public void btcadastrar(View view){
        startActivity(new Intent(this,CadastroActivity.class));

    }
    public void verificarusuariologado(){
        firebaseAuth=ConfigFirebase.getFirebaseAutenticacao();
    firebaseAuth.signOut();
        // verifica se tem usuario

        if(firebaseAuth.getCurrentUser()!=null){
            abrirprincipal();
        }

    }
    public void abrirprincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarusuariologado();
    }
}

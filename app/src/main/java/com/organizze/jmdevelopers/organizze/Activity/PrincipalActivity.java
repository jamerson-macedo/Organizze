package com.organizze.jmdevelopers.organizze.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class PrincipalActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth= ConfigFirebase.getFirebaseAutenticacao();
    private TextView nome,saldo;
    private MaterialCalendarView materialCalendarView;
    private FirebaseAuth auth=ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebase = ConfigFirebase.getdatabase();
    private Double despesatotal=0.0;
    private Double receitatotal=0.0;
    private Double resumototal=0.0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome=findViewById(R.id.nome);
        saldo=findViewById(R.id.saldo);
        materialCalendarView=findViewById(R.id.calendar);

        toolbar.setTitle("Organizze");

        configurarcalendario();
        recuperarresumo();

    }
    public void recuperarresumo(){
        // recperar o id

        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        DatabaseReference usuarioref = firebase.child("Usuarios").child(emailconvertido);
        usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario=dataSnapshot.getValue(Usuario.class);
                despesatotal=usuario.getDespesatotal();
                receitatotal=usuario.getReceitatotal();
                resumototal=receitatotal-despesatotal;
                DecimalFormat decimalFormat=new DecimalFormat("0.##");
                String resultadoformatado=decimalFormat.format(resumototal);
                nome.setText("Olá, "+usuario.getNome());
                saldo.setText("R$ "+resultadoformatado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
// metodo para tratar o clique no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menusair:

            auth.signOut();
            startActivity(new Intent(this,MainActivity.class));
            finish();
            break;

        }

        return super.onOptionsItemSelected(item);
    }

    // para adicionar o botao no toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal,menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void adicionardespesa(View view){
        startActivity(new Intent(this,DespesasActivity.class));

    }
    public void adicionarreceita(View view){
        startActivity(new Intent(this,ReceitasActivity.class));


    }
    public void configurarcalendario(){

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

            }
        });



    }

}

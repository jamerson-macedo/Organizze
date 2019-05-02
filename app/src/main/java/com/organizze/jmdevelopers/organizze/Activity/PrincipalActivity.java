package com.organizze.jmdevelopers.organizze.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
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
import com.organizze.jmdevelopers.organizze.Adapter.MovimentacaoAdapter;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;
import com.organizze.jmdevelopers.organizze.Model.Movimentacao;
import com.organizze.jmdevelopers.organizze.Model.Usuario;
import com.organizze.jmdevelopers.organizze.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.String.valueOf;

public class PrincipalActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = ConfigFirebase.getFirebaseAutenticacao();
    private TextView nome, saldo;
    private MaterialCalendarView materialCalendarView;
    private FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
    private DatabaseReference firebase = ConfigFirebase.getdatabase();
    private Double despesatotal = 0.0;
    private Double receitatotal = 0.0;
    private Double resumototal = 0.0;
    private DatabaseReference usuarioref;
    private RecyclerView recyclerView;
    private MovimentacaoAdapter adapter;
    private ArrayList<Movimentacao> lista = new ArrayList<>();
    // esse value é feito para que o firebase nao fique recuperando os usuarios mesmo com o app fechado entao ele adiciona para parar quando da o stop no app
    private ValueEventListener valueEventListenerUsuario;
    private ValueEventListener valueEventListenerMovimentacao;
    /////// REFERENCIA PARA MOVIMENTACAO

    private DatabaseReference movimentacaoref;
    private String mesAnoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nome = findViewById(R.id.nome);
        saldo = findViewById(R.id.quantidade);
        materialCalendarView = findViewById(R.id.calendar);
        recyclerView = findViewById(R.id.recyclerviewmov);
        adapter = new MovimentacaoAdapter(this, lista);


        // configurações do recycler
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        toolbar.setTitle("Organizze");

        configurarcalendario();
        swipe();


    }

    public void swipe() {
        ItemTouchHelper.Callback itemtouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragflags=ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeflags=ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragflags,swipeflags);
                        /// como vai ser o movimento
            }
                /// aqui voce pode mover o objeto na tela
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Log.i("swipe","item arrastado");

            }

        };
        ///anexando ao recyclerview
        new ItemTouchHelper(itemtouch).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recuperarresumo();
        recuperarmovimentacoes();
    }

    private void recuperarmovimentacoes() {
        // recperar o id
        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        Log.i("emaildocara", emailconvertido);
        Log.i("iddocara", idusuario);

        movimentacaoref = ConfigFirebase.getdatabase();
        Log.i("movimentosdocara", "ola " + movimentacaoref.push());

        movimentacaoref = firebase.child("movimentacao").child(emailconvertido).child(mesAnoSelecionado);

        Log.i("movimenacoes", "no de movimenacoes " + movimentacaoref.toString());

        valueEventListenerMovimentacao = movimentacaoref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lista.clear();
                /// qando iver varios filhos ai coloca o gettchildrem
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Movimentacao mo = dados.getValue(Movimentacao.class);
                    Log.i("OLAMUNDO", "todos os dados" + dados.getValue());

                    lista.add(mo);
                    Log.i("atual123", "veja : " + lista);


                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void recuperarresumo() {
        // recperar o id

        String idusuario = firebaseAuth.getCurrentUser().getEmail();
        // agora conveter para base 64
        String emailconvertido = Base64Custom.codificar(idusuario);
        usuarioref = firebase.child("Usuarios").child(emailconvertido);

        valueEventListenerUsuario = usuarioref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                Log.i("MMMMM", "" + usuario.getDespesatotal());

                despesatotal = usuario.getDespesatotal();
                receitatotal = usuario.getReceitatotal();
                resumototal = receitatotal - despesatotal;
                DecimalFormat decimalFormat = new DecimalFormat("0.##");
                String resultadoformatado = decimalFormat.format(resumototal);
                nome.setText("Olá, " + usuario.getNome());
                saldo.setText("R$ " + resultadoformatado);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    // metodo para tratar o clique no menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menusair:

                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    // para adicionar o botao no toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal, menu);


        return super.onCreateOptionsMenu(menu);
    }

    public void adicionardespesa(View view) {
        startActivity(new Intent(this, DespesasActivity.class));

    }

    public void adicionarreceita(View view) {
        startActivity(new Intent(this, ReceitasActivity.class));


    }

    public void configurarcalendario() {
        final CalendarDay data = materialCalendarView.getCurrentDate();
        /// 0 de valor ara ras e d de digio
        String MesSelecionado = String.format("%02d", (data.getMonth() + 1));
        mesAnoSelecionado = String.valueOf(MesSelecionado + "" + data.getYear());

        materialCalendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String MesSelecionado = String.format("%02d", (date.getMonth() + 1));
                mesAnoSelecionado = (MesSelecionado) + "" + date.getYear();
                movimentacaoref.removeEventListener(valueEventListenerMovimentacao);
                recuperarmovimentacoes();
            }
        });


    }

    // esse metodo é chamado quuando o usario fecha o app
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("onstop", "evento removido");
        usuarioref.removeEventListener(valueEventListenerUsuario);
        movimentacaoref.removeEventListener(valueEventListenerMovimentacao);
    }
}

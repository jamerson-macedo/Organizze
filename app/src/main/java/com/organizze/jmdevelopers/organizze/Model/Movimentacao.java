package com.organizze.jmdevelopers.organizze.Model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;

public class Movimentacao {
    private String data;
    private String categoria;
    private String descricao;
    private String tipo;
    private Double valor;


    public Movimentacao() {
    }
    public void salvar(){
        FirebaseAuth firebaseAuth=ConfigFirebase.getFirebaseAutenticacao();
        // pegando o usuario atual
        String idusuario= Base64Custom.codificar(firebaseAuth.getCurrentUser().getEmail());
        DatabaseReference databaseReference = ConfigFirebase.getdatabase();
        // movimentacao com um email criptografado, o mes o push id unico do firebase set value é ara salvar tudo

        databaseReference.child("movimentacao").child(idusuario).child("032018").push().setValue(this);

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}

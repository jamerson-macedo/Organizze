package com.organizze.jmdevelopers.organizze.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.organizze.jmdevelopers.organizze.Config.ConfigFirebase;

public class Usuario {
    private String idusuario;
    private String nome;
    private String email;
    private String senha;


    public Usuario() {
    }
    // salvar no firebase
    public void salvar(){
        DatabaseReference firebase=ConfigFirebase.getdatabase();
        firebase.child("Usuarios").child(this.idusuario).setValue(this);
    }
    @Exclude
    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

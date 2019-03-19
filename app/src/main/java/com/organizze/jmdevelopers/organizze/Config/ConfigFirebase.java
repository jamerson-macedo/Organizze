package com.organizze.jmdevelopers.organizze.Config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.organizze.jmdevelopers.organizze.Helper.Base64Custom;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference firebaseDatabase;
   public static FirebaseAuth getFirebaseAutenticacao() {
       if (auth == null) {
           auth = FirebaseAuth.getInstance();
       }
       return auth;
   }
   public static DatabaseReference getdatabase(){
       if(firebaseDatabase==null){
           firebaseDatabase=FirebaseDatabase.getInstance().getReference();

       }
       return firebaseDatabase;

   }
   public static DatabaseReference getidusuario(){

       String idusuario = auth.getCurrentUser().getEmail();
       // agora conveter para base 64
       String emailconvertido = Base64Custom.codificar(idusuario);
       DatabaseReference usuarioref = firebaseDatabase.child("Usuarios").child(emailconvertido);
       return usuarioref;

   }


}

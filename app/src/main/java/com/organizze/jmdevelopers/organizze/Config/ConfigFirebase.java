package com.organizze.jmdevelopers.organizze.Config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
}

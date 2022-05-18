package com.example.vishnusports.ui.Profile;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataScope;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.vishnusports.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {

    protected MutableLiveData<String> mText,mText2,mText3;
    static  String val1,val2,val3,res1,res2,res3;
    static  boolean a1,a2,a3;

    TextView x;
    String email2,username1,game1,game2,game3;
    String id;
    // creating a variable for
    // our Firebase Database.
    //FirebaseDatabase firebaseDatabase;
    //FirebaseAuth firebaseAuth;
    DocumentReference documentReference;
    FirebaseUser user;
    FirebaseFirestore fStore;
    // creating a variable for our
    // Database Reference for Firebase.

     public ProfileViewModel() {

        mText = new MutableLiveData<>();

       /* user= FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        id=user.getUid();
        fStore= FirebaseFirestore.getInstance();
        documentReference=fStore.collection("users").document(id);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {


            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    email2 = documentSnapshot.getString("email");
                    username1 = documentSnapshot.getString("username");
                    game1 = documentSnapshot.getString("game1");
                    game2 = documentSnapshot.getString("game2");
                    game3 = documentSnapshot.getString("game3");
                    if (val1.compareTo("email") == 0){
                        res1=email2;
                        mText.setValue(res1);
                        val2="";val3="";val1="";
                        a1=true; a2=false;a3=false;
                    }
                    if (val2.compareTo("username") == 0) {
                        res2 = username1;
                        mText2.setValue(res2);
                        val1="";val3="";val2="";
                        a1=false; a2=true;a3=false;
                    }
                    if (val3.compareTo("game1") == 0) {
                        if (game2 == null) {
                            res3 = "   Your selected games(Click to change)\n   " + game1;
                        } else if (game3 == null) {
                            res3 = "   Your selected games(Click to change)\n   " + game1 + "\n   " + game2;
                        } else {
                            res3 = "   Your selected games(Click to change)\n   " + game1 + "\n   " + game2 + "\n   " + game3;
                        }
                        mText3.setValue(res3);
                        val1="";val2="";val3="";
                        a1=false; a2=false;a3=true;
                    }
                }
            }

            });*/

       // });


    }
    public void temp(@NonNull String v){
       /*  if(v.compareTo("email")==0)
                this.val1=v;
         if(v.compareTo("username")==0)
                this.val2=v;
         if(v.compareTo("game1")==0)
                this.val3=v;*/
    }

    public LiveData<String> getText() {
        /* if(a1)
             mText=mText;
        else if(a2)
            mText=mText2;
        else if(a3)
            mText=mText3;*/

        return mText;


    }
}